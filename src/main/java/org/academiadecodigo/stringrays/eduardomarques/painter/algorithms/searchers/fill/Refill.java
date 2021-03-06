package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers.fill;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.Algorithm;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers.AbstractSearcher;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.Deque;
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
                tempCell.paint();
            }

            processNeighbouringCells(current, positionStack);
        }
        System.out.println("POINTER: Operation took " + (System.currentTimeMillis() - start) + " ms.");
    }

    @Override
    protected void checkCellAndAddToContainer(Cell tempCell, Position position, Deque<Position> list) {
        if (tempCell != null &&
                tempCell.isPainted() &&
                tempCell.getColor() == rootColor) {
            list.push(position);
        }
    }
}
