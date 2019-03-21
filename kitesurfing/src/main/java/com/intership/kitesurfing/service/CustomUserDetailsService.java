package com.intership.kitesurfing.service;

import com.intership.kitesurfing.domain.Location;
import com.intership.kitesurfing.domain.Role;
import com.intership.kitesurfing.repository.LocationRepository;
import com.intership.kitesurfing.repository.RoleRepository;
import com.intership.kitesurfing.domain.UserDto;
import com.intership.kitesurfing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDto findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public UserDto findUserByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void saveRole(Role role){
        roleRepository.save(role);
    }

    public void updateUserFavorites(UserDto userDto, Location location){

        Set<Location> locations = userDto.getFavorites();
        if(location != null) locations.add(location);
        userDto.setFavorites(locations);
        userRepository.save(userDto);
    }

    public void saveUser(UserDto user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void deleteSpot(UserDto userDto, Location location){

        Set<Location> locations = userDto.getFavorites();
        locations.remove(location);
        userDto.setFavorites(locations);
        userRepository.save(userDto);
    }

    public Location findLocationById(String id){
        return locationRepository.findBy_id(id);
    }

    public Location findLocationByName(String name){
        return locationRepository.findByName(name);
    }

    public List<Location> findAllLocations(){
        return locationRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user = userRepository.findByUsername(username);
        if(user != null){

            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        }
        else{
            throw new UsernameNotFoundException("Username not found!");
        }
    }

    private UserDetails buildUserForAuthentication(UserDto user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), authorities);

    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();

        userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getRole())));

        return new ArrayList<>(roles);
    }
}

