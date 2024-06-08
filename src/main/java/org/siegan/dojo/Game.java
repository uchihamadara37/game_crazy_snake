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

    private static final Object lock = new Object();
    public static Arah arahe;

    private final Board board;
    private List<Wall> walls;
    private Snake snake;
    private int speed;
//    private Scanner sc = new Scanner(System.in);
    public static boolean gameRunning = true;

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

            while(gameRunning) {
                try {
                    int input = System.in.read();
    //                    while (System.in.available() > 0) {
    //                        System.in.read(); // Membersihkan buffer
    //                    }
                    if (input != -1) { // Periksa apakah ada input
                        if (input == '\n') { // Jika input adalah newline, baca karakter berikutnya
                            input = System.in.read();
                        }
                    }
                    // Periksa input dan lakukan tindakan yang sesuai
                    synchronized (Game.lock) {
                        System.out.println("input e " + input);
                        switch (input) {
                            case 'w':
                                // Gerakkan ular ke atas
    //                                System.out.println("w");
    //                                snake.UbahArah(Arah.UP);
                                System.out.println("atas pkpk");
                                Game.arahe = Arah.UP;
                                break;
                            case 'a':
                                // Gerakkan ular ke kiri
                                //System.out.println("A");
    //                                snake.UbahArah(Arah.LEFT);
                                System.out.println("kiri pkpk");
                                Game.arahe = Arah.LEFT;
                                break;
                            case 's':
                                // Gerakkan ular ke bawah
                                //System.out.println("S");
    //                                snake.UbahArah(Arah.DOWN);
                                System.out.println("bawah pkpk");
                                Game.arahe = Arah.DOWN;
                                break;
                            case 'd':
                                // Gerakkan ular ke kanan
                                //System.out.println("D");
    //                                snake.UbahArah(Arah.RIGHT);
                                System.out.println("kanan pkpk");
                                Game.arahe = Arah.RIGHT;
                                break;
                            // Tambahkan kasus lain jika diperlukan
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void render(){
//        synchronized (Game.lock){
//            Game.arahe = Arah.RIGHT;
//        }
        try {
            int ok2 = 1;
            while (gameRunning){
                new ProcessBuilder("clear").inheritIO().start().waitFor();

                ok2 = (ok2 + 1) % 10;


                board.displayBoard();
//                snake.StepForward(board);

                snake.showNotif();
                snake.showNotif2();
                snake.showNotif3();

                boolean checkAdaBuah = false;
                for(int i = 1; i < board.getRow()-1; i++){
                    for (int j = 0; j < board.getCol()-1; j++) {
                        if (board.getElement(new Point(j, i)) instanceof Buah){
                            checkAdaBuah = true;
                            break;
                        }
                    }
                }

//                if (ok2 == 0){
                if (!checkAdaBuah){
                    Random random2 = new Random();
                    int KoorX = random2.nextInt(board.getRow()-2)+1;
                    int KoorY = random2.nextInt(board.getRow()-2)+1;
                    Buah buah = new Buah("buah", "OK", KoorX, KoorY);

                    board.putObject(buah.getKoordinat(), buah);
                }

                if (snake.getLevel() == 0){
                    Thread.sleep(200);
                    Snake.notif3 = "Level 0";
                    if (snake.getSize() == 7){
                        snake.setLevel(1);
                        // menghapus body
                        for (int i = snake.getBodys().size()-1; i > 2; i--) {
                            board.putObject(snake.getBodys().getLast(), null);
                            snake.getBodys().removeLast();
                        }
                        snake.setSize(4);
                    }
                }else if (snake.getLevel() == 1){
                    Thread.sleep(150);
                    Snake.notif3 = "Level 1";
                    if (snake.getSize() == 15){
                        snake.setLevel(2);
                        // menghapus body
                        for (int i = snake.getBodys().size()-1; i > 2; i--) {
                            board.putObject(snake.getBodys().getLast(), null);
                            snake.getBodys().removeLast();
                        }
                        snake.setSize(4);
                    }

                }else if (snake.getLevel() == 2){
                    Thread.sleep(100);
                    Snake.notif3 = "Level 2";
                    if (snake.getSize() == 20){
                        snake.setLevel(3);
                        // menghapus body
                        for (int i = snake.getBodys().size()-1; i > 2; i--) {
                            board.putObject(snake.getBodys().getLast(), null);
                            snake.getBodys().removeLast();
                        }
                        snake.setSize(4);
                    }
                }else{
                    Thread.sleep(70);
                    Snake.notif3 = "Level Ultimate";
                }
                snake.StepForward(board);


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
        List<Buah> listBuah = new ArrayList<>();
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
                    for(Point sb : snake.getBodys()){
                        board.putObject(sb, new Body("body", "o"+i));
                        i++;
                    }
                }
            }
            return this;
        }
        public Builder addBuah(Buah buah){
            this.listBuah.add(buah);
            return this;
        }
        public Builder putBuahOnBoard(){
            if (!listBuah.isEmpty()){
                for (Buah buah : listBuah){
                    board.putObject(buah.getKoordinat(), buah);
                }
            }
            return this;
        }

    }
}
