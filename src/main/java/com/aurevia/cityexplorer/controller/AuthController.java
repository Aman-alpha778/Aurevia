package com.aurevia.cityexplorer.controller;

import jakarta.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.aurevia.cityexplorer.model.SignupForm;
import com.aurevia.cityexplorer.model.User;
import com.aurevia.cityexplorer.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login"})
    public String loginPage(Model model) {
        if (!model.containsAttribute("signupForm")) {
            model.addAttribute("signupForm", new SignupForm());
        }
        return "login";
    }

    @GetMapping({"/signup", "/signup/"})
    public String signupPage(Model model) {
        if (!model.containsAttribute("signupForm")) {
            model.addAttribute("signupForm", new SignupForm());
        }
        model.addAttribute("showSignup", true);
        return "login";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupForm") SignupForm signupForm,
                         BindingResult bindingResult,
                         HttpServletRequest request,
                         Model model) {
        if (!bindingResult.hasErrors() && userService.emailExists(signupForm.getEmail())) {
            bindingResult.rejectValue("email", "exists", "An account with this email already exists.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("showSignup", true);
            return "login";
        }

        User user = userService.register(signupForm);
        autoLogin(user, request);
        return "redirect:/portal";
    }

    private void autoLogin(User user, HttpServletRequest request) {
        var userDetails = userService.loadUserByUsername(user.getEmail());
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }
}
