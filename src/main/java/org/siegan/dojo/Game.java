package org.siegan.dojo;

import org.siegan.dojo.model.Point;
import org.siegan.dojo.thing.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//    class untuk mengkontrol jalannya game
public class Game {
    private final Board board;
    private List<Wall> walls;
    private Snake snake;
    private int speed;
//    private Scanner sc = new Scanner(System.in);
    private static boolean gameRunning = true;

    private static final Object lock = new Object();

    public Game(Builder builder){
        this.board = builder.board;
        this.walls = builder.walls;
        this.snake = builder.snake;
        this.speed = builder.speed;

        // menciptakan tread dengan multi tread
//        Thread inputThread = new Thread(new InputHandler());
//        inputThread.start();
    }
    public static Builder getBuilder(){
        return new Builder();
    }
    private class InputHandler implements Runnable {
        @Override
        public void run() {
            try {
                while (gameRunning) {
                    int input = System.in.read();
                    while (System.in.available() > 0) {
                        System.in.read(); // Membersihkan buffer
                    }

                    // Periksa input dan lakukan tindakan yang sesuai
                    synchronized (lock){
                        switch (input) {
                            case 'w':
                                // Gerakkan ular ke atas
//                                System.out.println("w");
                                snake.UbahArah(Arah.UP);
                                break;
                            case 'a':
                                // Gerakkan ular ke kiri
                                //System.out.println("A");
                                snake.UbahArah(Arah.LEFT);
                                break;
                            case 's':
                                // Gerakkan ular ke bawah
                                //System.out.println("S");
                                snake.UbahArah(Arah.DOWN);
                                break;
                            case 'd':
                                // Gerakkan ular ke kanan
                                //System.out.println("D");
                                snake.UbahArah(Arah.RIGHT);
                                break;
                            // Tambahkan kasus lain jika diperlukan
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void render(){
        try {
            int ok = 1;
            while (gameRunning){
                ok = (ok + 1 ) % 4;
                board.displayBoard();
                snake.StepForward(board);

                snake.showNotif();
                snake.showNotif2();
                snake.showNotif3();
                if (ok == 0){
                    Random random = new Random();
                    Arah arah = Arah.values()[random.nextInt(Arah.values().length)];
                    snake.UbahArah(arah);
                }
                Thread.sleep(200);

                new ProcessBuilder("clear").inheritIO().start().waitFor();

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static class Builder{
        Board board;
        List<Wall> walls;
        Snake snake;
        int speed;

        public Builder createBoard(int row, int col){
            board = new Board("Board", "", row, col);
            return this;
        }
        public Builder createWalls(){
            // method membuat dinding area game
            walls = new ArrayList<>();
            for (int i = 0; i < board.getRow(); i++) {
                for (int j = 0; j < board.getCol(); j++) {
                    if (i == 0 || i == board.getRow() -1 || j == 0 || j == board.getCol() -1) {
                        Wall w = new Wall("wall", "* ");

                        board.putObject(new Point(j, i), w);
                    }
                }
            }
            return this;
        }
        public Game build(){
            return new Game(this);
        }

        public Builder createSnake(Snake snake){
            this.snake = snake;
            return this;
        }
        public Builder createSnakePopulation(){
            if (snake != null){
                board.putObject(snake.getHead(), snake);
                if (!snake.getBodys().isEmpty()){
                    int i = 1;
                    for(SnakeBody sb : snake.getBodys()){
                        board.putObject(sb.getBody(), new Wall("wall", "o"+i));
                        i++;
                    }
                }
            }
            return this;
        }

    }
}
