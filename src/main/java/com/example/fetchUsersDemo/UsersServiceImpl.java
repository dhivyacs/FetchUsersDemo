package com.example.fetchUsersDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UsersServiceImpl implements UsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersFromCityService usersFromCityService;
    private final UsersWithinRadiusService usersWithinRadiusService;

    @Value("${radius_in_meters}")
    private double radius;

    @Value("${London.latitude}")
    private double latitudeOfLondon;

    @Value("${London.longitude}")
    private double longitudeOfLondon;

    @Autowired
    public UsersServiceImpl(UsersFromCityService usersFromCityService, UsersWithinRadiusService usersWithinRadiusService) {
        this.usersFromCityService = usersFromCityService;
        this.usersWithinRadiusService = usersWithinRadiusService;
    }

    public List<Users> getUsers(String cityName) {
        double cityLatitude;
        double cityLongitude;

        logger.info("Users from city {} is requested ", cityName);

        switch (cityName) {
            case "London":
                cityLatitude = latitudeOfLondon;
                cityLongitude = longitudeOfLondon;
                break;
            default:
                cityLatitude = 0.0;
                cityLongitude = 0.0;
        }

        List<Users> cityUsers = usersFromCityService.getUsersFromCity(cityName);

        List<Users> usersWithinRadius =
                usersWithinRadiusService.getUsersWithinRadius(cityLatitude, cityLongitude, radius);

        return Stream.of(cityUsers, usersWithinRadius)
                .flatMap(x -> x.stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
