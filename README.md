# Sports Scorer CLI

This is a command line interface (CLI) application created for SENG350 (Software Architecture) with various features 
related to sports scoring. 
This project implements the Observer design pattern, which passes data from the subject to all subscribers.

## Features
- Start a game.
- End a game.
- Predict running game winner.
- View prediction statistics.
- Populate teams with team names in a text file.
- See which teams are playing.
- View a team's win history.
- Get current scoreboard.
- Simulate a round by adding random points to each team.
- View a table of finished games.
- View latest news headline.
- View all available news headlines.
- Add a team.
- View a list of available commands (help).
- Exit CLI.

## External Libraries / Frameworks
- Spring Boot
- Spring Shell
- Spring AOP

### Logging
Logging is done with SLF4J using Spring AOP.

### Data Transfer
Data is transferred in this application primarily with records.

### Interface
The interface is made possible using the Spring Shell framework.

### Exception Handling
Exceptions are resolved in the command classes using exception resolvers.