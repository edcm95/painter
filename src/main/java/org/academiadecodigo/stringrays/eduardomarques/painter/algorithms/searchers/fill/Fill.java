package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers.fill;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.Algorithm;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers.AbstractSearcher;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.LinkedList;

public class Fill extends AbstractSearcher implements Algorithm {

    public Fill(Grid grid, Color color, Position position) {
        super(grid, color, position);
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
            if (!tempCell.isPainted()) {
                tempCell.setColor(color);
                tempCell.paint();
            }

            processNeighbouringCells(current, positionStack);

        }
        System.out.println("POINTER: Operation took " + (System.currentTimeMillis() - start) + " ms.");

    }

    @Override
    protected void checkCellAndAddToContainer(Cell tempCell, Position position, LinkedList<Position> list) {
        if (tempCell != null && !tempCell.isPainted()) {
            list.push(position);
        }
    }
}
