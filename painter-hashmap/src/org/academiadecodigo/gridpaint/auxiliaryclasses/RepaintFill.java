package org.academiadecodigo.gridpaint.auxiliaryclasses;

import org.academiadecodigo.gridpaint.Grid;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.LinkedList;

public class RepaintFill implements Runnable {

    private Grid grid;
    private Color color;
    private Color rootColor;
    private Position position;
    private double cellSize;

    public RepaintFill(Grid grid, Color color, Color rootColor, Position position, double cellSize) {
        this.grid = grid;
        this.color = color;
        this.rootColor = rootColor;
        this.position = position;
        this.cellSize = cellSize;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        Position rootPosition = new Position(position.getX(), position.getY());

        LinkedList<Position> positionStack = new LinkedList<>();
        positionStack.push(rootPosition);

        while (!positionStack.isEmpty()) {
            Position current = positionStack.pop();
            Cell tempCell = grid.getCellInPosition(current);

            //Process current cell
            if (tempCell.isPainted() && tempCell.getColor() == rootColor) {
                tempCell.setColor(color);
                tempCell.draw();
            }

            Position toRight = new Position(current);
            toRight.translate(cellSize, 0);
            tempCell = grid.getCellInPosition(toRight);
            checkCellAndAddToContainer(tempCell, toRight, positionStack);

            Position toUp = new Position(current);
            toUp.translate(0, -cellSize);
            tempCell = grid.getCellInPosition(toUp);
            checkCellAndAddToContainer(tempCell, toUp, positionStack);

            Position toDown = new Position(current);
            toDown.translate(0, cellSize);
            tempCell = grid.getCellInPosition(toDown);
            checkCellAndAddToContainer(tempCell, toDown, positionStack);

            Position toLeft = new Position(current);
            toLeft.translate(-cellSize, 0);
            tempCell = grid.getCellInPosition(toLeft);
            checkCellAndAddToContainer(tempCell, toLeft, positionStack);
        }
        System.out.println("POINTER: Operation took " + (System.currentTimeMillis() - start) + " ms.");
    }

    private void checkCellAndAddToContainer(Cell tempCell, Position position, LinkedList<Position> list) {
        if (tempCell != null &&
                tempCell.isPainted() &&
                tempCell.getColor() == rootColor) {
            list.push(position);
        }
    }
}
