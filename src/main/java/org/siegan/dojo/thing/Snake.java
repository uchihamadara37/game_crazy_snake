package org.siegan.dojo.thing;

import org.siegan.dojo.Game;
import org.siegan.dojo.model.Point;

import java.util.ArrayList;
import java.util.List;

public class Snake extends Thing implements AnimalBehavour{

    // besar ular termasuk kepalanya
    private int size;
    private Point head;
    private String name;
    private List<Point> bodys;

    public Snake(Builder builder) {
        super(builder.getName(), builder.getAppearance());
        this.head = builder.getPosition();
        this.bodys = builder.getBodys();
    }
    @Override
    public void StepForward(Board board) {

        int y = head.getY();
        if (y > 0 && y < board.getCol()-2){
            board.putObject(head, null);
            int i = 1;
            for (Point body : bodys){
                board.putObject(body, null);
                body.setY(body.getY()+1);
                board.putObject(body, this);
            }
            head.setY((y+1));
            board.putObject(head, this);
        }else if (y == board.getCol()-2){
            board.putObject(head, null);
            head.setY(1);
            board.putObject(head, this);
        }

    }

    public int getSize() {
        return size;
    }



    public Point getHead() {
        return head;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public static Builder getBuilder(){
        return new Builder();
    }

    public static class Builder{
        private int size;
        private String name;
        private String appearance;
        private int posX, posY;
        private List<Point> bodys;

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public List<Point> getBodys() {
            return bodys;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAppearance(String appearance) {
            this.appearance = appearance;
            return this;
        }

        public Builder setPosX(int posX) {
            this.posX = posX;
            return this;
        }

        public Builder setPosY(int posY) {
            this.posY = posY;
            return this;
        }

        public int getSize() {
            return size;
        }

        public String getAppearance() {
            return appearance;
        }

        public String getName() {
            return name;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosY() {
            return posY;
        }

        public Builder setPosition(int x, int y){
            this.posX = x;
            this.posY = y;
            return this;
        }
        public Point getPosition(){
            return new Point(posY, posX);
        }
        public Builder generateBody(){
            bodys = new ArrayList<>();
            for (int i = 0; i < size-1; i++) {
                bodys.add(new Point(this.getPosY()-(i*1), this.getPosX()));
            }
            return this;
        }
        public Snake build(){
            return new Snake(this);
        }
    }


}
