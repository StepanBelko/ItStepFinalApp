package by.itstep.stpnbelko.controller;

import by.itstep.stpnbelko.dto.UserDto;
import by.itstep.stpnbelko.entity.Role;
import by.itstep.stpnbelko.entity.User;
import by.itstep.stpnbelko.service.RoleService;
import by.itstep.stpnbelko.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;

    public AuthController(UserService userService, RoleService roleService, HttpSession httpSession) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("index")
    public String home() {
        return "index";
    }

    @GetMapping("/")
    public String homeSecondVersion() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();

            model.addAttribute("user", user);
            return "register";
        }
        model.addAttribute("message", "User successfully added. Check your email to get activation link");
        userService.saveUser(user);
        return "login";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();

        User user = getUserFromSession();

        model.addAttribute("userInSession", user);
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable(value = "code") String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated. Please login");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }


        return "login";
    }

    @GetMapping("/roles")
    public String showAllRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "roles";
    }

    private User getUserFromSession() {
        org.springframework.security.core.userdetails.User userFromSecurity
                = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userService.findByEmail(userFromSecurity.getUsername());
    }

    @GetMapping("/updateUser")
    public String updateUser(@ModelAttribute User user,
                             Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.findByEmail(user.getEmail()));

        return "updateUser";
    }

    @PostMapping("/updateUser/save")
    public String saveUpdatedUser(@ModelAttribute User user,
                                  @RequestParam(value = "roleName", required = false) String[] roleName,
                                  Model model) {
        List<Role> roleList = new ArrayList<>();

        if (roleName != null) {
            for (String s : roleName) {
                Role role = roleService.getByName(s);
                roleList.add(role);
            }
        }
        userService.updateUserInfo(user, roleList);

        return "redirect:/users";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@ModelAttribute User user,
                             Model model) {
        userService.deleteUser(userService.findByEmail(user.getEmail()));
        model.addAttribute("message", "User " + user.getEmail() + "successfully removed.");
        return "redirect:/users";
    }

}
