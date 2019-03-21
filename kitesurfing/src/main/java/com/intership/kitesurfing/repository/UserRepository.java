package com.intership.kitesurfing.repository;

import com.intership.kitesurfing.domain.Location;
import com.intership.kitesurfing.domain.UserDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

public interface UserRepository extends MongoRepository<UserDto, String> {

    UserDto findByEmail(String email);

    UserDto findByUsername(String username);

    UserDto findByUsernameAndPassword(String username, String password);


}
