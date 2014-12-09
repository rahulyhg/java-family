package org.demis.familh.web.controller;

import org.demis.familh.core.service.GenericService;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class GenericController<M, DTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NameController.class);

    protected abstract GenericConverterWeb getConverter();

    protected  abstract GenericService<M> getService();

    protected List<DTO> getResources(HttpServletRequest request, HttpServletResponse httpResponse) {
        httpResponse.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<DTO> dtos = null;
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
            List<M> models = getService().findPart(range.getPage(), range.getSize());
            if (models.isEmpty()) {
                httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            } else {
                httpResponse.setHeader(HttpHeaders.CONTENT_RANGE.toString(), "resources " + range.getStart() + "-" + Math.min(range.getEnd(), models.size()) + "/*");
                httpResponse.setStatus(HttpStatus.OK.value());
                dtos = getConverter().convertModels(models, request);
            }
        } else {
            dtos = getConverter().convertModels(getService().findAll(), request);
        }
        return dtos;
    }

}
