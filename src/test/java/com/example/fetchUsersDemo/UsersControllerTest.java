package com.example.fetchUsersDemo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class UsersControllerTest {

    private UsersServiceImpl usersServiceImpl;

    private UsersController usersController;

    @Before
    public void initialise() {
        usersServiceImpl = mock(UsersServiceImpl.class);
        usersController = new UsersController(usersServiceImpl);
    }

    @Test
    public void givenInvalidCity_ReturnsEmptyArrayList() {

        String invalidInput = "Paris";
        when(usersServiceImpl.getUsers(invalidInput)).thenReturn(new ArrayList<>());
        assertThat((usersController.getUsersInLondon(invalidInput)).getBody(), is(new ArrayList<>()));
    }

    @Test
    public void givenOneUser_ReturnOneUserInResponse() {

        List<Users> users = new ArrayList<>();

        Users user1 = new Users(1, "Maurice", "Sheldon", "mshieldon0@squidoo.com",
                "192.57.232.111", 34.003135, -117.7228641);

        users.add(user1);

        String validInput = "London";
        when(usersServiceImpl.getUsers(validInput)).thenReturn(users);
        assertThat((usersController.getUsersInLondon(validInput)).getBody(), is(users));
    }

    @Test
    public void givenMultipleUsers_ReturnMultipleUsersInResponse() {

        List<Users> users = new ArrayList<>();

        Users user1 = new Users(1, "Maurice", "Sheldon", "mshieldon0@squidoo.com",
                "192.57.232.111", 34.003135, -117.7228641);

        users.add(user1);

        Users user2 = new Users(2, "Meghan", "Southall", "msouthall2@ihg.com",
                "21.243.184.215", 51.4839, -0.6044);

        users.add(user2);

        String validInput = "London";
        when(usersServiceImpl.getUsers(validInput)).thenReturn(users);
        assertThat((usersController.getUsersInLondon(validInput)).getBody(), is(users));
    }

}
