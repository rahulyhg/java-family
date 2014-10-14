package org.demis.familh.web.converter;

import org.demis.familh.core.jpa.entity.Name;
import org.demis.familh.web.dto.DTOWeb;
import org.demis.familh.web.dto.NameDTOWeb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service(value = "nameConverterWeb")
public class NameConverterWeb extends GenericConverterWeb<Name, NameDTOWeb> {

    public NameConverterWeb() {
        super(Name.class, NameDTOWeb.class);
    }

    protected void updateModelFields(Name name, NameDTOWeb nameDTO) {
        name.setFirstName(nameDTO.getFirstName());
        name.setLastName(nameDTO.getLastName());
        name.setType(nameDTO.getType());
    }

    protected void updateDTOFields(NameDTOWeb nameDTO, Name name) {
        nameDTO.setFirstName(name.getFirstName());
        nameDTO.setLastName(name.getLastName());
        nameDTO.setType(name.getType());
        nameDTO.setPersonId(name.getPerson().getId());
    }

    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        super.generateHref(dto, request);
        generateHrefForResource(((NameDTOWeb)dto).getPerson(), request);
    }
}
