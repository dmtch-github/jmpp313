package jm.spring.rest.controller;

import jm.spring.rest.entity.Roles;
import jm.spring.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    public static final String URL_ROOT = "/user";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        String stringRoles = roles.stream().map(x -> x.replace(Roles.ROLE_PREFIX,"")).sorted().collect(Collectors.joining(" ","",""));
        model.addAttribute("stringRoles", stringRoles);
        model.addAttribute("user", userService.getUserByName(auth.getName()));
        return "user";
    }
}
