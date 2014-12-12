package org.demis.familh.web.controller;

import org.demis.familh.core.Range;
import org.demis.familh.core.Sort;
import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.core.service.UserService;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.controller.exception.RangeException;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.converter.UserConverterWeb;
import org.demis.familh.web.dto.UserDTOWeb;
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
public class UserController extends GenericController<User, UserDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("userService" )
    private UserService userService;

    @Autowired
    @Qualifier("userConverterWeb" )
    private UserConverterWeb userConverter;

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/user", "/user/"})
    @ResponseBody
    public List<UserDTOWeb> getUsers(@RequestParam(value="sort", required = false) String sortParameters,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws RangeException {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<UserDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<Sort> sorts = getSorts(sortParameters);

        List<User> models = userService.findPart(range, sorts);
        if (models.isEmpty()) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            response.setHeader(HttpHeaders.CONTENT_RANGE.toString(), "resources " + range.getStart() + "-" + Math.min(range.getEnd(), models.size()) + "/*");
            response.setStatus(HttpStatus.OK.value());
            dtos = getConverter().convertModels(models, request);
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
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user/{id}", "/user/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postUser() {
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
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteUsers() {
    }

    @RequestMapping(value = {"/user/{id}", "/user/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteUser(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        User user = userService.findById(id);
        if (user != null) {
            try {
                userService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the user: " + user, e);
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

    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putUsers() {
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
                LOGGER.warn("Can't modify the user: " + user, e);
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
    public void headUsers(){
    }

    @RequestMapping(value = {"/user/{id}", "/user/{id}/"}, method = RequestMethod.HEAD)
    public void headUser(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        User user = userService.findById(id);
        if (user != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, user.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
    
    @Override
    protected GenericConverterWeb getConverter() {
        return userConverter;
    }


}
