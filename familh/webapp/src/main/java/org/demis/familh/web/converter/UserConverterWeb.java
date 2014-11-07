package org.demis.familh.web.converter;

import org.demis.familh.core.jpa.entity.User;
import org.demis.familh.web.dto.DTOWeb;
import org.demis.familh.web.dto.UserDTOWeb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service(value = "userConverterWeb")
public class UserConverterWeb extends GenericConverterWeb<User, UserDTOWeb> {

    public UserConverterWeb() {
        super(User.class, UserDTOWeb.class);
    }

    protected void updateModelFields(User user, UserDTOWeb userDTO) {
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
    }

    protected void updateDTOFields(UserDTOWeb userDTO, User user) {
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setLogin(user.getLogin());
        userDTO.setRole(user.getRole());
    }

    @Override
    public void generateHref(DTOWeb dto, HttpServletRequest request) {
        super.generateHref(dto, request);
        generateHrefForReferences(dto, request, ((UserDTOWeb) dto).getFamilyTrees());
    }
}

