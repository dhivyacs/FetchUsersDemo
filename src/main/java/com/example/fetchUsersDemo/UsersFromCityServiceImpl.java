package com.example.fetchUsersDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersFromCityServiceImpl implements UsersFromCityService {

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public UsersFromCityServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${API_uri}")
    private String url;

    public List<Users> getUsersFromCity(String cityName) {

        String request_usersFromCity = url + "city/" + cityName + "/users";

        logger.info("Request all users for the given city.");
        final ResponseEntity<Users[]> responseEntity =
                restTemplate.getForEntity(request_usersFromCity, Users[].class);

        final Users[] users = responseEntity.getBody();

        return users != null ? Arrays.asList(users) : new ArrayList<>();

    }
}
