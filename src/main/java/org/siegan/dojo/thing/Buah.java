package org.siegan.dojo.thing;

import org.siegan.dojo.model.Point;

import java.util.ArrayList;
import java.util.List;

public class Buah extends Thing{
    private Point koordinat;
    private int score = 1;
    private int sizeX = 1;
    private int sizeY = 1;

    public Buah(String name, String appearance, int posX, int posY) {
        super(name, appearance);
        koordinat = new Point(posY, posX);
    }

    public void setKoordinat(Point koordinat) {
        this.koordinat = new Point(koordinat.getY(), koordinat.getX());
    }

    public Point getKoordinat() {
        return koordinat;
    }

    public int getScore() {
        return score;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
