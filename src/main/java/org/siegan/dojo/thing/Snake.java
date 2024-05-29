package org.siegan.dojo.thing;

import org.siegan.dojo.Arah;
import org.siegan.dojo.Game;
import org.siegan.dojo.model.Point;

import java.util.*;

public class Snake extends Thing implements AnimalBehavour{

    // besar ular termasuk kepalanya
    private int size;
    private Point head;
    private String name;
    // merubah body menjadi bisa memiliki 2 nilai point, 1 point untuk dirinya 1 point unytuk bengkokan terakhir yang dia lewati
    private List<SnakeBody> bodys;
    private Wall isiBody = new Wall("wll", "ux");
    private List<Point> bengkok = new ArrayList<>();
    private Arah arah = Arah.RIGHT;
    public static String notif = "";
    public static String notif2 = "";
    public static String notif3 = "";


    public Snake(Builder builder) {
        super(builder.getName(), builder.getAppearance());
        this.head = builder.getPosition();
        this.bodys = builder.getBodys();
        this.size = builder.getSize();


        Timer timer = new Timer();

//        timer.schedule(new ChangeDirectionTask(), 4000, 4000);

    }
    @Override
    public void StepForward(Board board) {
        // gerakan kepala
        board.putObject(head, null);

        if (arah == Arah.RIGHT){
            // pembatas pinggir
            if (head.getY() < board.getCol() - 2){
                head.setY(head.getY()+1);
            }else if (head.getY() == board.getCol() -2){
                head.setY(1);
//                arah = Arah.DOWN;
            }
        }else if (arah == Arah.DOWN){
            // pembatas pinggir
            if (head.getX() < board.getRow() - 2){
                head.setX(head.getX()+1);
            }else if (head.getX() == board.getRow() -2){
                head.setX(1);
//                arah = Arah.LEFT;
            }
        }else if (arah == Arah.LEFT){
            // pembatas pinggir
            if (head.getY() > 1){
                head.setY(head.getY()-1);
            }else if (head.getY() == 1){
                head.setY(board.getCol()-2);
//                arah = Arah.UP;
            }
        }else if (arah == Arah.UP){
            // pembatas pinggir
            if (head.getX() > 1){
                head.setX(head.getX()-1);
            }else if (head.getX() == 1){
                head.setX(board.getRow()-2);
//                arah = Arah.RIGHT;
            }
        }
        // menampilkan head kembali
        board.putObject(head, this);
        // mulai menggerakan body
        StepBody(board);
    }

    void StepBody(Board board){
        int i = 1;
        for (SnakeBody snakeBody : bodys){
            if (!bengkok.isEmpty()){
                // mengecek jika ada yang sudah melalui bengkok yang cuma 1
                if (snakeBody.getTujuan().getY() == snakeBody.getBody().getY() && snakeBody.getTujuan().getX() == snakeBody.getBody().getX()){
//                    notif = "bengkok 1 tiba di tujuan";
                    // tujuan akhirnya menjadi Head
                    snakeBody.addHistoryFiltered(new Point(snakeBody.getTujuan().getY(), snakeBody.getTujuan().getX()));
                    for (Point bengk : bengkok){
                        if (snakeBody.isHistoryContainsThis(bengk)){
                            snakeBody.setTujuan(new Point(head.getY(), head.getX()));
                        }else{
                            snakeBody.setTujuan(new Point(bengk.getY(), bengk.getX()));
                            break;
                        }
                    }
                    notif3 = "jangkrik";
//                    notif2 = "lewat bengkok mase.";
                    // tambahkan history bengkok yang tekah dilalui
                    if (snakeBody.getHistory().isEmpty()){
                        notif2 = "history kosong";
                    }
                }
                // jika body terakhir sedang melalui bengkok,
//                notif2 = "koordinat tail "+bodys.get(bodys.size()-1).getBody().getX()+","+bodys.get(bodys.size()-1).getBody().getY()+" bengk "+bengkok.getFirst().getX()+","+bengkok.getFirst().getY();
                if (
                        (bodys.getLast().getBody().getY() == bengkok.getFirst().getY()) &&
                        (bodys.getLast().getBody().getX() == bengkok.getFirst().getX())
                ){
                    for (SnakeBody sB : bodys){
                        // remove history
                        sB.removeHistory(bengkok.getFirst());
                    }
                    bengkok.removeFirst();
                    notif = "menghapus history";
                }
            }else{
                // jika tidak ada bengkok
                snakeBody.setTujuan(new Point(head.getY(), head.getX()));
            }
            notif = "bengkok ada "+bengkok.size();
            if (snakeBody.getTujuan() == null){
                throw new RuntimeException("tujuan malah masih kosong");
            }

            // identifikasi posisi body dari tujuan secara vertical dan horizontal
            if (snakeBody.getBody().getX() == snakeBody.getTujuan().getX()){
                if (
                        (snakeBody.getBody().getY() < snakeBody.getTujuan().getY() &&
                        (snakeBody.getTujuan().getY() - snakeBody.getBody().getY()) <= getSize() &&
                        (snakeBody.getTujuan().getY()-snakeBody.getBody().getY() > 0))
                        ||
                        ((snakeBody.getBody().getY() > snakeBody.getTujuan().getY()) &&
                        (snakeBody.getTujuan().getY() - snakeBody.getBody().getY()) <= -(board.getCol()-getSize() - 2))
                ) {
                    // maka tujuan berada di kanannya, menuju kanan meski tujuan sudah jadi 1 lagi.
                    if (snakeBody.getBody().getY() < board.getCol() -2){
                        board.putObject(snakeBody.getBody(), null);
                        snakeBody.getBody().setY(snakeBody.getBody().getY() + 1);
                        board.putObject(snakeBody.getBody(), isiBody);
                    }else if (snakeBody.getBody().getY() == board.getCol() -2){
                        board.putObject(snakeBody.getBody(), null);
                        snakeBody.getBody().setY(1);
                        board.putObject(snakeBody.getBody(), isiBody);
                    }

                }else if (
                        ((snakeBody.getBody().getY() > snakeBody.getTujuan().getY()) &&
                        (snakeBody.getBody().getY() - snakeBody.getTujuan().getY()) <= getSize() &&
                        (snakeBody.getBody().getY() - snakeBody.getTujuan().getY()) > 0)
                        ||
                        ((snakeBody.getBody().getY() < snakeBody.getTujuan().getY()) &&
                        (snakeBody.getTujuan().getY() - snakeBody.getBody().getY()) >= (board.getCol()-getSize() - 2))
                ){
                    // maka tujaun berada di sebelah kirinya, menuju kiri
                    if (snakeBody.getBody().getY() > 1){
                        board.putObject(snakeBody.getBody(), null);
                        snakeBody.getBody().setY(snakeBody.getBody().getY()-1);
                        board.putObject(snakeBody.getBody(), isiBody);
                    }else if (snakeBody.getBody().getY() == 1){
                        board.putObject(snakeBody.getBody(), null);
                        snakeBody.getBody().setY(board.getCol() - 2);
                        board.putObject(snakeBody.getBody(), isiBody);
                    }

                }else{
//                        notif2 = "sama x malah sama y indx"+i;

                        throw new RuntimeException("x sama tapi y malah sama index ke "+i+"\n Koordinat = "+snakeBody.getBody().getX()+","+snakeBody.getBody().getY()+" tujuan : "+snakeBody.getTujuan().getX()+","+snakeBody.getTujuan().getY());
//                        snakeBody.setTujuan(null);
//                        snakeBody.addHistoryFiltered(new Point(snakeBody.getTujuan().getY(), snakeBody.getTujuan().getX()));
//                        snakeBody.setTujuan(new Point(head.getY(), head.getX()));
                }
            }else if (snakeBody.getBody().getY() == snakeBody.getTujuan().getY()){
                if (
                    ((snakeBody.getBody().getX() < snakeBody.getTujuan().getX()) &&
                    (snakeBody.getTujuan().getX() - snakeBody.getBody().getX() <= getSize()) &&
                    (snakeBody.getTujuan().getX() - snakeBody.getBody().getX() > 0))
                    ||
                    ((snakeBody.getBody().getX() > snakeBody.getTujuan().getX()) &&
                    (snakeBody.getTujuan().getX() - snakeBody.getBody().getX() <= -(board.getRow() - getSize() - 2)))
                ){
                    // jika minus maka tujuannya berada di bawahnya
                    if (snakeBody.getBody().getX() < board.getRow() - 2){
                        board.putObject(snakeBody.getBody(), null);
                        snakeBody.getBody().setX(snakeBody.getBody().getX()+1);
                        board.putObject(snakeBody.getBody(), isiBody);
                    }else if (snakeBody.getBody().getX() == board.getRow() - 2){
                        board.putObject(snakeBody.getBody(), null);
                        snakeBody.getBody().setX(1);
                        board.putObject(snakeBody.getBody(), isiBody);
                    }
                }else if (
                        ((snakeBody.getBody().getX() > snakeBody.getTujuan().getX()) &&
                        (snakeBody.getBody().getX() - snakeBody.getTujuan().getX() <= getSize()) &&
                        (snakeBody.getBody().getX() - snakeBody.getTujuan().getX() > 0))
                        ||
                        ((snakeBody.getBody().getX() < snakeBody.getTujuan().getX()) &&
                        (snakeBody.getBody().getX() - snakeBody.getTujuan().getX() <= -(board.getRow()-getSize()-2)))
                ){
                    // maka tujuan berada di atasnya
                    if (snakeBody.getBody().getX() > 1){
                        board.putObject(snakeBody.getBody(), null);
                        snakeBody.getBody().setX(snakeBody.getBody().getX()-1);
                        board.putObject(snakeBody.getBody(), isiBody);
                    }else if (snakeBody.getBody().getX() == 1){
                        board.putObject(snakeBody.getBody(), null);
                        snakeBody.getBody().setX(board.getRow()-2);
                        board.putObject(snakeBody.getBody(), isiBody);
//                            notif2 = "lewat munggaaaaahh mase.";
                    }
                }else{
//                        notif2 = "sama y malah sama x";
                        throw new RuntimeException("y sama tapi x sama");
//                        snakeBody.addHistoryFiltered(new Point(snakeBody.getTujuan().getY(), snakeBody.getTujuan().getX()));
//                        snakeBody.setTujuan(new Point(head.getY(), head.getX()));
                }
            }else{
//                    throw new RuntimeException("yongalah");
                    boolean punya = snakeBody.isHistoryContainsThis(bengkok.getLast());
                    throw new RuntimeException("Yongalah index ke "+i+
                            "\nKoordinat = "+snakeBody.getBody().getX()+","+snakeBody.getBody().getY()+" tujuan : "+snakeBody.getTujuan().getX()+","+snakeBody.getTujuan().getY() +
                            "\nBengkok ada "+bengkok.size() + " terakhir di: "+bengkok.getLast().getX()+","+bengkok.getLast().getY()+
                            "\nHistory yang ada : sejumlah "+snakeBody.getHistory().size()+" koordinat1 "+snakeBody.getHistory().getFirst().getX()+","+snakeBody.getHistory().getFirst().getY()+
                            "\nHistory punya history bengkok terakhir : "+punya);
//                    notif2 = "kacau, beda x beda y";
            }

            i++;
        } // end for bodys
    }

    public int getSize() {
        return size;
    }

    public void UbahArah(Arah arah){
        if (
            (this.arah == Arah.RIGHT && arah == Arah.RIGHT) || (this.arah == Arah.RIGHT && arah == Arah.LEFT) ||
            (this.arah == Arah.DOWN && arah == Arah.DOWN) || (this.arah == Arah.DOWN && arah == Arah.UP) ||
            (this.arah == Arah.LEFT && arah == Arah.LEFT) || (this.arah == Arah.LEFT && arah == Arah.RIGHT) ||
            (this.arah == Arah.UP && arah == Arah.UP) || (this.arah == Arah.UP && arah == Arah.DOWN)
        ){
            // maka arah tidak perlu diubah
        }else{
            this.arah = arah;
            notif3 = arah.toString();
            bengkok.add(new Point(head.getY(), head.getX()));

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

    public List<SnakeBody> getBodys() {
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
        private List<SnakeBody> bodys;

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public List<SnakeBody> getBodys() {
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
            bodys = new ArrayList<SnakeBody>();
            for (int i = 1; i <= getSize() - 1; i++) {
                SnakeBody group =  new SnakeBody();
                group.setBody(new Point(posY-i, posX));

                bodys.add(group);
            }
            return this;
        }
        public Snake build(){
            return new Snake(this);
        }
    }


}
