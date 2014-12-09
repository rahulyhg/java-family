package org.demis.familh.web.controller;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.*;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.converter.*;
import org.demis.familh.web.dto.PersonDTOWeb;
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

@RestController
@RequestMapping(RestConfiguration.REST_BASE_URL)
public class PersonController extends GenericController<Person, PersonDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    @Autowired
    @Qualifier("personService" )
    private PersonService personService;

    @Autowired
    @Qualifier("personConverterWeb" )
    private PersonConverterWeb personConverter;

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

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person",
            "/user/{userId}/familyTree/{familyTreeId}/person/"
    })
    @ResponseBody
    public List<PersonDTOWeb> getPersons(@PathVariable(value = "userId") Long userId,
                                         @PathVariable(value = "familyTreeId") Long familyTreeId,
                                         HttpServletRequest request,
                                         HttpServletResponse httpResponse) {
        httpResponse.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<PersonDTOWeb> dtos = null;
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
        else {
            range = new Range(0, configuration.getDefaultPageSize());
        }

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            LOGGER.info("The familyTree #" + familyTreeId + " don't exist for user #" + userId + ", no persons returned");
            return dtos;
        }

        List<Person> models = personService.findFamilyTreePersons(familyTree);
        // TODO add range to the find method
        if (models == null || models.isEmpty()) {
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            httpResponse.setHeader(HttpHeaders.CONTENT_RANGE.toString(), "resources " + range.getStart() + "-" + Math.min(range.getEnd(), models.size()) + "/*");
            httpResponse.setStatus(HttpStatus.OK.value());
            dtos = getConverter().convertModels(models, request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}/"
        }, method = RequestMethod.GET)
    public Object getPerson(@PathVariable(value = "userId") Long userId,
                            @PathVariable(value = "familyTreeId") Long familyTreeId,
                            @PathVariable(value = "id") Long id,
                            HttpServletResponse httpResponse,
                            HttpServletRequest request) {

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        Person person = personService.findById(id);
        if (checkPerson(familyTree, person)) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
            PersonDTOWeb dto = personConverter.convertModel(person, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // TODO move to EventController
    @ResponseBody
    @RequestMapping(value = {"/person/{id}/event","/person/{id}/event/"}, method = RequestMethod.GET)
    public Object getPersonEvents(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.findById(id);
        if (person != null) {
            List<Event> events = eventService.findPersonEvents(person);
            httpResponse.setStatus(HttpStatus.OK.value());
            return eventConverter.convertModels(events, request);
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}/"},
        method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postPerson() {
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person",
            "/user/{userId}/familyTree/{familyTreeId}/person/"},
        method = RequestMethod.POST)
    @ResponseBody
    public Object postPerson(@PathVariable(value = "userId") Long userId,
                             @PathVariable(value = "familyTreeId") Long familyTreeId,
                             @RequestBody PersonDTOWeb personDTO,
                             HttpServletResponse httpResponse,
                             HttpServletRequest request) {

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            LOGGER.info("The familyTree #" + familyTreeId + " don't exist for user #" + userId + ", no persons returned");
            return null;
        }

        Person person = personConverter.convertDTO(personDTO);
        person.setFamilyTree(familyTree);
        person.setUser(user);

        person = personService.create(person);

        httpResponse.setStatus(HttpStatus.OK.value());
        httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
        PersonDTOWeb dto = personConverter.convertModel(person, request);
        return dto;
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person",
            "/user/{userId}/familyTree/{familyTreeId}/person/"},
        method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deletePersons() {
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}/"},
        method = RequestMethod.DELETE)
    @ResponseBody
    public Object deletePerson(@PathVariable(value = "userId") Long userId,
                               @PathVariable(value = "familyTreeId") Long familyTreeId,
                               @PathVariable(value = "id") Long id,
                               HttpServletResponse httpResponse) {

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        Person person = personService.findById(id);
        if (checkPerson(familyTree, person)) {
            try {
                personService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the person: " + person, e);
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

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person",
            "/user/{userId}/familyTree/{familyTreeId}/person/"},
        method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putPersons() {
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}/"},
        method = RequestMethod.PUT)
    @ResponseBody
    public Object putPerson(@PathVariable(value = "userId") Long userId,
                            @PathVariable(value = "familyTreeId") Long familyTreeId,
                            @PathVariable("id") Long id,
                            @RequestBody PersonDTOWeb dto,
                            HttpServletResponse httpResponse,
                            HttpServletRequest request) {

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        Person person = personService.findById(id);
        if (checkPerson(familyTree, person)) {
            personConverter.updateModel(person, dto);
            try {
                Person result = personService.update(person);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                PersonDTOWeb resultDto = personConverter.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the person: " + person, e);
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

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person",
            "/user/{userId}/familyTree/{familyTreeId}/person/"},
        method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsPersons(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}/"},
        method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person",
            "/user/{userId}/familyTree/{familyTreeId}/person/"},
        method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headPersons(){
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{id}/"},
        method = RequestMethod.HEAD)
    public void headPerson(@PathVariable(value = "userId") Long userId,
                           @PathVariable(value = "familyTreeId") Long familyTreeId,
                           @PathVariable(value = "id") Long id,
                           HttpServletResponse httpResponse){
        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        Person person = personService.findById(id);
        if (checkPerson(familyTree, person)) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    private boolean checkPerson(FamilyTree familyTree, Person person) {
        return person != null
                && person.getFamilyTree() != null
                && person.getFamilyTree().equals(familyTree);
    }

    private boolean checkFamilyTree(User user, FamilyTree familyTree) {
        return user != null
                && familyTree != null
                && familyTree.getUser() != null
                && familyTree.getUser().equals(user);
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return personConverter;
    }

    @Override
    protected GenericService getService() {
        return personService;
    }
}
