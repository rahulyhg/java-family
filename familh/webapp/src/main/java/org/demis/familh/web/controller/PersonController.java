package org.demis.familh.web.controller;

import org.demis.familh.core.jpa.entity.Event;
import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.jpa.entity.Person;
import org.demis.familh.core.service.*;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.converter.EventConverterWeb;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.converter.NameConverterWeb;
import org.demis.familh.web.converter.PersonConverterWeb;
import org.demis.familh.web.dto.NameDTOWeb;
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
    @Qualifier("personService" )
    private PersonService personService;

    @Autowired
    @Qualifier("nameService" )
    private NameService nameService;

    @Autowired
    @Qualifier("eventService" )
    private EventService eventService;

    @Autowired
    @Qualifier("personConverterWeb" )
    private PersonConverterWeb personConverter;

    @Autowired
    @Qualifier("nameConverterWeb" )
    private NameConverterWeb nameConverter;

    @Autowired
    @Qualifier("eventConverterWeb" )
    private EventConverterWeb eventConverter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/person", "/person/"})
    @ResponseBody
    public List<PersonDTOWeb> getPersons(HttpServletRequest request, HttpServletResponse httpResponse) {
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

        if (range != null) {
            List<Person> models = getService().findPart(range.getPage(), range.getSize());
            if (models.isEmpty()) {
                httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            } else {
                httpResponse.setHeader(HttpHeaders.CONTENT_RANGE, "resources " + range.getStart() + "-" + Math.min(range.getEnd(), models.size()) + "/*");
                httpResponse.setStatus(HttpStatus.OK.value());
                dtos = getConverter().convertModels(models, request);
            }
        } else {
            dtos = getConverter().convertModels(getService().findAll(), request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(value = {"/person/{id}","/person/{id}/"}, method = RequestMethod.GET)
    public Object getPerson(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.findById(id);
        if (person != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
            PersonDTOWeb dto = personConverter.convertModel(person, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/{id}/name","/{id}/name/"}, method = RequestMethod.GET)
    public Object getPersonNames(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.findById(id);
        if (person != null) {
            List<Name> names = nameService.findPersonNames(person);
            httpResponse.setStatus(HttpStatus.OK.value());
            return nameConverter.convertModels(names, request);
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

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

    @RequestMapping(value = {"/person/{id}", "/person/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postPerson() {
    }

    @RequestMapping(value = {"/person", "/person/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postPerson(@RequestBody PersonDTOWeb personDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.create(personConverter.convertDTO(personDTO));
        if (person != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
            PersonDTOWeb dto = personConverter.convertModel(person, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    @RequestMapping(value = {"/person/{id}/name", "/person/{id}/name/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postPersonName(@PathVariable(value = "id") Long personId, @RequestBody NameDTOWeb nameDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.findById(personId);
        if (person != null) {
            Name name = nameConverter.convertDTO(nameDTO);
            name.setPerson(person);
            nameService.create(name);
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, name.getUpdated().getTime());
            return nameConverter.convertModel(name, request);
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/person", "/person/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deletePersons() {
    }

    @RequestMapping(value = {"/person/{id}", "/person/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deletePerson(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        Person person = personService.findById(id);
        if (person != null) {
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

    @RequestMapping(value = {"/person", "/person/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putPersons() {
    }

    @RequestMapping(value = {"/person/{id}", "/person/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putPerson(@PathVariable("id") Long id, @RequestBody PersonDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.findById(id);
        if (person != null) {
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

    @RequestMapping(value = {"/person", "/person/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsPersons(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/person/{id}", "/person/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/person", "/person/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headPersons(){
    }

    @RequestMapping(value = {"/person/{id}", "/person/{id}/"}, method = RequestMethod.HEAD)
    public void headPerson(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        Person person = personService.findById(id);
        if (person != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
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
