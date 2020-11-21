package org.academiadecodigo.stringrays.eduardomarques.painter.entities;

import org.academiadecodigo.stringrays.eduardomarques.painter.config.Config;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.Serializable;

/**
 * Basic unit, define a small square
 * The grid is made up of many cells
 */
public class Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Rectangle cellShape;
    private final Position position;
    private boolean painted;

    /**
     * Initializes one cell based on one position
     *
     * @param position from wich to equal values on new object
     */
    public Cell(Position position) {
        this.position = position;
        this.cellShape = new Rectangle(
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
        cellShape.setColor(Color.BLACK);
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
        //cellShape.setColor(getColor());
        cellShape.fill();
        painted = true;
    }

    public void setColor(Color color) {
        this.cellShape.setColor(color);
    }

    public Color getColor() {
        return cellShape.getColor();
    }

    public boolean isPainted() {
        return painted;
    }

    public Position getPosition() {
        return position;
    }
}
