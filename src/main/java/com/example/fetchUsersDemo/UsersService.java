package com.example.fetchUsersDemo;

import java.util.List;

public interface UsersService {
    List<Users> getUsers(String cityName);
}
