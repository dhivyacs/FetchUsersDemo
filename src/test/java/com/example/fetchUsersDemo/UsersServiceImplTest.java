package com.example.fetchUsersDemo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersServiceImplTest {

    private UsersServiceImpl usersServiceImpl;

    private UsersFromCityService usersFromCityService;

    private UsersWithinRadiusService usersWithinRadiusService;

    @Before
    public void initialise() {
        usersFromCityService = mock(UsersFromCityService.class);
        usersWithinRadiusService = mock(UsersWithinRadiusService.class);
        usersServiceImpl = new UsersServiceImpl(usersFromCityService, usersWithinRadiusService);
        ReflectionTestUtils.setField(usersServiceImpl, "radius", 80467.2);
        ReflectionTestUtils.setField(usersServiceImpl, "latitudeOfLondon", 51.5074);
        ReflectionTestUtils.setField(usersServiceImpl, "longitudeOfLondon", -0.1277);
    }

    @Test
    public void givenNoUsersFound_GetUsers_ReturnsEmptyArrayList() {

        when(usersFromCityService.getUsersFromCity("London"))
                .thenReturn(new ArrayList<>());
        when(usersWithinRadiusService.getUsersWithinRadius(51.5074, -0.1277, 80467.2))
                .thenReturn(new ArrayList<>());

        List<Users> allUsers = usersServiceImpl.getUsers("London");

        assertThat(allUsers, is(new ArrayList<>()));
    }

    @Test
    public void givenMultipleUsersFound_GetUsers_ReturnsMultipleUsersArrayList() {

        Users user1 = new Users(1, "Maurice", "Sheldon", "mshieldon0@squidoo.com",
                "192.57.232.111", 51.5074, -0.1277);

        List<Users> cityUsers = new ArrayList<>();
        cityUsers.add(user1);

        Users user2 = new Users(2, "Meghan", "Southall", "msouthall2@ihg.com",
                "21.243.184.215", 51.4839, -0.6044);

        List<Users> usersWithinRadius = new ArrayList<>();
        usersWithinRadius.add(user2);

        when(usersFromCityService.getUsersFromCity("London")).thenReturn(cityUsers);
        when(usersWithinRadiusService.getUsersWithinRadius(51.5074, -0.1277, 80467.2)).thenReturn(usersWithinRadius);

        List<Users> allUsers = usersServiceImpl.getUsers("London");

        assertThat(allUsers.size(), is(2));
        assertTrue(allUsers.containsAll(cityUsers));
        assertTrue(allUsers.containsAll(usersWithinRadius));
    }

    @Test
    public void givenSameUserReturnedInBothServices_GetUsers_ReturnsUsersWithoutDuplicatesArrayList() {

        Users user1 = new Users(1, "Maurice", "Sheldon", "mshieldon0@squidoo.com",
                "192.57.232.111", 51.5074, -0.1277);

        List<Users> cityUsers = new ArrayList<>();
        cityUsers.add(user1);

        List<Users> usersWithinRadius = new ArrayList<>();
        usersWithinRadius.add(user1);

        when(usersFromCityService.getUsersFromCity("London")).thenReturn(cityUsers);
        when(usersWithinRadiusService.getUsersWithinRadius(51.5074, -0.1277, 80467.2)).thenReturn(usersWithinRadius);

        List<Users> allUsers = usersServiceImpl.getUsers("London");

        assertThat(allUsers.size(), is(1));
        assertTrue(allUsers.containsAll(cityUsers));
    }


}
