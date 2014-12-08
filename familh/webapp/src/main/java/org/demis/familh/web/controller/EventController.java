package org.demis.familh.web.controller;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.service.EventService;
import org.demis.familh.core.service.GenericService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.converter.EventConverterWeb;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.dto.EventDTOWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

// TODO add user, familyTree, person to event

@RestController
@RequestMapping(RestConfiguration.REST_BASE_URL)
public class EventController extends GenericController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    @Qualifier("eventService" )
    private EventService eventService;

    @Autowired
    @Qualifier("eventConverterWeb" )
    private EventConverterWeb eventConverter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET,
            value = {"/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event",
                    "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/"})
    @ResponseBody
    public List<EventDTOWeb> getEvents(HttpServletRequest request, HttpServletResponse httpResponse) {
        httpResponse.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<EventDTOWeb> dtos = null;
        Range range = null;

        if (request.getHeader("Range") != null) {
            try {
                range = Range.parse(request.getHeader("Range"));
            } catch (RequestedRangeUnsatisfiableException e) {
                LOGGER.warn("Wrong format for the range parameter. The format is: \"resources: page=[page-number];size=[page-size]\" and the parameter value is: " + request.getHeader("Range"));
                httpResponse.setStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value());
                return null;
            }
        }

        if (range != null) {
            List<Event> events = eventService.findPart(range.getPage(), range.getSize());
            if (events.isEmpty()) {
                httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            } else {
                httpResponse.setHeader(HttpHeaders.CONTENT_RANGE, "resources " + range.getStart() + "-" + Math.min(range.getEnd(), events.size()) + "/*");
                httpResponse.setStatus(HttpStatus.OK.value());
                dtos = eventConverter.convertModels(events, request);
            }
        } else {
            dtos = eventConverter.convertModels(eventService.findAll(), request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}/"})
    public Object getEvent(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Event event = eventService.findById(id);
        if (event != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            EventDTOWeb dto = eventConverter.convertModel(event, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}", "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postResource() {
    }

    @RequestMapping(value = {"/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event", "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postResource(@RequestBody EventDTOWeb eventDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        Event event = eventService.create(eventConverter.convertDTO(eventDTO));
        if (event != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            EventDTOWeb dto = eventConverter.convertModel(event, request);
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
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteResources() {
    }


    @RequestMapping(method = RequestMethod.DELETE, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}/"})
    @ResponseBody
    public Object deleteResource(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        Event event = eventService.findById(id);
        if (event != null) {
            try {
                eventService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete event: " + event, e);
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
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putResources() {
    }

    @RequestMapping(method = RequestMethod.PUT, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}/"})
    @ResponseBody
    public Object putResource(@PathVariable("id") Long id, @RequestBody EventDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        Event event = eventService.findById(id);
        if (event != null) {
            eventConverter.updateModel(event, dto);
            try {
                Event result = eventService.update(event);
                EventDTOWeb resultDto = eventConverter.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify event: " + event, e);
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
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/"})
    @ResponseStatus(HttpStatus.OK)
    public void optionsResources(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.HEAD, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headResources(){
    }

    @RequestMapping(method = RequestMethod.HEAD, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/{id}/"})
    public void headResource(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        Event event = eventService.findById(id);
        if (event != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, event.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return eventConverter;
    }

    @Override
    protected GenericService getService() {
        return eventService;
    }
}
