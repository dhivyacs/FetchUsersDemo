package com.example.fetchUsersDemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class Users {

    @JsonProperty("id")
    private int id;

    @JsonProperty("first_name")
    private String first_name;

    @JsonProperty("last_name")
    private String last_name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("ip_address")
    private String ip_address;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

}
