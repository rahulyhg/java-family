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
@RequestMapping(RestConfiguration.REST_BASE_URL + "person")
public class PersonController extends GenericController<Person, PersonDTOWeb> {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

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

    @RequestMapping(method = RequestMethod.GET, value = {"", "/"})
    @ResponseBody
    public List<PersonDTOWeb> getPersons(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<PersonDTOWeb> dtos = null;
        Range range = null;

        if (request.getHeader("Range") != null) {
            range = Range.parse(request.getHeader("Range"));
        }

        if (range != null) {
            List<Person> models = getService().findPart(range.getPage(), range.getSize());
            if (models.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
            }
            else {
                response.setHeader(HttpHeaders.CONTENT_RANGE, "resources " + range.getStart() + "-" + Math.min(range.getEnd(), models.size()) + "/*");
                response.setStatus(HttpStatus.OK.value());
                dtos = getConverter().convertModels(models, request);
            }
        }
        else {
            dtos = getConverter().convertModels(getService().findAll(), request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(value = {"/{id}","/{id}/"}, method = RequestMethod.GET)
    public Object getPerson(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.findById(id);
        if (person != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
            PersonDTOWeb dto = personConverter.convertModel(person, request);
            return dto;
        }
        else {
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
        }
        else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/{id}/event","/{id}/event/"}, method = RequestMethod.GET)
    public Object getPersonEvents(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.findById(id);
        if (person != null) {
            List<Event> events = eventService.findPersonEvents(person);
            httpResponse.setStatus(HttpStatus.OK.value());
            return eventConverter.convertModels(events, request);
        }
        else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postPerson() {
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postPerson(@RequestBody PersonDTOWeb personDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.create(personConverter.convertDTO(personDTO));
        if (person != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
            PersonDTOWeb dto = personConverter.convertModel(person, request);
            return dto;
        }
        else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    @RequestMapping(value = {"{id}/name", "{id}/name/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postPersonName(@PathVariable(value = "id") Long personId, @RequestBody NameDTOWeb nameDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        Person person = personService.findById(personId);
        if (person != null) {
            Name name = nameConverter.convertDTO(nameDTO);
            name.setPerson(person);
            nameService.create(name);
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, name.getUpdated().getTime());
            return nameConverter.convertModel(name, request);
        }
        else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }
/*
    @RequestMapping(value = {"person/{id}/event", "person/{id}/event/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postPersonEvent(@PathVariable(value = "id") Long personId, @RequestBody EventDTOWeb eventDTO, HttpServletResponse httpResponse) {
        Person person = personService.findById(personId);
        if (person != null) {
            Event event = eventConverter.convertDTO(eventDTO);
            person.addEvent(event, );
            nameService.create(event);
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, event.getUpdated().getTime());
            return nameConverter.convertModel(event);
        }
        else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }
*/
    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"", "/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deletePersons() {
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deletePerson(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        Person person = personService.findById(id);
        if (person != null) {
            try {
                personService.delete(id);
            } catch (ModelNotFoundException e) {
                logger.warn("Can't delete the person: " + person, e);
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return null;
            }
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        }
        else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // PUT
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"", "/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putPersons() {
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.PUT)
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
                logger.warn("Can't modify the person: " + person, e);
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return null;
            }
        }
        else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // OPTIONS
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"", "/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsPersons(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"", "/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headPersons(HttpServletResponse httpResponse){
    }

    @RequestMapping(value = {"{id}", "{id}/"}, method = RequestMethod.HEAD)
    public void headPerson(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        Person person = personService.findById(id);
        if (person != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, person.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        }
        else {
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
