package com.example.fetchUsersDemo;

import org.apache.lucene.util.SloppyMath;
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
public class UsersWithinRadiusServiceImpl implements UsersWithinRadiusService {

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public UsersWithinRadiusServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${API_uri}")
    private String uri;

    public List<Users> getUsersWithinRadius(double latitude, double longitude, double radius) {

        String request_usersWithinRadius = uri + "users";

        List<Users> usersWithinRadius = new ArrayList<>();

        logger.info("Request all users within radius");

        final ResponseEntity<Users[]> responseEntity =
                restTemplate.getForEntity(request_usersWithinRadius, Users[].class);

        if (responseEntity != null && responseEntity.getBody() != null) {
            List<Users> allUsers = Arrays.asList(responseEntity.getBody());

            logger.info("Fetched all users, total users count : {}", allUsers.size());

            for (Users user : allUsers) {
                if (SloppyMath.haversinMeters(latitude, longitude, user.getLatitude(), user.getLongitude()) <= radius) {
                    usersWithinRadius.add(user);
                }
            }
            logger.info("Users within 50 miles : {}", usersWithinRadius.size());

        }
        return usersWithinRadius;
    }
}
