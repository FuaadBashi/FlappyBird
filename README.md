# Flappy Bird — Java Swing

A desktop game exercise using Java Swing: flap through randomly positioned pipes, track a score, and restart after a collision.

## Run locally

Requires a JDK and a graphical desktop.

```bash
git clone https://github.com/FuaadBashi/FlappyBird.git
cd FlappyBird
cd flappyBird/src
javac App.java Bird.java FlappyBird.java
java App
```

Keep the PNG files in this directory: the game loads them as classpath resources.

## Controls

- **Space:** flap upward.
- **Enter:** restart after game over.

## Code to explore

- [App.java](flappyBird/src/App.java): window creation and application entry point.
- [FlappyBird.java](flappyBird/src/FlappyBird.java): Swing timers, rendering, pipe generation, collision checks, and keyboard input.
- [Bird.java](flappyBird/src/Bird.java): bird state.

The project demonstrates an event-driven game loop and sprite rendering. Scores are held in memory; there is no persistent leaderboard.
