package com.javarush.games.snake;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

import java.util.Random;

public class Apple extends GameObject {
    public boolean isAlive = true;
    public static String app;

    public static void gen() {
        Random rand = new Random();
        int a=0;
        int b=3;
        int x  =  a +  rand.nextInt(b - a + 1);
        switch (x) {
            case (0):
                app = "\uD83C\uDF4E";
                break;
            case (1):
                app = "\uD83E\uDD5D";
                break;
            case (2):
                app = "\uD83C\uDF49";
                break;
            case (3):
                app = "\uD83E\uDD6D";
                break;
        }
    }

    public void draw (Game game) {
        game.setCellValueEx(x, y, Color.NONE, app, Color.GREEN, 75);
    }

    public Apple(int xCoord, int yCoord) {
        super(xCoord, yCoord);
        gen();
    }
}
