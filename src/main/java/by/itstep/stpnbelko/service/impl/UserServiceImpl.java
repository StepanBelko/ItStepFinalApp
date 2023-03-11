package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.dto.UserDto;
import by.itstep.stpnbelko.repository.RoleRepository;
import by.itstep.stpnbelko.entity.Role;
import by.itstep.stpnbelko.entity.User;
import by.itstep.stpnbelko.repository.UserRepository;
import by.itstep.stpnbelko.service.UserService;
import by.itstep.stpnbelko.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static by.itstep.stpnbelko.util.AppConstants.ACTIVATION_LINK;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private MailSender mailSender;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        user.setActivationCode(String.valueOf(UUID.randomUUID()));


//        Высылаем письмо с кодом активации
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to CurrencyApp.\n" +
                            "To complete the registration follow the link below.\n" +
                            "%s%s", user.getFirstName(), ACTIVATION_LINK, user.getActivationCode());
            mailSender.send(user.getEmail(), "ACTIVATION_CODE", message);
        }

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public User findByActivationCode(String code) {
        User user = null;
        if (code != null) {
            for (User user1 : userRepository.findAll()) {
                if (user1.getActivationCode().equals(code)) {
                    user = user1;
                    break;
                }
            }
        }
        return user;
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
//        String[] name = user.getName().split(" ");
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoleSet(user.getRoles());
        userDto.setActive(user.isActive());
        userDto.setActivationCode(user.getActivationCode());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }


}
