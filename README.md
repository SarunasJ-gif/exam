# Sourcery Exam

You have a starter Spring Boot project with an embedded H2 database.
You should be able to understand provided code and to add features.
You will develop API for social media posts.

There will be 5 exercises each given some points - you can get a fraction
of points given for the exercise too, so always do your best!
Please read all exercises first before coding.

Attention: What you develop here might not fully match the
API mocked in the first part of exam, so do not worry if you notice differences.

## Starter Application

* We recommend to use IntelliJ IDE for this exam
* There are a few ways how you can run the starter application:
  * Right click on _api/src/main/java/com/dbg/api/ApiApplication.java_ and select _Run 'ApiApplication'_
  * Use `gradlew bootRun` to run the starter application.
* Navigate to http://localhost:8080/h2 to open H2 DB Console UI.
  * Use `jdbc:h2:./8saf` for JDBC URL to and click Connect.
    [H2LoginPage](H2LoginPage.png)
* Application start up will set up tables using liquibase migration tool.

````sql
SELECT * FROM users;
````

Added tables could be used for exercises (so you just need to write the repositories).
However, feel free to modify liquibase migrations to suit your needs.
You can also modify or drop existing tables if you want.

H2 database here is a file which persists if you restart the app (i.e. after restart the data will still be there).
This also creates challenges:
1. If you restart app while liquibase is still running, you will leave your DB in locked state - then you need to remove
   a row from databasechangeloglock table.
2. If you face other problems, sometimes quickest solutions is to simply delete the *.db files :)

#### Troubleshooting instructions

If you run into Java version mismatch error follow these steps:
1. Open Project Structure settings (_File -> Project Structure_)
2. Open Project tab
3. Change the project SDK to Java 17 version

If `gradlew bootRun` does not work, try (`./gradlew bootRun`)

If you get an error like this: `./gradlew: Permission denied`, try running `chmod +x gradlew` and try running the first command again.

If you don't see Gradle toolbar on the right side or dependency imports in the packages are showing a lot of errors, try right-click on `build.gradle`, select `Link Gradle Project` and wait for a couple of seconds for imports to resolve. 

#### Packages

- Root **com.dbg.api** package contains Spring Boot Application and configuration classes.
  - **games.dto** package contains GamesDto and ErrorDto, which could be used as REST response bodies

## Exam Exercises

### Web API

You need to create a RESTful API that allows users to manipulate database of computer games data (like IMDB for movies)
UI is not needed, you can test everything using Postman or curl.
You are free to modify the existing code to suit your needs.
Please first read through all exercises before starting implementation.

#### Exercise 1 (1 point)
Create a Controller with Create operation to create publisher records.

* Endpoint takes a publisher title and returns created publisher in a response body.

Sample publisher (response):
```json
{
  "id": "75ec8f0a-e74d-45b9-81da-e143a86ed7fe",
  "title": "Rockstar Games"
}
```

#### Exercise 2 (3 points)

Create a Controller with Create and Read operations to manipulate games.
Select appropriate HTTP method for each operation.

* Create - takes title, publisher and salesCount and returns the created game in response body (example below)
* Read single by game ID - response is in the example below. Return 404 if not found.
* Read all by publisher - return all games ordered by sales count in the response
  For simplicity, no pagination or size limit is needed.

Sample game (response):
```json
{
  "id": "75ec8f0a-e74d-45b9-81da-e143a86ed7fe",
  "title": "Grand Theft Auto V",
  "publisher": "Rockstar Games",
  "salesCount": 170000000
}
```

#### Exercise 3 (1 point)

Add request validation when creating a game and publisher.
API should return appropriate HTTP response code.

* Title must contain at least 3 characters.
* Publisher's name must contain at least 3 characters.
* Sale count must be at least 1.

#### Exercise 4 (3 points)

Add ability to the system to add or remove "Likes" via API (i.e. like thumbs-up in Facebook).
Each user can add only a single "Like" and also can remove his/her "Like" from game.
Return total count of likes with game responses.

Sample game (response):
```json
{
  "id": "75ec8f0a-e74d-45b9-81da-e143a86ed7fe",
  "title": "Grand Theft Auto V",
  "publisher": "Rockstar Games",
  "salesCount": 170000000,
  "likes": 790
}
```

#### Exercise 5 (2 points)

Return appropriate HTTP response code for endpoints which require userId on requests passing non-existing userId.
There should be multiple endpoints to cover.

Error response body format (you can use ErrorDto):
```json
{
  "message": "non-existant userId"
}
```

#### Exercise 6 (2 point)

Create endpoint that accepts a publisher ID and returns its top 3 games having the highest count of likes ordered
by descending order.
Make number of items returned controllable via spring properties

#### Exercise 7 (2 point). Bonus task (you can still get max points without answering this one).

Enable HTTP Basic Authentication on spring boot API endpoints. Specify single user:

* username: sourceryUser
* password: sourceryPassword

Feel free to use the simplest possible way - you can leave plain text username and password in configuration (or source code) for this exercise.

## Curl helper
```shell
# -X sets HTTP method
# -d sets request body
# -H for adding header
curl -X 'POST' -d '{"foo":"bar"}' -H 'Content-type: application/json' localhost:8080/your/api/here
```
