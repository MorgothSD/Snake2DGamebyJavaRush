package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public boolean isAlive = true;
    private List<GameObject> snakeParts = new ArrayList<GameObject>();
    private static final String HEAD_SIGN = "\uD83D\uDC0D";
    private static final String BODY_SIGN = "\u26AB";
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        for (int i = 0; i < 3; i++) {
            snakeParts.add(new GameObject(x,y));
            x++;
        }
    }

    public int getLength() {
        return snakeParts.size();
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x == SnakeGame.WIDTH && this.direction == Direction.RIGHT) newHead.x = 0;
        if (newHead.x < 0 && this.direction == Direction.LEFT) newHead.x = SnakeGame.WIDTH-1;
        if (newHead.y < 0 && this.direction == Direction.UP) newHead.y = SnakeGame.HEIGHT-1;
        if (newHead.y == SnakeGame.HEIGHT && this.direction == Direction.DOWN) newHead.y = 0;

        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
            snakeParts.add(0, newHead);
            return;
        }

        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }

        snakeParts.add(0, newHead);
        removeTail();
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject g : snakeParts) {
            if (g.x == gameObject.x && g.y == gameObject.y) return true;
        }
        return false;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size()-1);
    }

    public GameObject createNewHead() {
        GameObject gameObject = null;
        if (direction == Direction.LEFT) gameObject = new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y);
        if (direction == Direction.RIGHT) gameObject = new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
        if (direction == Direction.DOWN) gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y+1);
        if (direction == Direction.UP) gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y-1);
        return gameObject;
    }

    public void setDirection(Direction direction) {
        if (this.direction == Direction.DOWN && direction == Direction.UP) return;
        if (this.direction == Direction.UP && direction == Direction.DOWN) return;
        if (this.direction == Direction.LEFT && direction == Direction.RIGHT) return;
        if (this.direction == Direction.RIGHT && direction == Direction.LEFT) return;
        if (this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x) return;
        if (this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x) return;
        if (this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y) return;
        if (this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) return;
        this.direction = direction;
    }

    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            if (isAlive == true) {
                if (i == 0)
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                else game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
            }
            else {
                if (i == 0)
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                else game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
        }
    }
}
