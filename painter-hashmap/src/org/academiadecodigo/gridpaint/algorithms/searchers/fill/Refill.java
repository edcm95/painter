package org.academiadecodigo.gridpaint.algorithms.searchers.fill;

import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.gridpaint.algorithms.Algorithm;
import org.academiadecodigo.gridpaint.algorithms.searchers.AbstractSearcher;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.LinkedList;

public class Refill extends AbstractSearcher implements Algorithm {

    public Refill(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    @Override
    public void run() {
        if (color == rootColor) {
            return;
        }

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

            processNeighbouringCells(current, tempCell, positionStack);
        }
        System.out.println("POINTER: Operation took " + (System.currentTimeMillis() - start) + " ms.");
    }

    @Override
    protected void checkCellAndAddToContainer(Cell tempCell, Position position, LinkedList<Position> list) {
        if (tempCell != null &&
                tempCell.isPainted() &&
                tempCell.getColor() == rootColor) {
            list.push(position);
        }
    }
}
