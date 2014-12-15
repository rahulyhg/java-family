package org.demis.familh.web.controller;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.EventService;
import org.demis.familh.core.service.FamilyTreeService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.PersonService;
import org.demis.familh.core.service.UserService;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.controller.exception.RangeException;
import org.demis.familh.web.converter.EventConverterWeb;
import org.demis.familh.web.converter.FamilyTreeConverterWeb;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.converter.PersonConverterWeb;
import org.demis.familh.web.converter.UserConverterWeb;
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

    @Autowired
    @Qualifier("userService" )
    private UserService userService;

    @Autowired
    @Qualifier("userConverterWeb" )
    private UserConverterWeb userConverter;

    @Autowired
    @Qualifier("familyTreeService" )
    private FamilyTreeService familyTreeService;

    @Autowired
    @Qualifier("familyTreeConverterWeb" )
    private FamilyTreeConverterWeb familyTreeConverter;

    @Autowired
    @Qualifier("personService" )
    private PersonService personService;

    @Autowired
    @Qualifier("personConverterWeb" )
    private PersonConverterWeb personConverter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET,
            value = {"/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event",
                    "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/event/"})
    @ResponseBody
    public List<EventDTOWeb> getEvents(@PathVariable(value = "userId") Long userId,
                                       @PathVariable(value = "familyTreeId") Long familyTreeId,
                                       @PathVariable(value = "personId") Long personId,
                                       HttpServletRequest request,
                                       HttpServletResponse httpResponse,
                                       @RequestParam(value="sort", required = false) String sortParameters) throws RangeException {
        httpResponse.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<EventDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<Sort> sorts = getSorts(sortParameters);

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);
        Person person = personService.findById(personId);

        if (range != null) {
            List<Event> events = eventService.findPersonEvents(person, range, sorts);
            if (events.isEmpty()) {
                httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            } else {
                httpResponse.setHeader(HttpHeaders.CONTENT_RANGE.toString(), "resources " + range.getStart() + "-" + Math.min(range.getEnd(), events.size()) + "/*");
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
}
