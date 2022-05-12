package com.enlabs.controller;


import com.enlabs.model.dto.UserDto;
import com.enlabs.servce.UserService;
import com.enlabs.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService ;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(Model model) {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("signup_form");
        model.addAttribute("user", new User());
        return modelAndView;
    }


    @PostMapping("/process-register")
    public String processRegister(UserDto user) {
        userService.addUser(user);
        return "register_success";
    }

    @PostMapping("/")
    public ModelAndView updateUser(@Valid UserDto userDto){
        ModelAndView modelAndView = new ModelAndView();
        User user=userService.update(userDto);
        modelAndView.setViewName("users");
        modelAndView.addObject("user",user);
        return modelAndView;

    }

    @GetMapping("/users")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping(path = "/{email}")
    public ModelAndView getUser(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("edit");
        return modelAndView;
    }
}
