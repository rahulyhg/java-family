package org.demis.familh.web.controller;

import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.GenericService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.UserService;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.converter.UserConverterWeb;
import org.demis.familh.web.dto.UserDTOWeb;
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
public class UserController extends GenericController<User, UserDTOWeb> {

    @Autowired
    @Qualifier("userService" )
    private UserService userService;

    @Autowired
    @Qualifier("userConverterWeb" )
    private UserConverterWeb userConverter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/user", "/user/"})
    @ResponseBody
    public List<UserDTOWeb> getUsers(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<UserDTOWeb> dtos = null;
        Range range = null;

        if (request.getHeader("Range") != null) {
            range = Range.parse(request.getHeader("Range"));
        }

        if (range != null) {
            List<User> models = getService().findPart(range.getPage(), range.getSize());
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
    @RequestMapping(value = {"/user/{id}","/user/{id}/"}, method = RequestMethod.GET)
    public Object getUser(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        User user = userService.findById(id);
        if (user != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, user.getUpdated().getTime());
            UserDTOWeb dto = userConverter.convertModel(user, request);
            return dto;
        }
        else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user/{id}", "/user/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postUser(HttpServletResponse httpResponse) {
    }

    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postUser(@RequestBody UserDTOWeb userDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        User user = userService.create(userConverter.convertDTO(userDTO));
        if (user != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, user.getUpdated().getTime());
            UserDTOWeb dto = userConverter.convertModel(user, request);
            return dto;
        }
        else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteUsers(HttpServletResponse httpResponse) {
    }

    @RequestMapping(value = {"/user/{id}", "/user/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteUser(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        User user = userService.findById(id);
        if (user != null) {
            try {
                userService.delete(id);
            } catch (ModelNotFoundException e) {
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

    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putUsers(HttpServletResponse httpResponse) {
    }

    @RequestMapping(value = {"/user/{id}", "/user/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putUser(@PathVariable("id") Long id, @RequestBody UserDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        User user = userService.findById(id);
        if (user != null) {
            userConverter.updateModel(user, dto);
            try {
                User result = userService.update(user);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                UserDTOWeb resultDto = userConverter.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
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

    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsUsers(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/user/{id}", "/user/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headUsers(HttpServletResponse httpResponse){
    }

    @RequestMapping(value = {"/user/{id}", "/user/{id}/"}, method = RequestMethod.HEAD)
    public void headUser(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        User user = userService.findById(id);
        if (user != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, user.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        }
        else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
    
    @Override
    protected GenericConverterWeb getConverter() {
        return userConverter;
    }

    @Override
    protected GenericService<User> getService() {
        return userService;
    }
}
