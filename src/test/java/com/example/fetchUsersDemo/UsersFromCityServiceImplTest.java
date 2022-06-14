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

public class UsersFromCityServiceImplTest {

    private UsersFromCityServiceImpl usersFromCityServiceImpl;

    private RestTemplate restTemplate;

    @Before
    public void initialise() {
        restTemplate = mock(RestTemplate.class);
        usersFromCityServiceImpl = new UsersFromCityServiceImpl(restTemplate);
        ReflectionTestUtils.setField(usersFromCityServiceImpl, "url", "https://dwp-techtest.herokuapp.com/");
    }

    @Test
    public void givenInvalidCity_getCityUsers_thenReturnEmptyArrayList() {

        Users[] users = new Users[0];

        String invalidCity = "Paris";

        when(restTemplate.getForEntity("https://dwp-techtest.herokuapp.com/city/Paris/users", Users[].class)).thenReturn(new ResponseEntity<>(users, HttpStatus.OK));
        assertThat(usersFromCityServiceImpl.getUsersFromCity(invalidCity), is(new ArrayList<>()));
    }

    @Test
    public void givenValidCity_getCityUsers_thenReturnUsersList() {

        Users userInLondon = new Users(1, "Maurice", "Sheldon", "mshieldon0@squidoo.com",
                "192.57.232.111", 51.5074, -0.1277);

        Users[] users = new Users[1];
        users[0] = userInLondon;

        List<Users> usersInLondon = new ArrayList<>();
        usersInLondon.add(userInLondon);

        String validCity = "London";

        when(restTemplate.getForEntity("https://dwp-techtest.herokuapp.com/city/London/users", Users[].class)).thenReturn(new ResponseEntity<>(users, HttpStatus.OK));
        assertThat(usersFromCityServiceImpl.getUsersFromCity(validCity), is(usersInLondon));
    }
}
