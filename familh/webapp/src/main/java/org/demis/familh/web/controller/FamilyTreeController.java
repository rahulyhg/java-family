package org.demis.familh.web.controller;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.FamilyTreeService;
import org.demis.familh.core.service.GenericService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.UserService;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.converter.FamilyTreeConverterWeb;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.converter.UserConverterWeb;
import org.demis.familh.web.dto.FamilyTreeDTOWeb;
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
public class FamilyTreeController extends GenericController<FamilyTree, FamilyTreeDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyTreeController.class);

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

    @ResponseBody
    @RequestMapping(value = {"/user/{userId}/familyTree","/user/{userId}/familyTree/"}, method = RequestMethod.GET)
    public Object getFamilyTrees(@PathVariable(value = "userId") Long userId,
                                     HttpServletResponse httpResponse,
                                     HttpServletRequest request) {
        httpResponse.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");
        List<FamilyTreeDTOWeb> dtos = null;
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

        User user = userService.findById(userId);
        if (user != null) {
            if (range != null) {
                List<FamilyTree> trees = familyTreeService.findUserFamilyTrees(user);
                if (trees.isEmpty()) {
                    httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
                } else {
                    httpResponse.setHeader(HttpHeaders.CONTENT_RANGE, "resources " + range.getStart() + "-" + Math.min(range.getEnd(), trees.size()) + "/*");
                    httpResponse.setStatus(HttpStatus.OK.value());
                    dtos = getConverter().convertModels(trees, request);
                }
            }
            else {
                dtos = getConverter().convertModels(getService().findAll(), request);
            }
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(value = {"/user/{userId}/familyTree/{familyTreeId}","/user/{userId}/familyTree/{familyTreeId}/"},
            method = RequestMethod.GET)
    public Object getFamilyTree(@PathVariable(value = "userId") Long userId,
                                    @PathVariable(value = "familyTreeId") Long familyTreeId,
                                    HttpServletResponse httpResponse,
                                    HttpServletRequest request) {
        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);
        if (user != null && familyTree != null && familyTree.getUser().getId() == userId) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return familyTreeConverter.convertModel(familyTree, request);
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user/{userId}/familyTree/{familyTreeId}", "/user/{userId}/familyTree/{familyTreeId}/"},
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postExistingFamilyTree() {
    }

    @RequestMapping(value = {"/user/{userId}/familyTree", "/user/{userId}/familyTree/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postFamilyTree(@PathVariable(value = "userId") Long userId,
                                 @RequestBody FamilyTreeDTOWeb familyTreeDTOWeb,
                                 HttpServletResponse httpResponse,
                                 HttpServletRequest request) {
        User user = userService.findById(userId);
        if (user != null) {
            FamilyTree familyTree = familyTreeConverter.convertDTO(familyTreeDTOWeb);
            familyTree.setUser(user);
            familyTreeService.create(familyTree);
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, familyTree.getUpdated().getTime());
            return familyTreeConverter.convertModel(familyTree, request);
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user/{userId}/familyTree", "/user/{userId}/familyTree/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteFamilyTrees() {
    }

    @RequestMapping(value = {"/user/{userId}/familyTree/{familyTreeId}", "/user/{userId}/familyTree/{familyTreeId}/"},
            method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteFamilyTree(@PathVariable(value = "userId") Long userId,
                                   @PathVariable(value = "familyTreeId") Long familyTreeId,
                                   HttpServletResponse httpResponse) {
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);
        if (familyTree != null && familyTree.getUser().getId() == userId) {
            try {
                familyTreeService.delete(familyTreeId);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete familyTree: " + familyTree, e);
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

    @RequestMapping(value = {"/user/{userId}/familyTree", "/user/{userId}/familyTree/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putFamilyTrees() {
    }

    @RequestMapping(value = {"/user/{userId}/familyTree/{familyTreeId}", "/user/{userId}/familyTree/{familyTreeId}/"},
            method = RequestMethod.PUT)
    @ResponseBody
    public Object putUser(@PathVariable("userId") Long userId,
                          @PathVariable("familyTreeId") Long familyTreeId,
                          @RequestBody FamilyTreeDTOWeb dto,
                          HttpServletResponse httpResponse,
                          HttpServletRequest request) {
        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);
        if (user != null && familyTree != null && familyTree.getUser().getId() == userId) {
            familyTreeConverter.updateModel(familyTree, dto);
            try {
                FamilyTree result = familyTreeService.update(familyTree);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                FamilyTreeDTOWeb resultDto = familyTreeConverter.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't familyTree name: " + user, e);
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

    @RequestMapping(value = {"/user/{userId}/familyTree", "/user/{userId}/familyTree/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsFamilyTrees(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/user/{userId}/familyTree/{familyTreeId}", "/user/{userId}/familyTree/{familyTreeId}/"},
            method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user/{userId}/familyTree", "/user/{userId}/familyTree/"},
            method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headFamilyTrees(){
    }

    @RequestMapping(value = {"/user/{userId}/familyTree/{familyTreeId}", "/user/{userId}/familyTree/{familyTreeId}/"},
            method = RequestMethod.HEAD)
    public void headFamilyTree(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        FamilyTree familyTree = familyTreeService.findById(id);
        if (familyTree != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, familyTree.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
    
    @Override
    protected GenericConverterWeb getConverter() {
        return familyTreeConverter;
    }

    @Override
    protected GenericService<FamilyTree> getService() {
        return familyTreeService;
    }
}
