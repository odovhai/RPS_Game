##Purpose 
Purpose of this solution is to create a RESTful web service for playing Rock-Paper-Scissors game with a computer.
Main algorithm is based on Markov chains, it can predict the next user choice based on previous ones.

Since the game is needed for a casino main goal is to "win at all costs". Every new round of the game is an independent event,
so if the user uses random generator Markov Chain or any other algorithm cannot guarantee a stable win. 
Moreover if the user guess the implemented algorithm the casino can suffer big financial losses.
So there was implemented an additional "Cheating" algorithm that "knows" about user choice and wins with the defined probability. 
It can be activated in application config file or via the appropriate env variables before starting the app.

##Configure


####1. Java and Maven
The application use [Java 12](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html) 
and [Maven](https://maven.apache.org/download.cgi)  as build tools, so those items should be installed.

####2. Database

The app uses postgres database, by default it has the next configs:

| Name | Default value       |  
|:------------ |:---------------|                             
|   SPRING_DATASOURCE_URL|  jdbc:postgresql://localhost:5432/casino  |  
|   SPRING_DATASOURCE_USERNAME|  postgres       |  
|   SPRING_DATASOURCE_PASSWORD|  Passw0rd!  

The configs can be changed in application.yaml config file or set as environment variable before app starts.

It needs either to [install postgres](https://www.postgresql.org/download/) database locally and create needed database and user or [install docker](https://docs.docker.com/install/) and run prepared docker-compose file with all needed configs.

To run db in docker from project root directory run: 

   `$ docker-compose up`

####3. Application game configs

The application admin user credential configs and algorithm configs. 
Admin user can have access for special endpoints, such as listing all users and deleting user.

| Name | Default value       |  
|:------------ |:---------------|                             
|   APP_ADMIN_USER|  admin  |  
|   APP_ADMIN_PASSWORD|  Adm1n!  |  
|   APP_RPS_GAME_FAIR_GAME|   true |  
|   APP_RPS_GAME_AI_WINS_PART|  79  |  
|   APP_RPS_GAME_HUMAN_WINS_PART| 61   |  
|   APP_RPS_GAME_TIE_PART|   63 |  

If `APP_RPS_GAME_FAIR_GAME` is set to `false` the "Cheating" algorithm will work instead of Markov Chain one. 

The greater the difference of `APP_RPS_GAME_AI_WINS_PART` from `APP_RPS_GAME_HUMAN_WINS_PART` and `APP_RPS_GAME_TIE_PART` 
the greater win rate of casino    

##Start and play

1. To start the app, from the root directory run:

    `$ mvn clean install`   
    `$ java -jar target/rock-paper-scissors-0.0.1-SNAPSHOT.jar`

2. All REST API endpoints are describe and available to use in Swagger, open in a browser: `http://localhost:8080/swagger-ui.html#/`

3. To play the game:
    1. Create user:
        `curl -X POST "http://localhost:8080/users" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"Usr1\", \"password\": \"Password1\"}"`
    2. Start game:
        `curl -X POST "http://localhost:8080/games/start" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"userName\": \"Usr1\", \"password\": \"Password1\"}"`
    3. Play round:
        `curl -X POST "http://localhost:8080/games/rps" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"gameId\": 1, \"userChoice\": \"ROCK\", \"userCredentials\":{ \"userName\": \"Usr1\", \"password\": \"Password1\"}}"`
        
        *Note: use gameId from item 2 response for the request boy*
    4. Get game statistics:
        `curl -X POST "http://localhost:8080/games/1" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"userName\": \"Usr1\", \"password\": \"Password1\"}"`
    5. Finish game:
        `curl -X POST "http://localhost:8080/games/finish" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"userName\": \"Usr1\", \"password\": \"Password1\"}"`
    
    