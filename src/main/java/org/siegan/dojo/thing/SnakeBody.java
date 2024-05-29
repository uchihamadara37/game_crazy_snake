package org.siegan.dojo.thing;

import org.siegan.dojo.model.Point;

import java.util.ArrayList;
import java.util.List;

public class SnakeBody {
    private Point body;
    private List<Point> history = new ArrayList<>();
    private Point destination = null;

    public SnakeBody setBody(Point point){
        body = point;
        return this;
    }

    public Point getBody() {
        return body;
    }

    public Point getTujuan() {
        return destination;
    }

    public SnakeBody setTujuan(Point point){
        destination = point;
        return this;
    }

    public SnakeBody addHistoryFiltered(Point dariTujuan){
        if (history.isEmpty()){
            history.add(new Point(dariTujuan.getY(), dariTujuan.getX()));
        }else{
            boolean sudahAda = false;
            for (Point his : history){
                if (his.getY() == dariTujuan.getY() && his.getX() == dariTujuan.getX()){
                    sudahAda = true;
                    break;
                }
            }
            if (!sudahAda){
                history.add(new Point(dariTujuan.getY(), dariTujuan.getX()));
            }
        }
        return this;
    }

    public SnakeBody removeHistory(Point point){
        int i = 0;
        if (!history.isEmpty()){
            for (Point his : history){
                if (his.getX() == point.getX() && his.getY() == point.getY()){
                    history.remove(i);
                    Snake.notif2 = "pernah hapus history";
                    break;
                }
                i++;
            }
        }
        return this;
    }
    public void cekTujuanUntukDihapus(){
        for (Point his : history){
            if (his == destination){
                destination = null;
                break;
            }
        }
    }
    public boolean isHistoryContainsThis(Point point){
        for (Point his : history){
            if (his.getY() == point.getY() && his.getX() == point.getX()){
                return true;
            }
        }
        return false;
    }

    public List<Point> getHistory() {
        return history;
    }
}
