package org.siegan.dojo.thing;

import org.siegan.dojo.Arah;
import org.siegan.dojo.Game;
import org.siegan.dojo.Run;
import org.siegan.dojo.model.Point;

import java.util.*;

public class Snake extends Thing implements AnimalBehavour{

    static {
        System.loadLibrary("setnormal");
    }

    // besar ular termasuk kepalanya
    private int size;
    private Point head;
    private String name;
    // merubah body menjadi bisa memiliki 2 nilai point, 1 point untuk dirinya 1 point unytuk bengkokan terakhir yang dia lewati
//    private List<SnakeBody> bodys;
    private List<Point> bodys;
    private Body isiBody = new Body("wll", "ux");
    private List<Point> bengkok = new ArrayList<>();
    private Arah arah = Arah.RIGHT;
    public static String notif = "";
    public static String notif2 = "";
    public static String notif3 = "";

    private Point temp1 = new Point(0,0);
    private Point temp2 = new Point(0,0);


    public Snake(Builder builder) {
        super(builder.getName(), builder.getAppearance());
        this.head = builder.getPosition();
        this.bodys = builder.getBodys();
        this.size = builder.getSize();

    }

    @Override
    public Point getPointForward(Board board) {

//        if (arah == Arah.RIGHT){
//            // pembatas pinggir
//            if (head.getY() < board.getCol() - 2){
//                return new Point(head.getY()+1, head.getX());
//            }else if (head.getY() == board.getCol() -2){
//                return new Point(1, head.getX());
//            }
//        }else if (arah == Arah.DOWN){
//            // pembatas pinggir
//            if (head.getX() < board.getRow() - 2){
//                return new Point(head.getY(), head.getX()+1);
//            }else if (head.getX() == board.getRow() -2){
//                return new Point(head.getY(), 1);
//            }
//        }else if (arah == Arah.LEFT){
//            // pembatas pinggir
//            if (head.getY() > 1){
//                return new Point(head.getY()-1, head.getX());
//            }else if (head.getY() == 1){
//                return new Point(board.getCol()-2, head.getX());
//            }
//        }else if (arah == Arah.UP){
//            // pembatas pinggir
//            if (head.getX() > 1){
//                return new Point(head.getY(), head.getX()-1);
//            }else if (head.getX() == 1){
//                return new Point(head.getY(), board.getRow()-2);
//            }
//        }
        if (arah == Arah.RIGHT){
            return new Point(head.getY()+1, head.getX());
        }else if (arah == Arah.DOWN){
            return new Point(head.getY(), head.getX()+1);
        }else if (arah == Arah.LEFT){
            return new Point(head.getY()-1, head.getX());
        }else if (arah == Arah.UP){
            return new Point(head.getY(), head.getX()-1);
        }
        throw new RuntimeException("jangkrik forward");
    }

    @Override
    public Point getPointRight(Board board) {
//        notif2 = "pointRight";
        if (arah == Arah.RIGHT){
            // ke bawah
            if (head.getX() < board.getRow() - 2){
                return new Point(head.getY(), head.getX()+1);
            }else if (head.getX() == board.getRow() -2){
                return new Point(head.getY(), 1);
            }
        }else if (arah == Arah.DOWN){

            // ke kiri
            if (head.getY() > 1){
                return new Point(head.getY()-1, head.getX());
            }else if (head.getY() == 1){
                return new Point(board.getCol()-2, head.getX());
            }
        }else if (arah == Arah.LEFT){

            // ke atas
            if (head.getX() > 1){
                return new Point(head.getY(), head.getX()-1);
            }else if (head.getX() == 1){
                return new Point(head.getY(), board.getRow()-2);
            }
        }else if (arah == Arah.UP){

            // ke kanan
            if (head.getY() < board.getCol() - 2){
                return new Point(head.getY()+1, head.getX());
            }else if (head.getY() == board.getCol() -2){
                return new Point(1, head.getX());
            }
        }
        return null;
    }

    @Override
    public Point getPointLeft(Board board) {
//        notif2 = "pointLeft";
        if (arah == Arah.RIGHT){
            // ke atas
            if (head.getX() > 1){
                return new Point(head.getY(), head.getX()-1);
            }else if (head.getX() == 1){
                return new Point(head.getY(), board.getRow()-2);
            }
        }else if (arah == Arah.DOWN){
            // ke kanan
            if (head.getY() < board.getCol() - 2){
                return new Point(head.getY()+1, head.getX());
            }else if (head.getY() == board.getCol() -2){
                return new Point(1, head.getX());
            }
        }else if (arah == Arah.LEFT){
            // ke bawah
            if (head.getX() < board.getRow() - 2){
                return new Point(head.getY(), head.getX()+1);
            }else if (head.getX() == board.getRow() -2){
                return new Point(head.getY(), 1);
            }
        }else if (arah == Arah.UP){
            // ke kiri
            if (head.getY() > 1){
                return new Point(head.getY()-1, head.getX());
            }else if (head.getY() == 1){
                return new Point(board.getCol()-2, head.getX());
            }
        }
        return null;
    }

    public void getPointBackward(Board board){

    }

    @Override
    public void StepForward(Board board) {
        // gerakan kepala
        board.putObject(head, null);
        temp1.setX(head.getX());
        temp1.setY(head.getY());

        temp2.setX(getPointForward(board).getX());
        temp2.setY(getPointForward(board).getY());
        notif =  "Koordinat head : "+head.getX()+","+head.getY();
        notif2 =  "Koordinat temp2 : "+temp2.getX()+","+temp2.getY();

        if (board.getElement(temp2) instanceof Buah ){
            int score = ((Buah) board.getElement(temp2)).getScore();
            this.size = this.size + score;
            // set appearance jumlah di kepala;
            String kepala = "";
            if (this.size < 10 ){
                kepala = "0"+this.size;
            }else if (size < 100){
                kepala = Integer.toString(size);
            }else{
                kepala = ":)";
            }
            this.setAppearance(kepala);
            for (int i = 0; i < score; i++) {
                // secara default ngetem di koordinat 1,1 dahulu sebelum nanti ngikut teman2nya
                bodys.add(new Point(1, 1));
            }
        }else if (board.getElement(temp2) instanceof Body){
            Game.gameRunning = false;
            System.out.println("Menabrak body Mase...");
//            terminalRawToNormal();
//            Run.terminalRawToNormal();
//            throw new RuntimeException("menabrak body mase"+
//                    "\nKoordinat depan : "+temp2.getX()+","+temp2.getY()+
//                    "\nKoordinat head : "+head.getX()+","+head.getY());

        }
        else if (board.getElement(temp2) instanceof Wall){
            Game.gameRunning = false;
            System.out.println("Menabrak wall Mase...");
//            terminalRawToNormal();
//            Run.terminalRawToNormal();
//            throw new RuntimeException("menabrak Wall mase"+
//                    "\nKoordinat depan : "+temp2.getX()+","+temp2.getY()+
//                    "\nKoordinat head : "+head.getX()+","+head.getY());
        }
        head.setX(temp2.getX());
        head.setY(temp2.getY());

        // menampilkan head kembali
        board.putObject(head, this);
        // mulai menggerakan body
        StepBody(board);
    }

    void StepBody(Board board){
//        i
        // cara temp tetapi tembus
        // mencoba arah normal dahulu
        for (Point bd : bodys){
            board.putObject(bd, null);
            temp2.setX(bd.getX());
            temp2.setY(bd.getY());
            bd.setX(temp1.getX());
            bd.setY(temp1.getY());
            temp1.setX(temp2.getX());
            temp1.setY(temp2.getY());
            board.putObject(bd, isiBody);
        }
    }

    public int getSize() {
        return size;
    }

    public void UbahArah(Arah arah){
        if (
                // logika di sini masih salah
//            (this.arah == Arah.RIGHT && arah == Arah.RIGHT) || (this.arah == Arah.RIGHT && arah == Arah.LEFT) ||
//            (this.arah == Arah.DOWN && arah == Arah.DOWN) || (this.arah == Arah.DOWN && arah == Arah.UP) ||
//            (this.arah == Arah.LEFT && arah == Arah.LEFT) || (this.arah == Arah.LEFT && arah == Arah.RIGHT) ||
//            (this.arah == Arah.UP && arah == Arah.UP) || (this.arah == Arah.UP && arah == Arah.DOWN)
                // mencoba
                // saat arah ke kanan
            ((bodys.getFirst().getX() == head.getX() && bodys.getFirst().getY() == head.getY()-1) && (arah == Arah.RIGHT || arah == Arah.LEFT)) ||
                // saat arah ke kiri
            ((bodys.getFirst().getX() == head.getX() && bodys.getFirst().getY() == head.getY()+1) && (arah == Arah.RIGHT || arah == Arah.LEFT)) ||
                // saat arah ke atas
            ((bodys.getFirst().getX() == head.getX()+1 && bodys.getFirst().getY() == head.getY()) && (arah == Arah.UP || arah == Arah.DOWN)) ||
                // saat arah ke bawah
            ((bodys.getFirst().getX() == head.getX()-1 && bodys.getFirst().getY() == head.getY()) && (arah == Arah.UP || arah == Arah.DOWN))
        ){
            // maka arah tidak perlu diubah
        }else{
            this.arah = arah;
//            notif3 = arah.toString();
//            notif3 = Game.arahe.toString();

        }
    }

    private class ChangeDirectionTask extends TimerTask {
        @Override
        public void run() {
            // Dapatkan arah acak
            Random random = new Random();
            Arah newDirection = Arah.values()[random.nextInt(Arah.values().length)];
            UbahArah(newDirection);

        }
    }
    public void showNotif(){
        System.out.println("notif : "+notif);
    }
    public void showNotif2(){
        System.out.println("notif2 : "+notif2);
    }
    public void showNotif3(){
        System.out.println("notif3 : "+notif3);
    }



    public Point getHead() {
        return head;
    }

    public List<Point> getBodys() {
        return bodys;
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
            bodys = new ArrayList<Point>();
            for (int i = 1; i <= getSize() - 1; i++) {
                bodys.add(new Point(posY-i, posX));
            }
            return this;
        }
        public Snake build(){
            return new Snake(this);
        }
    }

    public static native void terminalRawToNormal();

}
