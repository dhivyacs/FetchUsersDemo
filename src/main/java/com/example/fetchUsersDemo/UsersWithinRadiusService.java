package com.example.fetchUsersDemo;

import java.util.List;

public interface UsersWithinRadiusService {
    List<Users> getUsersWithinRadius(double latitude, double longitude, double radius);
}
