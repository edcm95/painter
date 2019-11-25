package org.academiadecodigo.gridpaint.auxiliaryclasses;

import org.academiadecodigo.gridpaint.auxiliaryclasses.config.Constants;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Cell {

    private Color color;
    private Rectangle cellShape;
    private boolean painted;

    public Cell(Position position) {
        cellShape = new Rectangle(position.getX(), position.getY(), Constants.CELL_SIZE, Constants.CELL_SIZE);
    }

    public void initCell() {
        cellShape.draw();
        painted = false;
    }

    public synchronized void draw() {
        cellShape.setColor(color);
        cellShape.fill();
        painted = true;
    }

    public synchronized void erase() {
        cellShape.setColor(Color.BLACK);
        cellShape.draw();
        painted = false;
    }

    void writeCell(byte[] value) {
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
            draw();
        }
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
}
