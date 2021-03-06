package school.journal.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.journal.aop.Secured;
import school.journal.controller.exception.ControllerException;
import school.journal.entity.Subject;
import school.journal.entity.enums.RoleEnum;
import school.journal.service.ISubjectService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/subjects")
public class SubjectAPIController extends BaseController<Subject> {
    private Logger LOGGER = Logger.getLogger(SubjectAPIController.class);

    @Autowired
    @Qualifier("SubjectService")
    private ISubjectService subjectService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Secured(RoleEnum.USER)
    public ResponseEntity get(HttpServletRequest request, HttpServletRequest req)
            throws ControllerException {
        return read(() -> subjectService.read(), "Can't get full subjects list", LOGGER);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Secured(RoleEnum.DIRECTOR_OF_STUDIES)
    public ResponseEntity create(HttpServletRequest request, @RequestBody Subject subject)
            throws ControllerException {
        return createOrUpdate((Subject s) -> subjectService.create(s), subject, "Can't create subject", LOGGER);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Secured(RoleEnum.DIRECTOR_OF_STUDIES)
    public ResponseEntity update(HttpServletRequest request, @RequestBody Subject subject)
            throws ControllerException {
        return createOrUpdate((Subject s) -> subjectService.update(s), subject, "Can't update subject", LOGGER);
    }

    @RequestMapping(value = "/{subjectId}", method = RequestMethod.DELETE)
    @ResponseBody
    @Secured(RoleEnum.DIRECTOR_OF_STUDIES)
    public ResponseEntity delete(HttpServletRequest request, @PathVariable("subjectId") int subjectId)
            throws ControllerException {
        return delete((int id) -> subjectService.delete(id), subjectId, "Can't delete subject by id", LOGGER);
    }

    @RequestMapping(value = "/{subjectId}")
    @ResponseBody
    @Secured(RoleEnum.USER)
    public ResponseEntity getOne(HttpServletRequest request, @PathVariable("subjectId") int subjectId)
            throws ControllerException {
        return getOne((int id) -> subjectService.getOne(subjectId), subjectId, "Can't get subject by id", LOGGER);
    }
}
