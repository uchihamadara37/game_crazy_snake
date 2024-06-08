package org.siegan.dojo.thing;

import org.siegan.dojo.model.Point;

public interface AnimalBehavour {
    void StepForward(Board board);

    Point getPointForward(Board board);
    Point getPointRight(Board board);
    Point getPointLeft(Board board);
}
