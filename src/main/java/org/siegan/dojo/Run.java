package org.siegan.dojo;

import org.siegan.dojo.thing.Snake;
import org.siegan.dojo.thing.Buah;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


public class Run {

    static {
        System.loadLibrary("native");
    }
    static int row = 40;
    static int col = 40;

    static Snake snake = Snake.getBuilder()
            .setName("ular")
            .setAppearance("OO")
            .setPosX(20)
            .setPosY(15)
            .setSize(8)
            .generateBody()
            .build();
    static Game game = Game.getBuilder()
            .createBoard(row, col)
            .createWalls()
            .createSnake(snake)
            .createSnakePopulation()
            .putBuahOnBoard()
            .build();

    public static void main(String[] args) {




//
//        Buah buah = new Buah("buah", "OK", 10, 10);
//        Buah buah1 = new Buah("buah", "OK", 15, 30);
//        Buah buah2 = new Buah("buah", "OK", 20, 20);
//        Buah buah3 = new Buah("buah", "OK", 25, 12);
//        Buah buah4 = new Buah("buah", "OK", 30, 20);


//
//        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(new MyRunnable());
        thread.start(); // Memulai thread
        game.render();
        System.out.println("Tekan 'q' untuk keluar!");
        terminalRawToNormal();
    }

    public static native void controlls();

    public static native void terminalRawToNormal();

    public static void controlUp(){
        snake.UbahArah(Arah.UP);
    }
    public static void controlDown(){
        snake.UbahArah(Arah.DOWN);
    }
    public static void controlRight(){
        snake.UbahArah(Arah.RIGHT);
    }
    public static void controlLeft(){
        snake.UbahArah(Arah.LEFT);
    }


    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            controlls();
        }
    }

}
