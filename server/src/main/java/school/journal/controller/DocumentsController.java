package school.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.journal.controller.exception.ControllerException;
import school.journal.service.document.generation.DocumentType;
import school.journal.service.document.generation.IGenerationService;
import school.journal.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;

@CrossOrigin
@Controller
@RequestMapping(value = "/api/docs")
public class DocumentsController {

    @Autowired
    @Qualifier("GenerationService")
    private IGenerationService generationService;

    //XLS-----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getClassPupilsList/{classId}/xls", method = RequestMethod.GET, produces = "application/xlsx")
    @ResponseBody
    public ResponseEntity getClassPupilsListXSLDocument(HttpServletRequest request, HttpServletResponse response,
                                                        @PathVariable("classId") int classId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=pupils.xlsx");
            generationService.generateClassPupilListDocument(response.getOutputStream(), DocumentType.XLSX, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getClassSchedule/{classId}/xls", method = RequestMethod.GET, produces = "application/xlsx")
    @ResponseBody
    public ResponseEntity getClassScheduleXSLDocument(HttpServletRequest request, HttpServletResponse response,
                                                      @PathVariable("classId") int classId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=class_schedule.xlsx");
            generationService.generateClassScheduleDocument(response.getOutputStream(), DocumentType.XLSX, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getTeacherSchedule/{teacherId}/xls", method = RequestMethod.GET, produces = "application/xlsx")
    @ResponseBody
    public ResponseEntity getTeacherScheduleXSLDocument(HttpServletRequest request, HttpServletResponse response,
                                                        @PathVariable("teacherId") int teacherId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=teacher_schedule.xlsx");
            generationService.generateTeacherScheduleDocument(response.getOutputStream(), DocumentType.XLSX, teacherId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    @RequestMapping(value = "/getFullSchedule/xls", method = RequestMethod.GET, produces = "application/xlsx")
    @ResponseBody
    public ResponseEntity getFullScheduleXSLDocument(HttpServletRequest request, HttpServletResponse response)
            throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=full_schedule.xlsx");
            generationService.generateFullScheduleDocument(response.getOutputStream(), DocumentType.XLSX);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    @RequestMapping(value = "/getMarks/class/{classId}/subject/{subjectId}/xls",
            method = RequestMethod.GET, produces = "application/xlsx")
    @ResponseBody
    public ResponseEntity getMarksXSLDocument(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable("classId") int classId,
                                              @PathVariable("subjectId") int subjectId)
            throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=marks.xlsx");
            generationService.generateMarksDocument(response.getOutputStream(), DocumentType.XLSX, subjectId, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    //CSV-----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getClassPupilsList/{classId}/csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public ResponseEntity getClassPupilsListCSVDocument(HttpServletRequest request, HttpServletResponse response,
                                                        @PathVariable("classId") int classId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=pupils.csv");
            generationService.generateClassPupilListDocument(response.getOutputStream(), DocumentType.CSV, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getClassSchedule/{classId}/csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public ResponseEntity getClassScheduleCSVDocument(HttpServletRequest request, HttpServletResponse response,
                                                      @PathVariable("classId") int classId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=class_schedule.csv");
            generationService.generateClassScheduleDocument(response.getOutputStream(), DocumentType.CSV, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getTeacherSchedule/{teacherId}/csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public ResponseEntity getTeacherScheduleCSVDocument(HttpServletRequest request, HttpServletResponse response,
                                                        @PathVariable("teacherId") int teacherId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=teacher_schedule.csv");
            generationService.generateTeacherScheduleDocument(response.getOutputStream(), DocumentType.CSV, teacherId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    @RequestMapping(value = "/getFullSchedule/csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public ResponseEntity getFullScheduleCSVDocument(HttpServletRequest request, HttpServletResponse response)
            throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=full_schedule.csv");
            generationService.generateFullScheduleDocument(response.getOutputStream(), DocumentType.CSV);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    @RequestMapping(value = "/getMarks/class/{classId}/subject/{subjectId}/csv",
            method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public ResponseEntity getMarksCSVDocument(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable("classId") int classId,
                                   @PathVariable("subjectId") int subjectId)
            throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=marks.csv");
            generationService.generateMarksDocument(response.getOutputStream(), DocumentType.CSV, subjectId, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    //PDF-----------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getClassPupilsList/{classId}/pdf", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public ResponseEntity getClassPupilsListPDFDocument(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable("classId") int classId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=pupils.pdf");
            generationService.generateClassPupilListDocument(response.getOutputStream(), DocumentType.PDF, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getClassSchedule/{classId}/pdf", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public ResponseEntity getClassSchedulePDFDocument(HttpServletRequest request, HttpServletResponse response,
                                                      @PathVariable("classId") int classId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=class_schedule.pdf");
            generationService.generateClassScheduleDocument(response.getOutputStream(), DocumentType.PDF, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getTeacherSchedule/{teacherId}/pdf", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public ResponseEntity getTeacherSchedulePDFDocument(HttpServletRequest request, HttpServletResponse response,
                                                      @PathVariable("teacherId") int teacherId) throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=teacher_schedule.pdf");
            generationService.generateTeacherScheduleDocument(response.getOutputStream(), DocumentType.PDF, teacherId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    @RequestMapping(value = "/getFullSchedule/pdf", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public ResponseEntity getFullSchedulePDFDocument(HttpServletRequest request, HttpServletResponse response)
            throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=full_schedule.pdf");
            generationService.generateFullScheduleDocument(response.getOutputStream(), DocumentType.PDF);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    @RequestMapping(value = "/getMarks/class/{classId}/subject/{subjectId}/pdf",
            method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public ResponseEntity getMarksPDFDocument(HttpServletRequest request, HttpServletResponse response,
                                                        @PathVariable("classId") int classId,
                                                        @PathVariable("subjectId") int subjectId)
            throws ControllerException {
        ResponseEntity responseEntity = null;
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=marks.pdf");
            generationService.generateMarksDocument(response.getOutputStream(), DocumentType.PDF, subjectId, classId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException | IOException exc) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
