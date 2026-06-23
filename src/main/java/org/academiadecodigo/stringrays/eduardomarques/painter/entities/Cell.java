package org.academiadecodigo.stringrays.eduardomarques.painter.entities;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.ColorWrapper;
import java.io.Serializable;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.RectangleWrapper;
import org.academiadecodigo.stringrays.eduardomarques.painter.config.Config;

/**
 * Basic unit, define a small square
 * The grid is made up of many cells
 */
public class Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RectangleWrapper cellShape;
    private final Position position;
    private ColorWrapper color;
    private boolean painted;

    /**
     * Initializes one cell based on one position
     *
     * @param position from wich to equal values on new object
     */
    public Cell(Position position) {
        this.position = position;
        this.cellShape = new RectangleWrapper(
                position.getX(),
                position.getY(),
                Config.CELL_SIZE,
                Config.CELL_SIZE
        );
    }

    /**
     * Initializes the cell, defined color to avoid null
     * and draws it on screen
     */
    public void initCell() {
        color = ColorWrapper.BLACK;
        cellShape.setColor(color.unwrap());
        cellShape.draw();
        painted = false;
    }

    public void copyState(Cell other) {
        if (other.isPainted()) {
            setColor(other.getColor());
            paint();
            return;
        }
        initCell();
    }

    public synchronized void paint() {
        cellShape.fill();
        painted = true;
    }

    public void setColor(ColorWrapper color) {
        this.color = color;
        cellShape.setColor(color.unwrap());
    }

    public ColorWrapper getColor() {
        return color;
    }

    public boolean isPainted() {
        return painted;
    }

    public Position getPosition() {
        return position;
    }
}
