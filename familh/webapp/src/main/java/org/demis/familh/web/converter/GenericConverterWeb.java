package org.demis.familh.web.converter;


import org.demis.familh.core.elasticsearch.converter.GenericConverter;
import org.demis.familh.core.jpa.entity.Model;
import org.demis.familh.web.RestConfiguration;
import org.demis.familh.web.dto.DTOWeb;
import org.demis.familh.web.dto.ResourceReferenceDTOWeb;
import org.demis.familh.web.dto.ResourcesReferenceWeb;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericConverterWeb<ModelImpl extends Model, DTOImpl extends DTOWeb> extends GenericConverter<ModelImpl, DTOImpl> {


    public GenericConverterWeb(Class<ModelImpl> modelClass, Class<DTOImpl> dtoClass) {
        super(modelClass, dtoClass);
    }

    @Override
    public DTOImpl convertModel(ModelImpl model) {
        return convertModel(model, null);
    }

    public DTOImpl convertModel(ModelImpl model, HttpServletRequest request) {

        DTOImpl dto = null;
        dto = super.convertModel(model);
        generateHref(dto, request);
        return dto;
    }

    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        generateHrefForResource(dto, request);
    }


    @Override
    public List<DTOImpl> convertModels(List<ModelImpl> models) {
        return convertModels(models, null);
    }

    public List<DTOImpl> convertModels(List<ModelImpl> models, HttpServletRequest request) {
        List<DTOImpl> dtos = new ArrayList<>(models.size());

        for (ModelImpl model: models) {
            dtos.add(convertModel(model, request));
        }

        return dtos;
    }

    protected void generateHrefForResource(DTOWeb dto, HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        generateBaseHref(dto, request, buffer);

        dto.setHref(buffer.toString());
    }

    protected void generateBaseHref(DTOWeb dto, HttpServletRequest request, StringBuffer buffer) {
        if (request != null) {
            if (request.isSecure()) {
                buffer.append("https//");
            } else {
                buffer.append("http//");
            }
            buffer.append(request.getServerName());
            if (request.getServerPort() != 80) {
                buffer.append(":");
                buffer.append(request.getServerPort());
            }
            buffer.append(request.getContextPath());
        }
        buffer.append(RestConfiguration.REST_BASE_URL);
        buffer.append(dto.getResourceName());
        buffer.append("/");
        buffer.append(dto.getId());
    }

    protected void generateHrefForReferences(DTOWeb dto, HttpServletRequest request, ResourcesReferenceWeb reference) {
        StringBuffer buffer = new StringBuffer();
        generateBaseHref(dto, request, buffer);
        buffer.append("/");
        buffer.append(reference.getReferenceName());

        reference.setHref(buffer.toString());
    }

    protected void generateHrefForReference(DTOWeb dto, HttpServletRequest request, ResourceReferenceDTOWeb reference) {
        StringBuffer buffer = new StringBuffer();
        generateBaseHref(dto, request, buffer);
        buffer.append("/");
        buffer.append(reference.getResourceName());
        buffer.append("/");
        buffer.append(reference.getId());

        reference.setHref(buffer.toString());
    }
}
