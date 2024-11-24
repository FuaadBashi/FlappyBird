# FlappyBird

Flappy Bird Clone in Java

This project is a simple Flappy Bird game clone created using Java and Swing. The game replicates the basic mechanics of the popular Flappy Bird game, allowing the player to navigate a bird through obstacles and aim for the highest score.

Features

	•	Flappy Bird mechanics: Tap the space bar to make the bird jump and avoid obstacles.
	•	Randomly generated pipes: Each set of pipes has a randomized height to increase difficulty.
	•	Game over and restart: When the bird hits a pipe or the ground, the game ends. Press Enter to restart.
	•	Score tracking: The game displays your score based on how many pipes you successfully pass.

Getting Started

Prerequisites

	•	Java Development Kit (JDK) 8 or later.
	•	A Java IDE or text editor for running the application (e.g., IntelliJ IDEA, Eclipse, NetBeans, or plain terminal).

Installation

	1.	Clone or download this repository to your local machine.
	2.	Ensure the project includes the following files:
	•	App.java (main entry point)
	•	Bird.java (represents the bird object)
	•	Pipe and background image assets:
	•	flappybirdbg.png
	•	toppipe.png
	•	bottompipe.png
	•	flappybird.png
	3.	Compile the Java files:

javac App.java Bird.java


	4.	Run the application:

java App



How to Play

	1.	Start the game. The bird will fall due to gravity.
	2.	Press the Space bar to make the bird jump.
	3.	Avoid hitting the pipes and the ground.
	4.	Your score increases for every pair of pipes you pass.
	5.	When the game ends, press Enter to restart.

Controls

	•	Space bar: Make the bird jump.
	•	Enter: Restart the game after Game Over.

Project Structure

The project is organized as follows:
	•	App.java: Main game logic, including rendering, updating, and handling user inputs.
	•	Bird.java: A separate class that handles the bird’s properties and movement.
	•	Image assets:
	•	flappybirdbg.png: Background image.
	•	flappybird.png: Bird sprite.
	•	toppipe.png: Top pipe sprite.
	•	bottompipe.png: Bottom pipe sprite.

Demo

Include screenshots or a GIF here to show the game in action.

Future Improvements

	•	Add levels with increasing difficulty.
	•	Introduce sound effects and music.
	•	Optimize collision detection.
	•	Add a leaderboard to save high scores.

License

This project is licensed under the MIT License. Feel free to use and modify it as needed.

This README file provides clear instructions for installation, gameplay, and project understanding. Let me know if you need any changes or enhancements!
