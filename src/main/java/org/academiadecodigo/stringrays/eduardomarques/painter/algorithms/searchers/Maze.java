package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.Algorithm;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.*;

public class Maze extends AbstractSearcher implements Algorithm {


    public Maze(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    public void run() {
        long start = System.currentTimeMillis();
        Position rootPosition = new Position(position.getX(), position.getY());

        ArrayDeque<Position> queue = new ArrayDeque<>();
        queue.offer(rootPosition);

        while (!queue.isEmpty()) {
            //Get a cell from the container
            Position current = queue.poll();
            Cell tempCell = grid.getCellInPosition(current);

            //If cell is the seeked cell, empty the container and reverse the Path
            if (tempCell.isPainted() && tempCell.getColor() == color) {
                reversePath(current);
                emptyContainer(queue);
                break;
            }

            if (!isCellCleanIfSoProcess(tempCell)) {
                continue;
            }

            processNeighbouringCells(current, queue);
        }
        System.out.println("POINTER: Operation took " + (System.currentTimeMillis() - start) + " ms.");
    }

    @Override
    protected void checkCellAndAddToContainer(Cell cell, Position position, Deque<Position> container) {
        if (cell == null) {
            return;
        }

        if (cell.isPainted() && cell.getColor() == color) {
            container.offer(position);
        }

        if (!cell.isPainted()) {
            container.offer(position);
        }
    }


    private boolean isCellCleanIfSoProcess(Cell tempCell) {
        if (tempCell.isPainted()) {
            return false;
        }

        tempCell.setColor(Color.WHITE);
        tempCell.paint();
        return true;
    }

    private void emptyContainer(Deque<Position> list) {
        while (!list.isEmpty()) {
            Cell newCell = grid.getCellInPosition(list.poll());

            if (!newCell.isPainted()) {
                newCell.setColor(Color.GREEN);
                newCell.paint();
            }
        }
    }

    private void reversePath(Position position) {
        if (position.getOrigin() == null) {
            return;
        }

        Cell lastCell = grid.getCellInPosition(position.getOrigin());
        lastCell.setColor(color);
        lastCell.paint();

        reversePath(position.getOrigin());
    }
}
