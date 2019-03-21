package com.intership.kitesurfing.controller;


import com.intership.kitesurfing.domain.Location;
import com.intership.kitesurfing.domain.Role;
import com.intership.kitesurfing.domain.UserDto;
import com.intership.kitesurfing.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private CustomUserDetailsService userService;


    @RequestMapping(value = "/api/login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ModelAndView login(@Valid UserDto userDto){
        ModelAndView modelAndView = new ModelAndView();
        UserDto userExists = userService.findUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
        System.out.println(userExists);

        if(userExists != null){
            modelAndView.setViewName("dashboard");
        }
        else{
            System.out.println("NO user: ");
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/api/signup", method = RequestMethod.GET)
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = new UserDto();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/api/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserDto userDto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        UserDto userExists = userService.findUserByUsername(userDto.getUsername());
        if( userExists != null){
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            modelAndView.setViewName("redirect:/api/signup?error=true");
        }
        else{
            Role role = new Role();
            role.setRole("ADMIN");
            Set<Role> roles = new HashSet<>(Arrays.asList(role));
            userDto.setRoles(roles);
            System.out.println(userDto.getRoles());
            userService.saveRole(role);
            userService.saveUser(userDto);
            modelAndView.addObject("successMessage", "Successfully registered");
            modelAndView.addObject("user", new UserDto());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @GetMapping(value = "/api/users/me")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto userDto = userService.findUserByUsername(authentication.getName());
        modelAndView.addObject("currentUser", userDto);
        modelAndView.addObject("fullName", "Welcome " + userDto.getUsername());
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @GetMapping(value = {"/api/", "/api/home"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value="/api/logout", method = RequestMethod.GET)
    public ModelAndView logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("redirect:/api/login?logout=true");

    }

    @RequestMapping(value = "/api/favorites/spots", method = RequestMethod.GET)
    public ModelAndView showFavorites(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto userDto = userService.findUserByUsername(authentication.getName());
        ModelAndView modelAndView = new ModelAndView();

        List<Location> locationsToShow = userService.findAllLocations();
        Set<Location> toRemove = userDto.getFavorites();
        if(toRemove != null && !toRemove.isEmpty()){
            locationsToShow.removeAll(toRemove);
        }
        modelAndView.addObject("spots", locationsToShow);
        modelAndView.setViewName("favorites");
        return modelAndView;
    }


    @RequestMapping(value = "/api/favorites/spots", method = RequestMethod.POST)
    public Set<Location> addFavorites(@ModelAttribute("spot") Location model, @RequestParam("spot") Location loc, HttpServletRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String looc = request.getParameter("spot");
        System.out.println(looc);
        UserDto userDto = userService.findUserByUsername(authentication.getName());
        Location location = new Location();
        if(loc != null) location = userService.findLocationByName(loc.getName());

        System.out.println(loc);
        System.out.println(location);

        if(userDto != null && location != null){
            userService.updateUserFavorites(userDto, location);
            return userService.findUserByUsername(authentication.getName()).getFavorites();
        }

        return new HashSet<>();
    }

    @RequestMapping(value = "/api/favorites/spots/{spotId}", method = RequestMethod.GET)
    public Set<Location> deleteOne(@PathVariable("spotId") String spotId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto userDto = userService.findUserByUsername(authentication.getName());
        Location location = userService.findLocationById(spotId);

        if(userDto != null && location != null){
            userService.deleteSpot(userDto, location);
            return userService.findUserByUsername(authentication.getName()).getFavorites();
        }

        return new HashSet<>();
    }

}
