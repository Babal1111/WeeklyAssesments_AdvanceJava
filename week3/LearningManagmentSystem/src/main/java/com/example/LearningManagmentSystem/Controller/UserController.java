package com.example.LearningManagmentSystem.Controller;

import com.example.LearningManagmentSystem.Model.User;
import com.example.LearningManagmentSystem.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;
    public  UserController(UserService userServicer){
        this.userService = userServicer;
    }

    @ModelAttribute("user")
    public User userModel() {
        return new User();
    }

    @GetMapping("/register")
    public String showRegistration(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user")User user, BindingResult result){

        if (result.hasErrors()) {
            return "register";
        }

        userService.addUser(user);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model) {

        User user = userService.login(email, password);

        if (user != null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("errorMessage", "Invalid email or password");
        return "login";
    }



}
