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

import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final HttpSession httpSession;

    public AuthController(UserService userService, RoleService roleService, HttpSession httpSession) {
        this.userService = userService;
        this.roleService = roleService;
        this.httpSession = httpSession;
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
    public String activate(Model model, @PathVariable (value="code") String code) {
        System.out.println("activation mapping");
        boolean isActivated = userService.activateUser(code);

        if(isActivated) {
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

    private User getUserFromSession(){
        org.springframework.security.core.userdetails.User userFromSecurity = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userService.findByEmail(userFromSecurity.getUsername());
        return user;
    }

    @GetMapping("/updateUser")
    public String updateUser(@ModelAttribute User user,
                             Model model)  {
        System.out.println("UPDATEUSER!!!!!");
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.findByEmail(user.getEmail()));

        return "updateUser";
    }

    @PostMapping("/updateUser/save")
    public String saveUpdatedUser(@ModelAttribute User user,
                                  @RequestParam String[] roleId,
                                  Model model) {
        System.out.println("update user save");
        System.out.println("Roles size = " + roleId.length);

        return "redirect:/users";
    }

}
