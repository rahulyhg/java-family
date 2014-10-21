package org.demis.familh.web.controller;

import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.core.service.GenericService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.NameService;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.converter.NameConverterWeb;
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
    @Qualifier("nameService" )
    private NameService nameService;

    @Autowired
    @Qualifier("nameConverterWeb" )
    private NameConverterWeb nameConverter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/name", "/name/"})
    @ResponseBody
    public List<NameDTOWeb> getNames(HttpServletRequest request, HttpServletResponse httpResponse) {
        httpResponse.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<NameDTOWeb> dtos = null;
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
            List<Name> names = nameService.findPart(range.getPage(), range.getSize());
            if (names.isEmpty()) {
                httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            } else {
                httpResponse.setHeader(HttpHeaders.CONTENT_RANGE, "resources " + range.getStart() + "-" + Math.min(range.getEnd(), names.size()) + "/*");
                httpResponse.setStatus(HttpStatus.OK.value());
                dtos = nameConverter.convertModels(names, request);
            }
        } else {
            dtos = nameConverter.convertModels(nameService.findAll(), request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(value = {"/name/{id}","/name/{id}/"}, method = RequestMethod.GET)
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

    @RequestMapping(value = {"/name/{id}", "/name/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postResource() {
    }

    @RequestMapping(value = {"/name", "/name/"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"/name", "/name/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteResources() {
    }


    @RequestMapping(value = {"/name/{id}", "/name/{id}/"}, method = RequestMethod.DELETE)
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

    @RequestMapping(value = {"/name", "/name/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putResources() {
    }

    @RequestMapping(value = {"/name/{id}", "/name/{id}/"}, method = RequestMethod.PUT)
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

    @RequestMapping(value = {"/name", "/name/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResources(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/name/{id}", "/name/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/name", "/name/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headResources(){
    }

    @RequestMapping(value = {"/name/{id}", "/name/{id}/"}, method = RequestMethod.HEAD)
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

    @Override
    protected GenericService getService() {
        return nameService;
    }
}
