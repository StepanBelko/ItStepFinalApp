package by.itstep.stpnbelko.service;

import by.itstep.stpnbelko.dto.UserDto;
import by.itstep.stpnbelko.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    boolean activateUser(String code);

    User findByActivationCode(String code);
}
