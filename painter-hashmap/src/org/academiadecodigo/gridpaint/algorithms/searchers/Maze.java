package org.academiadecodigo.gridpaint.algorithms.searchers;

import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.gridpaint.algorithms.Algorithm;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.LinkedList;

public class Maze extends AbstractSearcher implements Algorithm {


    public Maze(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    public void run() {
        long start = System.currentTimeMillis();
        Position rootPosition = new Position(position.getX(), position.getY());

        LinkedList<Position> queue = new LinkedList<>();
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

            processNeighbouringCells(current, tempCell, queue);
        }
        System.out.println("POINTER: Operation took " + (System.currentTimeMillis() - start) + " ms.");
    }

    @Override
    protected void checkCellAndAddToContainer(Cell cell, Position position, LinkedList<Position> container) {
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
        tempCell.draw();
        return true;
    }

    private void emptyContainer(LinkedList<Position> list) {
        while (!list.isEmpty()) {
            Cell newCell = grid.getCellInPosition(list.poll());

            if (!newCell.isPainted()) {
                newCell.setColor(Color.GREEN);
                newCell.draw();
            }
        }
    }

    private void reversePath(Position position) {
        if (position.getOrigin() == null) {
            return;
        }

        Cell lastCell = grid.getCellInPosition(position.getOrigin());
        lastCell.setColor(color);
        lastCell.draw();

        reversePath(position.getOrigin());
    }


}
