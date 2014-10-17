package org.demis.familh.core.elasticsearch.converter;


import org.demis.familh.core.elasticsearch.dto.DTO;
import org.demis.familh.core.jpa.entity.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericConverter<ModelImpl extends Model, DTOImpl extends DTO> {

    private static Logger logger = LoggerFactory.getLogger(GenericConverter.class);


    protected abstract void updateModelFields(ModelImpl model, DTOImpl dto);

    protected abstract void updateDTOFields(DTOImpl dto, ModelImpl model);

    protected Class<ModelImpl> modelClass;

    protected Class<DTOImpl> dtoClass;

    public GenericConverter(Class<ModelImpl> modelClass, Class<DTOImpl> dtoClass) {
        this.modelClass = modelClass;
        this.dtoClass = dtoClass;
    }

    public DTOImpl convertModel(ModelImpl model) {
        DTOImpl dto = null;
        try {
            dto = dtoClass.newInstance();
            updateDTOFields(dto, model);
            dto.setId(model.getId());

        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Error when convert Model: " + model + " to DTO", e);
        }

        return dto;
    }

    public ModelImpl convertDTO(DTOImpl dto) {
        ModelImpl model = null;
        try {
            model = modelClass.newInstance();
            updateModelFields(model, dto);
            model.setId(dto.getId());
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Error when convert DTO: " + dto + " to Model", e);
        }

        return model;
    }

    public List<ModelImpl> convertDTOs(List<DTOImpl> dtos) {
        ArrayList<ModelImpl> models = new ArrayList<>(dtos.size());

        for (DTOImpl dto: dtos) {
            models.add(convertDTO(dto));
        }

        return models;
    }

    public List<DTOImpl> convertModels(List<ModelImpl> models) {
        ArrayList<DTOImpl> dtos = new ArrayList<>(models.size());

        for (ModelImpl model: models) {
            dtos.add(convertModel(model));
        }

        return dtos;
    }

    public void updateDTO(DTOImpl dto, ModelImpl model) {
        updateDTOFields(dto, model);
    }

    public void updateModel(ModelImpl model, DTOImpl dto) {
        updateModelFields(model, dto);
    }
}
