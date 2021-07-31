package com.darkdevil.security.controller;

import com.darkdevil.security.model.Authorities;
import com.darkdevil.security.model.Users;
import com.darkdevil.security.service.AuthorityRepo;
import com.darkdevil.security.service.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class HomeResource {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthorityRepo authorityRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(){
        return("<h1>Welcome</h1> <a href='/logout'>logout</a>");
    }

    @GetMapping("/user")
    public String user(){
        return("<h1>Welcome User</h1> <a href='/logout'>logout</a>");
    }

    @GetMapping("/admin")
    public String admin(){
        return("<h1>Welcome Admin</h1> <a href='/logout'>logout</a>");
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/login-error")
    public ModelAndView login(HttpServletRequest request, ModelAndView model) {
        try{
            HttpSession session = request.getSession(false);
            String errorMessage = null;
            if (session != null) {
                String ex = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION).toString();
                if(ex != null && !ex.isEmpty()){
                    String splitEx[] = ex.split(":");
                    errorMessage = splitEx[1];
                }
                else{
                    errorMessage = "Something went wrong!!!";
                }
            }
            model.setViewName("login");
            model.addObject("error", errorMessage);
            return model;
        }
        catch (Exception e){
            System.out.println("msg: " + e.getMessage());
            return model;
        }
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response) {
        try{
            ModelAndView modelAndView = new ModelAndView();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            modelAndView.setViewName("login");
            modelAndView.addObject("logout", "Logged Out Successfully");
            return modelAndView;
        }
        catch (Exception e){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/test/{param1}/{param2}")
    public String test(@PathVariable String param1, @PathVariable String param2){
        System.out.println("# " + param1);
        System.out.println("# " + param2);
        return "test";
    }

    @GetMapping("/register/{un}/{pw}")
    public String register(@PathVariable String un, @PathVariable String pw){
        Users users = new Users();
        users.setUsername(un);
        users.setPassword(passwordEncoder.encode(pw));
        users.setEnabled(1);

        Authorities authorities = new Authorities();
        authorities.setUsername(un);
        authorities.setAuthority("ROLE_USER");

        userRepo.save(users);
        authorityRepo.save(authorities);

        return "Success";
    }

}
