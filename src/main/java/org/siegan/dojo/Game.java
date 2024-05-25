package org.siegan.dojo;

import org.siegan.dojo.model.Point;
import org.siegan.dojo.thing.Board;
import org.siegan.dojo.thing.Cell;
import org.siegan.dojo.thing.Snake;
import org.siegan.dojo.thing.Wall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//    class untuk mengkontrol jalannya game
public class Game {
    private final Board board;
    private List<Wall> walls;
    private Snake snake;
    private int speed;

    public Game(Builder builder){
        this.board = builder.board;
        this.walls = builder.walls;
        this.snake = builder.snake;
        this.speed = builder.speed;
    }
    public static Builder getBuilder(){
        return new Builder();
    }

    public void render() throws InterruptedException, IOException {
        while (true){
            board.displayBoard();
            snake.StepForward(board);
            Thread.sleep(100);

            new ProcessBuilder("clear").inheritIO().start().waitFor();
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
            }
            return this;
        }

    }
}
