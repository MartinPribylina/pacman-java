# Pacman

This is a Java implementation of the classic Pacman game.

Key features:

- Classic Pacman gameplay mechanics
- Maze navigation with walls and corridors
- Collect key and go to portal to win
- Ghost AI random movement pattern
- Score tracking system
- Multiple levels

The project is built using Java and Maven, with a focus on object-oriented design principles. The game uses a custom graphics engine for rendering and implements the MVC (Model-View-Controller) architectural pattern.

## Authors

- Samuel Gall - xgalls00
- Martin Pribylina - xpriby19

## How to run

Check if you have Maven Installed<br>
`mvn --version`<br>
If Maven is not installed please follow installation guide [Link](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) <br>
Make sure you are in a project directory

### Build

`mvn package`

### Run

`java -cp .\target\ija-projekt-2-1.0-SNAPSHOT.jar src.Run`

### Documentation

`mvn site`

Open `target/site/apidocs/index.html`
