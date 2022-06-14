# FetchUsersDemo

#### Description

* This is a Springboot application which fetches users in and around 50 miles of city(London).
* This application calls the city API using the path /city/{cityName}/users and then the users API using the path
  /users.
* The city API returns all the users in the city(London).
* The users API returns all the users which is then filtered based on latitude and longitude. Users within 50 miles of
  city coordinates are returned.
* The application is built to return London users, it can be further enhanced to include various cities.
* The Latitude, Longitude information of the cities can be configured externally in application.properties

#### Instructions to run

* mvnw clean install
* java -jar target/FetchUsersDemo-0.0.1-SNAPSHOT.jar

The GET request is sent to ```http://localhost:8085/usersWithinRadius/{cityName}```

- For example, when requesting London users - ```http://localhost:8085/usersWithinRadius/London```

#### Dependency

https://dwp-techtest.herokuapp.com/