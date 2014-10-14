package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.elasticsearch.dto.UserDTO;
import org.demis.familh.core.jpa.entity.User;
import org.springframework.stereotype.Service;

@Service(value = "userConverterES")
public class UserConverter extends GenericConverter<User, UserDTO> {

    public UserConverter() {
        super(User.class, UserDTO.class);
    }

    @Override
    protected void updateModelFields(User user, UserDTO userDTO) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setLogin(userDTO.getLogin());
    }

    @Override
    protected void updateDTOFields(UserDTO userDTO, User user) {
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setLogin(user.getLogin());
    }
}
