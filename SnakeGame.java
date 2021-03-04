package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;

    public void setScore(int scor) {
        score = scor;
    }

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WON! CONGRATS!", Color.WHEAT, 60);
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "You lost! Boooooo!", Color.WHEAT, 60);
    }

    private void createNewApple() {
        while (true) {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
            if (!snake.checkCollision(apple)) {
                break;
            }
        }
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) snake.setDirection(Direction.LEFT);
        if (key == Key.RIGHT) snake.setDirection(Direction.RIGHT);
        if (key == Key.UP) snake.setDirection(Direction.UP);
        if (key == Key.DOWN) snake.setDirection(Direction.DOWN);
        if (key == Key.SPACE && isGameStopped == true) createGame();
    }

    private void createGame() {
        score = 0;
        setScore(score);
        turnDelay = 160;
        setTurnTimer(turnDelay);
        Snake newsnake = new Snake(WIDTH/2, HEIGHT/2);
        snake = newsnake;
        createNewApple();
        isGameStopped = false;
        drawScene();
    }

    @Override
    public void onTurn(int a) {
        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
            score = score + 8;
            setScore(score);
            turnDelay = turnDelay - 5;
            setTurnTimer(turnDelay);
        }
        if (!snake.isAlive) gameOver();
        if (snake.getLength() > 28) win();
        drawScene();
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }
}
