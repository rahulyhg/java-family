package org.demis.familh.web.controller;

import org.demis.familh.core.Range;
import org.demis.familh.core.jpa.entity.Family;
import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.*;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.controller.exception.RangeException;
import org.demis.familh.web.converter.*;
import org.demis.familh.web.dto.FamilyDTOWeb;
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
public class FamilyController extends GenericController<Family, FamilyDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyController.class);

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    @Autowired
    @Qualifier("familyService" )
    private FamilyService familyService;

    @Autowired
    @Qualifier("familyConverterWeb" )
    private FamilyConverterWeb familyConverter;

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
            "/user/{userId}/familyTree/{familyTreeId}/family",
            "/user/{userId}/familyTree/{familyTreeId}/family/"
    })
    @ResponseBody
    public List<FamilyDTOWeb> getFamilys(@PathVariable(value = "userId") Long userId,
                                         @PathVariable(value = "familyTreeId") Long familyTreeId,
                                         HttpServletRequest request,
                                         HttpServletResponse httpResponse) throws RangeException {
        httpResponse.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<FamilyDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return dtos;
        }

        List<Family> models = familyService.findFamilyTreeFamilies(familyTree);
        // TODO add range to the find method
        if (models.isEmpty()) {
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
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}/"
    }, method = RequestMethod.GET)
    public Object getFamily(@PathVariable(value = "userId") Long userId,
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

        Family family = familyService.findById(id);
        if (checkFamily(familyTree, family)) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, family.getUpdated().getTime());
            FamilyDTOWeb dto = familyConverter.convertModel(family, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}/"},
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postFamily() {
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/family",
            "/user/{userId}/familyTree/{familyTreeId}/family/"},
            method = RequestMethod.POST)
    @ResponseBody
    public Object postFamily(@PathVariable(value = "userId") Long userId,
                             @PathVariable(value = "familyTreeId") Long familyTreeId,
                             @RequestBody FamilyDTOWeb familyDTO,
                             HttpServletResponse httpResponse,
                             HttpServletRequest request) {

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        Family family = familyService.create(familyConverter.convertDTO(familyDTO));

        if (family != null) {
            family.setFamilyTree(familyTree);
            family.setUser(user);

            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, family.getUpdated().getTime());
            FamilyDTOWeb dto = familyConverter.convertModel(family, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/family",
            "/user/{userId}/familyTree/{familyTreeId}/family/"},
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteFamilys() {
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}/"},
            method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteFamily(@PathVariable(value = "userId") Long userId,
                               @PathVariable(value = "familyTreeId") Long familyTreeId,
                               @PathVariable(value = "id") Long id,
                               HttpServletResponse httpResponse) {

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        Family family = familyService.findById(id);
        if (checkFamily(familyTree, family)) {
            try {
                familyService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the family: " + family, e);
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
            "/user/{userId}/familyTree/{familyTreeId}/family",
            "/user/{userId}/familyTree/{familyTreeId}/family/"},
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putFamilys() {
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}/"},
            method = RequestMethod.PUT)
    @ResponseBody
    public Object putFamily(@PathVariable(value = "userId") Long userId,
                            @PathVariable(value = "familyTreeId") Long familyTreeId,
                            @PathVariable("id") Long id,
                            @RequestBody FamilyDTOWeb dto,
                            HttpServletResponse httpResponse,
                            HttpServletRequest request) {

        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        Family family = familyService.findById(id);
        if (checkFamily(familyTree, family)) {
            familyConverter.updateModel(family, dto);
            try {
                Family result = familyService.update(family);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                FamilyDTOWeb resultDto = familyConverter.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the family: " + family, e);
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
            "/user/{userId}/familyTree/{familyTreeId}/family",
            "/user/{userId}/familyTree/{familyTreeId}/family/"},
            method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsFamilys(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}/"},
            method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/family",
            "/user/{userId}/familyTree/{familyTreeId}/family/"},
            method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headFamilys(){
    }

    @RequestMapping(value = {
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}",
            "/user/{userId}/familyTree/{familyTreeId}/family/{id}/"},
            method = RequestMethod.HEAD)
    public void headFamily(@PathVariable(value = "userId") Long userId,
                           @PathVariable(value = "familyTreeId") Long familyTreeId,
                           @PathVariable(value = "id") Long id,
                           HttpServletResponse httpResponse){
        User user = userService.findById(userId);
        FamilyTree familyTree = familyTreeService.findById(familyTreeId);

        if (!checkFamilyTree(user, familyTree)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        Family family = familyService.findById(id);
        if (checkFamily(familyTree, family)) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, family.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    private boolean checkFamily(FamilyTree familyTree, Family family) {
        return family != null
                && family.getFamilyTree() != null
                && family.getFamilyTree().equals(familyTree);
    }

    private boolean checkFamilyTree(User user, FamilyTree familyTree) {
        return user != null
                && familyTree != null
                && familyTree.getUser() != null
                && familyTree.getUser().equals(user);
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return familyConverter;
    }

    protected GenericService getService() {
        return familyService;
    }
}
