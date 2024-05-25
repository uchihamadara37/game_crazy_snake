package org.siegan.dojo;

import org.siegan.dojo.thing.Snake;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Run {
    public static void main(String[] args) {

        int row = 50;
        int col = 50;

        Snake snake = Snake.getBuilder()
                .setName("ular")
                .setAppearance("<>")
                .setPosX(5)
                .setPosY(5)
                .setSize(5)
                .generateBody()
                .build();


        Game game = Game.getBuilder()
                .createBoard(row, col)
                .createWalls()
                .createSnake(snake)
                .createSnakePopulation()
                .build();

        try {
            game.render();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}