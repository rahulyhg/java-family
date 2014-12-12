package org.demis.familh.core.elasticsearch.converter;

import org.demis.familh.core.elasticsearch.dto.UserESDTO;
import org.demis.familh.core.jpa.entity.User;
import org.springframework.stereotype.Service;

@Service(value = "userConverterES")
public class UserConverter extends GenericConverter<User, UserESDTO> {

    public UserConverter() {
        super(User.class, UserESDTO.class);
    }

    @Override
    protected void updateModelFields(User user, UserESDTO userDTO) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setLogin(userDTO.getLogin());
        user.setRole(userDTO.getRole());
        user.setNickName(userDTO.getNickName());
    }

    @Override
    protected void updateDTOFields(UserESDTO userDTO, User user) {
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setLogin(user.getLogin());
        userDTO.setRole(user.getRole());
        userDTO.setNickName(user.getNickName());
    }
}
