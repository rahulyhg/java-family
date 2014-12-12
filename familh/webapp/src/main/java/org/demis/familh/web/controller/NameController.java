package org.demis.familh.web.controller;

import org.demis.familh.core.Range;
import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.service.*;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.controller.exception.RangeException;
import org.demis.familh.web.converter.*;
import org.demis.familh.web.dto.NameDTOWeb;
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
public class NameController extends GenericController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NameController.class);

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    @Autowired
    @Qualifier("nameService" )
    private NameService nameService;

    @Autowired
    @Qualifier("nameConverterWeb" )
    private NameConverterWeb nameConverter;

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

    @RequestMapping(method = RequestMethod.GET, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/"})
    @ResponseBody
    public List<NameDTOWeb> getNames(@PathVariable(value = "userId") Long userId,
                                     @PathVariable(value = "familyTreeId") Long familyTreeId,
                                     @PathVariable(value = "personId") Long personId,
                                     HttpServletRequest request, HttpServletResponse httpResponse) throws RangeException {
        httpResponse.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<NameDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));

        List<Name> names = nameService.findPart(range.getPage(), range.getSize());
        if (names.isEmpty()) {
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            httpResponse.setHeader(HttpHeaders.CONTENT_RANGE.toString(), "resources " + range.getStart() + "-" + Math.min(range.getEnd(), names.size()) + "/*");
            httpResponse.setStatus(HttpStatus.OK.value());
            dtos = nameConverter.convertModels(names, request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}/"})
    public Object getName(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Name name = nameService.findById(id);
        if (name != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            NameDTOWeb dto = nameConverter.convertModel(name, request);
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
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postResource() {
    }

    @RequestMapping(method = RequestMethod.POST, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/"})
    @ResponseBody
    public Object postResource(@RequestBody NameDTOWeb nameDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        Name name = nameService.create(nameConverter.convertDTO(nameDTO));
        if (name != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            NameDTOWeb dto = nameConverter.convertModel(name, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.DELETE, value = {"/name", "/name/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteResources() {
    }


    @RequestMapping(method = RequestMethod.DELETE, value = {"/name/{id}", "/name/{id}/"})
    @ResponseBody
    public Object deleteResource(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        Name name = nameService.findById(id);
        if (name != null) {
            try {
                nameService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete name: " + name, e);
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
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putResources() {
    }

    @RequestMapping(method = RequestMethod.PUT, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}/"})
    @ResponseBody
    public Object putResource(@PathVariable("id") Long id, @RequestBody NameDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        Name name = nameService.findById(id);
        if (name != null) {
            nameConverter.updateModel(name, dto);
            try {
                Name result = nameService.update(name);
                NameDTOWeb resultDto = nameConverter.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify name: " + name, e);
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
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/"})
    @ResponseStatus(HttpStatus.OK)
    public void optionsResources(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.HEAD, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/"})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headResources(){
    }

    @RequestMapping(method = RequestMethod.HEAD, value = {
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/person/{personId}/name/{id}/"})
    public void headResource(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        Name name = nameService.findById(id);
        if (name != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, name.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return nameConverter;
    }

    protected GenericService getService() {
        return nameService;
    }
}
