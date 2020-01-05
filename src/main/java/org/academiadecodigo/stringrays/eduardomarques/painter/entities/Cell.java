package org.academiadecodigo.stringrays.eduardomarques.painter.entities;

import org.academiadecodigo.stringrays.eduardomarques.painter.config.Constants;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Cell {

    private final Rectangle cellShape;
    private final Position position;
    private Color color;
    private boolean painted;

    public Cell(Position position) {
        this.position = position;
        this.cellShape = new Rectangle(position.getX(), position.getY(), Constants.CELL_SIZE, Constants.CELL_SIZE);
    }

    public void initCell() {
        cellShape.draw();
        painted = false;
    }

    public synchronized void paint() {
        cellShape.setColor(color);
        cellShape.fill();
        painted = true;
    }

    public synchronized void erase() {
        cellShape.setColor(Color.BLACK);
        cellShape.draw();
        painted = false;
    }

    public void writeCell(byte[] value) {
        if (value[0] == 0) {
            erase();
            return;
        }

        if (value[1] != 0) {
            switch (value[1]) {
                case 1:
                    setColor(Color.CYAN);
                    break;
                case 2:
                    setColor(Color.YELLOW);
                    break;
                case 3:
                    setColor(Color.PINK);
                    break;
                case 4:
                    setColor(Color.GREEN);
                    break;
                case 5:
                    setColor(Color.MAGENTA);
                    break;
                case 6:
                    setColor(Color.RED);
                    break;
                case 7:
                    setColor(Color.BLUE);
                    break;
                case 8:
                    setColor(Color.BLACK);
                    break;
                case 9:
                    setColor(Color.WHITE);
                    break;
            }
            paint();
        }
    }

    public void draw() {
        if(!painted) {
            return;
        }

        cellShape.setColor(color);
        cellShape.draw();
    }

    public synchronized void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean isPainted() {
        return painted;
    }

    public Position getPosition() {
        return position;
    }
}
