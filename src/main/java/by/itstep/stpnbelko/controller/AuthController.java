package by.itstep.stpnbelko.controller;

import by.itstep.stpnbelko.dto.UserDto;
import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.entity.Role;
import by.itstep.stpnbelko.entity.User;
import by.itstep.stpnbelko.service.CoinService;
import by.itstep.stpnbelko.service.RoleService;
import by.itstep.stpnbelko.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    private final CoinService coinService;

    public AuthController(UserService userService, RoleService roleService, CoinService coinService) {
        this.userService = userService;
        this.roleService = roleService;
        this.coinService = coinService;
    }

    @GetMapping("index")
    public String home() {
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
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/roles")
    public String showAllRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "roles";
    }


    @GetMapping("/coins")
    public String showAllCoins(Model model) {
        return pagination(1, "rank", "ASC", model);
       /* List<Coin> coinFromWeb = coinService.getAllCoinsFromWeb();
        List<Coin> coins = coinService.getAllCoinsFromDB();

        if (coins.isEmpty()) {
            coinService.updateCoinList();
            coins = coinService.getAllCoinsFromDB();
        }

        for (int i = 0; i < coinFromWeb.size(); i++) {
            if (!coins.get(i).equals(coinFromWeb.get(i))) {
                System.out.println("Change : " + coins.get(i).getName());
                coins.set(i, coinFromWeb.get(i));
            }

        }

        System.out.println(coins.equals(coinFromWeb));
        coinService.updateCoinList(coins);

        model.addAttribute("coins", coins);
        return "coins";*/
    }


    @GetMapping("/coins/page/{pageNo}")
    public String pagination(@PathVariable(value = "pageNo") int pageNo,
                             @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir,
                             Model model) {
        int pageSize = 10;

        Page<Coin> page = coinService.pagination(pageNo, pageSize, sortField, sortDir);
        List<Coin> listCoins = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCoins", listCoins);
        return "coinsNewTemplate";
    }

    
}
