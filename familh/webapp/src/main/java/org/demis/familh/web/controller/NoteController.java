package org.demis.familh.web.controller;

import org.demis.familh.core.jpa.entity.Note;
import org.demis.familh.core.service.GenericService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NoteService;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.converter.NoteConverterWeb;
import org.demis.familh.web.dto.NoteDTOWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(RestConfiguration.REST_BASE_URL)
public class NoteController extends GenericController<Note, NoteDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    @Qualifier("noteService" )
    private NoteService noteService;

    @Autowired
    @Qualifier("noteConverterWeb" )
    private NoteConverterWeb noteConverter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note",
            "/user/{userId}/familyTree/{familyTreeId}/note/"})
    @ResponseBody
    public List<NoteDTOWeb> getNotes(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<NoteDTOWeb> dtos = null;
        Range range = null;

        if (request.getHeader("Range") != null) {
            try {
                range = Range.parse(request.getHeader("Range"));
            } catch (RequestedRangeUnsatisfiableException e) {
                LOGGER.warn("Wrong format for the range parameter. The format is: \"resources: page=[page-number];size=[page-size]\" and the parameter value is: " + request.getHeader("Range"));
                response.setStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value());
                return null;
            }
        }

        if (range != null) {
            List<Note> models = getService().findPart(range.getPage(), range.getSize());
            if (models.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
            } else {
                response.setHeader(HttpHeaders.CONTENT_RANGE, "resources " + range.getStart() + "-" + Math.min(range.getEnd(), models.size()) + "/*");
                response.setStatus(HttpStatus.OK.value());
                dtos = getConverter().convertModels(models, request);
            }
        } else {
            dtos = getConverter().convertModels(getService().findAll(), request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}/"})
    public Object getNote(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Note note = noteService.findById(id);
        if (note != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, note.getUpdated().getTime());
            NoteDTOWeb dto = noteConverter.convertModel(note, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postNote() {
    }

    @RequestMapping(method = RequestMethod.POST, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note",
            "/user/{userId}/familyTree/{familyTreeId}/note/"})
    @ResponseBody
    public Object postNote(@RequestBody NoteDTOWeb noteDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        Note note = noteService.create(noteConverter.convertDTO(noteDTO));
        if (note != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, note.getUpdated().getTime());
            NoteDTOWeb dto = noteConverter.convertModel(note, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.DELETE, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note",
            "/user/{userId}/familyTree/{familyTreeId}/note/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteNotes() {
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}/"})
    @ResponseBody
    public Object deleteNote(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        Note note = noteService.findById(id);
        if (note != null) {
            try {
                noteService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the note: " + note, e);
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return null;
            }
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // PUT
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.PUT, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note",
            "/user/{userId}/familyTree/{familyTreeId}/note/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putNotes() {
    }

    @RequestMapping(method = RequestMethod.PUT, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}/"})
    @ResponseBody
    public Object putNote(@PathVariable("id") Long id, @RequestBody NoteDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        Note note = noteService.findById(id);
        if (note != null) {
            noteConverter.updateModel(note, dto);
            try {
                Note result = noteService.update(note);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                NoteDTOWeb resultDto = noteConverter.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the note: " + note, e);
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return null;
            }
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // OPTIONS
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.OPTIONS, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note",
            "/user/{userId}/familyTree/{familyTreeId}/note/"})
    @ResponseStatus(HttpStatus.OK)
    public void optionsNotes(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.HEAD, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note",
            "/user/{userId}/familyTree/{familyTreeId}/note/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headNotes(){
    }

    @RequestMapping(method = RequestMethod.HEAD, value = {
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/note/{id}/"})
    public void headNote(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        Note note = noteService.findById(id);
        if (note != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, note.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return noteConverter;
    }

    @Override
    protected GenericService<Note> getService() {
        return noteService;
    }

}
