package org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.searchers;

import org.academiadecodigo.gridpaint.Grid;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Cell;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Position;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.Algorithm;
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
            if (queue.size() > 8000000) {
                System.out.println("POINTER: Queue size exceeded 8 Million objects, operation aborted.");
                emptyContainer(queue);
                break;
            }

            //Get a cell from the container
            Position current = queue.poll();
            Cell tempCell = grid.getCellInPosition(current);

            //If cell is the seeked cell, empty the container and reverse the Path
            if (tempCell.isPainted() && tempCell.getColor() == color) {
                reversePath(current);
                emptyContainer(queue);
                break;
            }

            processCleanCell(tempCell);

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


    private void processCleanCell(Cell tempCell) {
        if (tempCell.isPainted()) {
            return;
        }

        tempCell.setColor(Color.WHITE);
        tempCell.draw();
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
