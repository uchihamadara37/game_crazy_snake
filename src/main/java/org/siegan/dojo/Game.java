package org.siegan.dojo;

import org.siegan.dojo.model.Point;
import org.siegan.dojo.thing.Board;
import org.siegan.dojo.thing.Cell;
import org.siegan.dojo.thing.Snake;
import org.siegan.dojo.thing.Wall;

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

    public void render(){
        board.displayBoard();
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
                if (i == 0 || i == board.getRow() -1){
                    for (int j = 0; j < board.getCol(); j++) {
                        Cell c = new Cell("cell", "* ", i, 0);
                        Wall w = new Wall("wall", "*");
                        c.AddThing(w);
                        walls.add(w);
                        board.putObject(new Point(j, i), c);
                    }


                }else{
                    for (int j = 0; j < board.getCol(); j++) {
                        if (j == 0 || j == board.getCol()-1){
                            Cell c = new Cell("cell", "* ", i, j);
                            Wall w = new Wall("wall", "*");
                            c.AddThing(w);
                            walls.add(w);
                            board.putObject(new Point(j, i), c);
                        }else{
                            Cell c = new Cell("cell", "  ", i, j);
                            Wall w = new Wall("wall", " ");
                            c.AddThing(w);
                            walls.add(w);
                            board.putObject(new Point(j, i), c);
                        }


                    }
                }
            }
            return this;
        }
        public Game build(){
            return new Game(this);
        }

    }
}
