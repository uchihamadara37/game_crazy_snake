package org.siegan.dojo;

import org.siegan.dojo.thing.Snake;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Run {
    public static void main(String[] args) {

        int row = 40;
        int col = 40;

        Snake snake = Snake.getBuilder()
                .setName("ular")
                .setAppearance("OO")
                .setPosX(20)
                .setPosY(10)
                .setSize(8)
                .generateBody()
                .build();


        Game game = Game.getBuilder()
                .createBoard(row, col)
                .createWalls()
                .createSnake(snake)
                .createSnakePopulation()
                .build();

        game.render();
    }
}