package org.siegan.dojo.thing;

import org.siegan.dojo.model.Point;

import java.util.ArrayList;
import java.util.List;

public class Board extends Thing{

    private final int row;
    private final int col;
    private final List<List<Cell>> board;

    public Board(String name, String appearance, int row, int col) {
        super(name, appearance);
        this.col = col;
        this.row = row;
        board = new ArrayList<>();
        for (int i = 0; i < this.row; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < this.col; j++) {
                board.get(i).add(new Cell("wall", "", i, j));
            }
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public void displayBoard(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board.get(i).get(j).DisplayCell();
            }
//            System.out.print("k");
            System.out.println();
        }
    }
    public void putObject(Point point, Thing thing){
        if (thing != null){
            this.getBoard().get(point.getX()).get(point.getY()).AddThing(thing);
        }else{
            this.getBoard().get(point.getX()).get(point.getY()).removeThing();
        }
    }

    public List<List<Cell>> getBoard() {
        return board;
    }
}
