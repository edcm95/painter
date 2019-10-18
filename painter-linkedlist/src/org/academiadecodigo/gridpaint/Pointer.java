package org.academiadecodigo.gridpaint;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.util.LinkedList;

public class Pointer {

    private Position position;
    private double cellSize;
    private Rectangle pointerShape;
    private Grid grid;
    private Color color;
    private boolean pointerWritingStatus;

    public Pointer(Position position, Grid grid) {
        this.grid = grid;
        this.cellSize = grid.getCellSize();
        this.position = position;
        pointerShape = new Rectangle(position.getX(), position.getY(), cellSize, cellSize);
        color = Color.CYAN;
    }

    //---------------------------MOVEMENT METHOS

    public void moveLeft() {
        position.translate(-cellSize, 0);
        pointerShape.translate(-cellSize, 0);
        paintCell();
    }

    public void moveRight() {
        position.translate(cellSize, 0);
        pointerShape.translate(cellSize, 0);
        paintCell();

    }

    public void moveUp() {
        position.translate(0, -cellSize);
        pointerShape.translate(0, -cellSize);
        paintCell();
    }

    public void moveDown() {
        position.translate(0, cellSize);
        pointerShape.translate(0, cellSize);
        paintCell();

    }

    public void recenter() {
        Position lastPosition = new Position(position.getX(), position.getY());

        position.setPosition(grid.getBoard().getX(), grid.getBoard().getY());

        double deltaX = position.getX() - lastPosition.getX();
        double deltaY = position.getY() - lastPosition.getY();

        pointerShape.translate(deltaX, deltaY);
    }

    public void paintCell() {
        if (!pointerWritingStatus) {
            return;
        }

        Cell cell = grid.getCellInPosition(position);

        if (cell == null) {
            Painter.message.setText("No such cell.");
            Painter.message.draw();
            return;
        }

        if (cell.isPainted()) {
            cell.erase();
            return;
        }

        cell.setColor(color);
        cell.draw();
    }

    public void fill() {
        long start = System.nanoTime();

        Position rootPosition = new Position(position.getX(), position.getY());
        LinkedList<Position> positionStack = new LinkedList<>();
        positionStack.push(rootPosition);

        while (!positionStack.isEmpty()) {

            Position current = positionStack.pop();
            Cell tempCell = grid.getCellInPosition(current);

            //Process current cell
            if (tempCell != null && !tempCell.isPainted()) {
                tempCell.setColor(color);
                tempCell.draw();
            }

            //Get right cell, if valid put it into queue

            Position toRight = new Position(current);
            toRight.translate(cellSize, 0);
            tempCell = grid.getCellInPosition(toRight);

            if (tempCell != null && !tempCell.isPainted()) {
                positionStack.push(toRight);
            }

            //Get upper cell, if valid put it into queue

            Position toUp = new Position(current);
            toUp.translate(0, -cellSize);
            tempCell = grid.getCellInPosition(toUp);

            if (tempCell != null && !tempCell.isPainted()) {
                positionStack.push(toUp);
            }

            //Get lower cell, if valid put it into queu

            Position toDown = new Position(current);
            toDown.translate(0, cellSize);
            tempCell = grid.getCellInPosition(toDown);

            if (tempCell != null && !tempCell.isPainted()) {
                positionStack.push(toDown);
            }

            //get cell to the left and do the same

            Position toLeft = new Position(current);
            toLeft.translate(-cellSize, 0);
            tempCell = grid.getCellInPosition(toLeft);

            if (tempCell != null && !tempCell.isPainted()) {
                positionStack.push(toLeft);
            }
        }
        System.out.println("POINTER: Operation took " + (System.nanoTime() - start) / 1000000 + " ms.");
    }

    public void setPointerWritingStatus(boolean value) {
        pointerWritingStatus = value;
    }

    public void setColor(Color color) {
        this.color = color;
        draw();
    }

    public void draw() {
        pointerShape.setColor(color);
        pointerShape.fill();
    }
}
