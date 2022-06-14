package com.example.fetchUsersDemo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersWithinRadiusServiceImplTest {

    private UsersWithinRadiusService usersWithinRadiusService;

    private RestTemplate restTemplate;

    @Before
    public void initialise() {
        restTemplate = mock(RestTemplate.class);
        usersWithinRadiusService = new UsersWithinRadiusServiceImpl(restTemplate);
        ReflectionTestUtils.setField(usersWithinRadiusService, "uri", "https://dwp-techtest.herokuapp.com/");
    }

    @Test
    public void givenUserCoordinatesMatchesLondonCoordinates_thenReturnUser() {

        Users userInLondon = new Users(1, "Maurice", "Sheldon", "mshieldon0@squidoo.com",
                "192.57.232.111", 51.5074, -0.1277);

        List<Users> usersInLondon = new ArrayList<>();
        usersInLondon.add(userInLondon);

        Users[] users = new Users[1];
        users[0] = userInLondon;

        when(restTemplate.getForEntity("https://dwp-techtest.herokuapp.com/users", Users[].class)).thenReturn(new ResponseEntity<>(users, HttpStatus.OK));
        assertThat(usersWithinRadiusService.getUsersWithinRadius(51.5074, -0.1277, 80467.2), is(usersInLondon));
    }

    @Test
    public void givenUserCoordinatesWithin50MilesOfLondonCoordinates_thenReturnUser() {

        Users userInLondon = new Users(1, "Maurice", "Sheldon", "mshieldon0@squidoo.com",
                "192.57.232.111", 51.4839, -0.6044);

        List<Users> usersInLondon = new ArrayList<>();
        usersInLondon.add(userInLondon);

        Users[] users = new Users[1];
        users[0] = userInLondon;

        when(restTemplate.getForEntity("https://dwp-techtest.herokuapp.com/users", Users[].class)).thenReturn(new ResponseEntity<>(users, HttpStatus.OK));
        assertThat(usersWithinRadiusService.getUsersWithinRadius(51.5074, -0.1277, 80467.2), is(usersInLondon));
    }

    @Test
    public void givenUserCoordinatesOutside50MilesOfLondonCoordinates_thenReturnUser() {

        Users userInLondon = new Users(1, "Maurice", "Sheldon", "mshieldon0@squidoo.com",
                "192.57.232.111", 55.0250, -1.5089);

        Users[] users = new Users[1];
        users[0] = userInLondon;

        when(restTemplate.getForEntity("https://dwp-techtest.herokuapp.com/users", Users[].class)).thenReturn(new ResponseEntity<>(users, HttpStatus.OK));
        assertThat(usersWithinRadiusService.getUsersWithinRadius(51.4839, -0.1278, 80467.2), is(new ArrayList<>()));
    }


}
