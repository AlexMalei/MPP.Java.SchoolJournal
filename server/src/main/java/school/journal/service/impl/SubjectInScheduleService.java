package school.journal.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import school.journal.controller.util.ExceptionEnum;
import school.journal.entity.*;
import org.springframework.stereotype.Service;
import school.journal.entity.enums.DayOfWeekEnum;
import school.journal.repository.IRepository;
import school.journal.repository.exception.RepositoryException;
import school.journal.service.CRUDService;
import school.journal.service.ISubjectInScheduleService;
import school.journal.service.exception.ClassifiedServiceException;
import school.journal.service.exception.ServiceException;
import school.journal.utils.exception.ValidationException;

import java.sql.Time;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import static school.journal.utils.ValidateServiceUtils.validateString;

@Service("SubjectInScheduleService")
public class SubjectInScheduleService extends CRUDService<SubjectInSchedule> implements ISubjectInScheduleService {

    private static final String SQL_FOR_GET_CLASS_SCHEDULE = "SELECT * \n" +
            "FROM `subject_in_schedule`\n" +
            "WHERE `class_id` = {0}\n" +
            "ORDER BY  `day_of_week`, `begin_time`; ";
    private static final String SQL_FOR_GET_TEACHER_SCHEDULE = "SELECT * \n" +
            "FROM `subject_in_schedule`\n" +
            "WHERE `teacher_id` = {0}\n" +
            "ORDER BY  `day_of_week`, `begin_time`; ";

    private static final long START_WORK_DAY_TIME_MILLIS = 28_799_999 - 10_800_000;//7h*60m*60s*1000ms+59m*60s*1000ms+59s*1000ms+999ms
    private static final long END_WORK_DAY_TIME_MILLIS = 72_000_000 - 10_800_000;//20h*60m*60s*1000ms

    @Autowired
    public SubjectInScheduleService(@Qualifier("SubjectInScheduleRepository") IRepository<SubjectInSchedule> repository) {
        LOGGER = Logger.getLogger(SubjectInSchedule.class);
        this.repository = repository;
    }

    private void checkTime(Time time) throws ServiceException {
        //TODO: SYNCHRONIZE WITH LESSON_TIME_SERVICE
        if (time.before(new Time(START_WORK_DAY_TIME_MILLIS))
                || time.after(new Time(END_WORK_DAY_TIME_MILLIS)))
            throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_has_wrong_time);
    }

    @Override
    public SubjectInSchedule create(SubjectInSchedule subject) throws ServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            validateString(subject.getPlace(), "Place");
        } catch (ValidationException exc) {
            LOGGER.error(exc);
            throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_has_wrong_place);
        }
        try {
            checkTime(subject.getBeginTime());
            checkDayOfWeek(subject.getDayOfWeek());
            subject.setClazz((Clazz) session.get(Clazz.class, subject.getClazzId()));
            subject.setSubject((Subject) session.get(Subject.class, subject.getSubjectId()));
            subject.setTeacher((Teacher) session.get(Teacher.class, subject.getTeacherId()));
            if (subject.getClazz().getClassId() == null) {
                throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_has_wrong_class);
            }
            if (subject.getTeacher().getUserId() == null) {
                throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_has_wrong_teacher);
            }
            if (subject.getSubject().getSubjectId() == null) {
                throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_has_wrong_subject);
            }
            List<SubjectInSchedule> sameSubjects = Collections.EMPTY_LIST;
            List<SubjectInSchedule> sameClass = Collections.EMPTY_LIST;
            try {
                sameSubjects = session.createQuery(MessageFormat.format("from SubjectInSchedule as s where s.teacher.userId = {0} and s.dayOfWeek = :day and s.beginTime = :time", subject.getTeacher().getUserId()))
                        .setParameter("time", (Object) subject.getBeginTime())
                        .setParameter("day", (Object) subject.getDayOfWeek())
                        .list();
                sameClass = session.createQuery(MessageFormat.format("from SubjectInSchedule as s where s.clazz.classId = {0} and s.dayOfWeek = :day and s.beginTime = :time", subject.getClazzId()))
                        .setParameter("time", (Object) subject.getBeginTime())
                        .setParameter("day", (Object) subject.getDayOfWeek())
                        .list();
            } catch (Exception exc) {
                LOGGER.error(exc);
                throw new ServiceException(exc);
            }
            if (sameSubjects.size() != 0) {
                throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_teacher_dublicate);
            }
            if (sameClass.size() != 0) {
                throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_class_dublicate);
            }
            repository.create(subject, session);
            transaction.commit();
        } catch (RepositoryException exc) {
            transaction.rollback();
            LOGGER.error(exc);
            throw new ServiceException(exc);
        } finally {
            session.close();
        }
        return subject;
    }

    @Override
    public SubjectInSchedule update(SubjectInSchedule newSubject) throws ServiceException {
        SubjectInSchedule subject;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            subject = repository.get(newSubject.getSubectInScheduleId(), session);
            subject.setClazz((Clazz) session.load(Clazz.class, newSubject.getClazzId()));
            subject.setSubject((Subject) session.load(Subject.class, newSubject.getSubjectId()));
            subject.setTeacher((Teacher) session.load(Teacher.class, newSubject.getTeacherId()));
            checkPlace(newSubject, subject);
            checkTime(newSubject.getBeginTime());
            subject.setBeginTime(newSubject.getBeginTime());
            checkDayOfWeek(newSubject.getDayOfWeek());
            subject.setDayOfWeek(newSubject.getDayOfWeek());
            repository.update(subject, session);
            transaction.commit();
        } catch (ValidationException | RepositoryException | ObjectNotFoundException exc) {
            transaction.rollback();
            LOGGER.error(exc);
            throw new ServiceException(exc);
        } finally {
            session.close();
        }
        return subject;
    }

    @Override
    public void delete(int id) throws ServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            SubjectInSchedule subject = (SubjectInSchedule) session.load(SubjectInSchedule.class, id);
            if (subject != null) {
                repository.delete(subject, session);
                transaction.commit();
            }
        } catch (ObjectNotFoundException | RepositoryException exc) {
            transaction.rollback();
            LOGGER.error(exc);
            throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_not_found);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<SubjectInSchedule> read() throws ServiceException {
        return super.read();
    }

    @Override
    public List<SubjectInSchedule> getPupilSchedule(int id) throws ServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<SubjectInSchedule> subjects = Collections.EMPTY_LIST;
        try {
            subjects = session.createQuery(MessageFormat.format("from SubjectInSchedule as s where s.clazz.classId = {0} order by s.dayOfWeek,s.beginTime ", id)).list();
        } catch (Exception exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        return subjects;
    }

    @Override
    public List<SubjectInSchedule> getTeacherSchedule(int teacherId) throws ServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<SubjectInSchedule> subjects = Collections.EMPTY_LIST;
        try {
            subjects = session.createQuery(MessageFormat.format("from SubjectInSchedule as s where s.teacher.userId = {0} order by s.dayOfWeek,s.beginTime", teacherId)).list();
        } catch (Exception exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        return subjects;
    }

    private void checkPlace(SubjectInSchedule newSubject, SubjectInSchedule subject) throws ValidationException {
        String place = newSubject.getPlace();
        if (place == null) return;
        validateString(place, "Place");
        subject.setPlace(place);
    }

    private void checkDayOfWeek(DayOfWeekEnum day) throws ServiceException {
        if (day.getValue() < 1 || day.getValue() > 6) {
            throw new ClassifiedServiceException(ExceptionEnum.subject_in_schedule_has_wrong_day_of_week);
        }
    }

    public List<SubjectInSchedule> getSubjectsWithTeacherClassSubject(int classId, int teacherId, int subjectId)
            throws ServiceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<SubjectInSchedule> subjects = Collections.EMPTY_LIST;
        try {
            subjects = session.createQuery(MessageFormat.format("from SubjectInSchedule as s where s.teacher.userId = {0} and s.subject.subjectId = {1} and s.clazz.classId = {2}", teacherId, subjectId, classId)).list();
        } catch (Exception exc) {
            LOGGER.error(exc);
            throw new ServiceException(exc);
        }
        return subjects;
    }
}
