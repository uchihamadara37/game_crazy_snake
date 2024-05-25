package org.siegan.dojo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Run {
    public static void main(String[] args) {

        int row = 50;
        int col = 50;

        Game game = Game.getBuilder()
                .createBoard(row, col)
                .createWalls()
                .build();
        game.render();
    }
}