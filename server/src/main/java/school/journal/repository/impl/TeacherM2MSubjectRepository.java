package school.journal.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import school.journal.entity.Role;
import school.journal.entity.TeacherM2MSubject;
import school.journal.entity.User;
import school.journal.repository.IRepository;
import school.journal.repository.RepositoryAbstractClass;
import school.journal.repository.exception.RepositoryException;
import school.journal.repository.specification.HibernateSpecification;

import java.util.List;

public class TeacherM2MSubjectRepository extends RepositoryAbstractClass<TeacherM2MSubject> {
    private static final TeacherM2MSubjectRepository instance = new TeacherM2MSubjectRepository();

    public static TeacherM2MSubjectRepository getInstance() {
        return instance;
    }

    private TeacherM2MSubjectRepository() {
    }

    public List<TeacherM2MSubject> read() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<TeacherM2MSubject> teacherM2MSubjects = (List<TeacherM2MSubject>) session.createCriteria(Role.class).list();
        session.getTransaction().commit();
        session.close();
        return teacherM2MSubjects;
    }

    @Override
    public TeacherM2MSubject create(TeacherM2MSubject teacherM2MSubject, Session session) throws RepositoryException {
        session.save(teacherM2MSubject);
        return teacherM2MSubject;
    }

    @Override
    public TeacherM2MSubject update(TeacherM2MSubject teacherM2MSubject, Session session) throws RepositoryException {
        session.update(teacherM2MSubject);
        return teacherM2MSubject;
    }

    @Override
    public TeacherM2MSubject delete(TeacherM2MSubject teacherM2MSubject, Session session) throws RepositoryException {
        session.delete(teacherM2MSubject);
        return teacherM2MSubject;
    }

    @Override
    public List<TeacherM2MSubject> query(HibernateSpecification specification, Session session) throws RepositoryException {
        Criteria criteria = session.createCriteria(TeacherM2MSubject.class);
        Criterion criterion = specification.toCriteria();
        if (criteria != null) {
            criteria.add(criterion);
        }
        return criteria.list();
    }
}
