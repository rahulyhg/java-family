package org.demis.familh.web.controller;

import org.demis.familh.core.jpa.entity.Document;
import org.demis.familh.core.service.DocumentService;
import org.demis.familh.core.service.GenericService;
import org.demis.familh.core.service.ModelNotFoundException;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.converter.DocumentConverterWeb;
import org.demis.familh.web.converter.GenericConverterWeb;
import org.demis.familh.web.dto.DocumentDTOWeb;
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
public class DocumentController extends GenericController<Document, DocumentDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    @Qualifier("documentService" )
    private DocumentService documentService;

    @Autowired
    @Qualifier("documentConverterWeb" )
    private DocumentConverterWeb documentConverter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/document", "/document/"})
    @ResponseBody
    public List<DocumentDTOWeb> getDocuments(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<DocumentDTOWeb> dtos = null;
        Range range = null;

        if (request.getHeader("Range") != null) {
            try {
                range = Range.parse(request.getHeader("Range"));
            } catch (RequestedRangeUnsatisfiableException e) {
                LOGGER.warn("Wrong format for the range parameter. The format is: \"resources: page=[page-number];size=[page-size]\" and the parameter value is: " + request.getHeader("Range"));
                response.setStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value());
                return null;
            }
        }

        if (range != null) {
            List<Document> models = getService().findPart(range.getPage(), range.getSize());
            if (models.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
            } else {
                response.setHeader(HttpHeaders.CONTENT_RANGE.toString(), "resources " + range.getStart() + "-" + Math.min(range.getEnd(), models.size()) + "/*");
                response.setStatus(HttpStatus.OK.value());
                dtos = getConverter().convertModels(models, request);
            }
        } else {
            dtos = getConverter().convertModels(getService().findAll(), request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(value = {"/document/{id}","/document/{id}/"}, method = RequestMethod.GET)
    public Object getDocument(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        Document document = documentService.findById(id);
        if (document != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, document.getUpdated().getTime());
            DocumentDTOWeb dto = documentConverter.convertModel(document, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/document/{id}", "/document/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postDocument() {
    }

    @RequestMapping(value = {"/document", "/document/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postDocument(@RequestBody DocumentDTOWeb documentDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        Document document = documentService.create(documentConverter.convertDTO(documentDTO));
        if (document != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, document.getUpdated().getTime());
            DocumentDTOWeb dto = documentConverter.convertModel(document, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/document", "/document/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteDocuments() {
    }

    @RequestMapping(value = {"/document/{id}", "/document/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteDocument(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        Document document = documentService.findById(id);
        if (document != null) {
            try {
                documentService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the document: " + document, e);
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

    @RequestMapping(value = {"/document", "/document/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putDocuments() {
    }

    @RequestMapping(value = {"/document/{id}", "/document/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putDocument(@PathVariable("id") Long id, @RequestBody DocumentDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        Document document = documentService.findById(id);
        if (document != null) {
            documentConverter.updateModel(document, dto);
            try {
                Document result = documentService.update(document);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                DocumentDTOWeb resultDto = documentConverter.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the document: " + document, e);
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

    @RequestMapping(value = {"/document", "/document/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsDocuments(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/document/{id}", "/document/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/document", "/document/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headDocuments(){
    }

    @RequestMapping(value = {"/document/{id}", "/document/{id}/"}, method = RequestMethod.HEAD)
    public void headDocument(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        Document document = documentService.findById(id);
        if (document != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, document.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return documentConverter;
    }

    @Override
    protected GenericService<Document> getService() {
        return documentService;
    }

}
