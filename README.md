# GreenBite 🐍🍏

**GreenBite** is a classic, simple, yet addictive snake game where a green snake devours red apples for points! The game is designed for fun, easy to use for players, and easily modifiable for developers.

##
### Table of Contents:
1. [Overview](#overview)
2. [Gameplay Instructions](#gameplay-instructions)
3. [Installation Guide](#installation-guide)
4. [Usage Instructions](#usage-instructions)
5. [For Developers](#for-developers)
6. [Directory Structure](#directory-structure)
7. [License](#license)

## 
### Overview:
In GreenBite, players control a green snake that grows longer each time it eats a red apple. The game challenges players to control the snake's movements, avoiding collisions with itself and the edges of the board. This classic game has been enhanced with sound effects and is perfect for quick, entertaining gameplay.

## 
### Gameplay Instructions:

**Start the Game:** Simply launch the executable file to start the game.

**Control the Snake:**
* Use the arrow keys on your keyboard to navigate the snake up, down, left, and right.

**Pause and Resume:**
* Press the P key to pause or resume the game.

**Goal:** Eat as many red apples as possible without crashing into yourself or the walls. Each apple you eat will make the snake grow longer.

##
### Installation Guide:

1. Clone the Repository:

        ```git clone https://github.com/Kaustav-coder-hub/GreenBite.git```
2. Navigate into the cloned repository:

        ```cd GreenBite```

**Option 1: Run the Executable**

For quick play without needing to compile the code, simply:

1. Locate the `GreenBite.exe` file in the repository.
2. Double-click the `GreenBite.exe` file to launch the game.

***Note:*** No additional installation is required for the executable.

**Option 2: Compile and Run from Source Code**

If you'd like to explore or modify the source code, follow the instructions in the [For Developers](#for-developers) section.


##
### Usage Instructions:
* Once the game is running, use the arrow keys to control the snake and start eating the apples!
* The game includes background music and sound effects for specific actions:
    * Background Music: Plays continuously while the game is running.
    * Eat Apple Sound: Plays when the snake eats an apple.
    * Game Over Sound: Plays when the snake crashes.

##
### For Developers:
If you're interested in further development, here are some pointers to help you get started.

**Requirements**

* Java JDK: Ensure Java Development Kit (JDK) is installed to compile and run the code.
* IDE: This project can be opened in Visual Studio Code or any preferred Java IDE.

**Setting Up the Project**

1. Clone the repository (if not already cloned):

        ```git clone https://github.com/Kaustav-coder-hub/GreenBite.git```
2. Open the project folder in your IDE.

3. Compile the code:
        
        ```javac src/*.java```

4. Run the game:

        ```java -cp src SnakeGame```

**Modifying the Code**

The project is structured to make development simple. Feel free to add new features or adjust the game mechanics. Some ideas for further development include:

* Adding more levels or obstacles.
* Introducing new sound effects or background themes.
* Adjusting the snake’s speed or apple spawn rate.

For sound files, place your `.wav` files in the `src/sounds` folder, and reference them in `GamePanel.java`.

##
### Directory Structure:
Here's a breakdown of the project structure:

```
GreenBite/
│
├── SNAKE-GAME-IN-JAVA-CODE/
│     └── src/
│           ├── sounds/
│           │     ├──background_music.wav
│           │     ├──eat_apple.wav
│           │     ├──game_over.wav
│           ├── GamePanel.java
│           └── SnakeGame.java
│
├── STANDALONE EXE DOWNLOAD/
│     └── SNAKE-GAME-JAVA-FINAL.exe
│
├── .gitignore
└── README.md
```

##
### License
This project is open source and free to use, modify, and distribute. Enjoy playing, and happy coding!
