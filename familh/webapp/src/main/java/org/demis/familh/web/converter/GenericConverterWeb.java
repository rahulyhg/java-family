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
        StringBuilder builder = new StringBuilder();
        generateBaseHref(dto, request, builder);

        dto.setHref(builder.toString());
    }

    protected void generateBaseHref(DTOWeb dto, HttpServletRequest request, StringBuilder builder) {
        if (request != null) {
            if (request.isSecure()) {
                builder.append("https//");
            } else {
                builder.append("http//");
            }
            builder.append(request.getServerName());
            if (request.getServerPort() != 80) {
                builder.append(":");
                builder.append(request.getServerPort());
            }
            builder.append(request.getContextPath());
        }
        builder.append(RestConfiguration.REST_BASE_URL);
        builder.append(dto.getResourceName());
        builder.append("/");
        builder.append(dto.getId());
    }

    protected void generateHrefForReferences(DTOWeb dto, HttpServletRequest request, ResourcesReferenceWeb reference) {
        StringBuilder builder = new StringBuilder();
        generateBaseHref(dto, request, builder);
        builder.append("/");
        builder.append(reference.getReferenceName());

        reference.setHref(builder.toString());
    }

    protected void generateHrefForReference(DTOWeb dto, HttpServletRequest request, ResourceReferenceDTOWeb reference) {
        StringBuilder builder = new StringBuilder();
        generateBaseHref(dto, request, builder);
        builder.append("/");
        builder.append(reference.getResourceName());
        builder.append("/");
        builder.append(reference.getId());

        reference.setHref(builder.toString());
    }
}
