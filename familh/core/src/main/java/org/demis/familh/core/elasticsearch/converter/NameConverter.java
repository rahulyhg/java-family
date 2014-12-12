package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.dto.NameDTO;
import org.demis.familh.core.jpa.entity.Name;
import org.springframework.stereotype.Service;

@Service(value = "nameConverterES")
public class NameConverter extends GenericConverter<Name, NameDTO> {

    public NameConverter() {
        super(Name.class, NameDTO.class);
    }

    @Override
    protected void updateModelFields(Name name, NameDTO nameDTO) {
        name.setFirstName(nameDTO.getFirstName());
        name.setLastName(nameDTO.getLastName());
        name.setType(nameDTO.getType());
    }

    @Override
    protected void updateDTOFields(NameDTO nameDTO, Name name) {
        nameDTO.setFirstName(name.getFirstName());
        nameDTO.setLastName(name.getLastName());
        nameDTO.setType(name.getType());
    }
}
