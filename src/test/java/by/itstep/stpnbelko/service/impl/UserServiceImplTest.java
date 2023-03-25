package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.dto.UserDto;
import by.itstep.stpnbelko.entity.Role;
import by.itstep.stpnbelko.repository.RoleRepository;
import by.itstep.stpnbelko.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

class UserServiceImplTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);
    private PasswordEncoder passwordEncoder = mock((PasswordEncoder.class));

    private static UserServiceImpl userService;

    @BeforeEach
    void initUserService() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void saveUser() {
        Role role = new Role();
        role.setName("ROLE_USER");
        List<Role> roleList = Arrays.asList();
        UserDto user = new UserDto(999L, "firstName", "lastName", "email@mail.com", roleList, "password", true, "activationCode");
        userService.saveUser(user);
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void activateUser() {
    }

    @Test
    void findByActivationCode() {
    }
}