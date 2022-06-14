package com.example.fetchUsersDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(path = "/usersWithinRadius/{cityName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Users>> getUsersInLondon(@PathVariable final String cityName) {

        List<Users> usersList = usersService.getUsers(cityName);

        return ResponseEntity.ok().body(usersList);
    }
}
