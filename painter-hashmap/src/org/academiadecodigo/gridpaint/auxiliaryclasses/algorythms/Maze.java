package org.academiadecodigo.gridpaint.auxiliaryclasses.algorythms;

import org.academiadecodigo.gridpaint.Grid;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Cell;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.LinkedList;

public class Maze implements Runnable {

    private Color color;
    private Grid grid;
    private Position root;
    private double cellSize;

    public Maze(Color color, Grid grid, Position root, double cellSize) {
        this.grid = grid;
        this.root = root;
        this.color = color;
        this.cellSize = cellSize;
    }

    public void run() {
        long start = System.currentTimeMillis();
        Position rootPosition = new Position(root.getX(), root.getY());

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
                System.out.println(queue.size());
                emptyContainer(queue);
                break;
            }

            processCleanCell(tempCell);

            Position lower = new Position(current);
            lower.translate(0, cellSize);
            tempCell = grid.getCellInPosition(lower);
            checkProcessedCellAndAddToContainer(queue, lower, tempCell);

            Position upper = new Position(current);
            upper.translate(0, -cellSize);
            tempCell = grid.getCellInPosition(upper);
            checkProcessedCellAndAddToContainer(queue, upper, tempCell);

            Position righter = new Position(current);
            righter.translate(cellSize, 0);
            tempCell = grid.getCellInPosition(righter);
            checkProcessedCellAndAddToContainer(queue, righter, tempCell);

            Position lefter = new Position(current);
            lefter.translate(-cellSize, 0);
            tempCell = grid.getCellInPosition(lefter);
            checkProcessedCellAndAddToContainer(queue, lefter, tempCell);
        }
        System.out.println("POINTER: Operation took " + (System.currentTimeMillis() - start) + " ms.");
    }

    private void checkProcessedCellAndAddToContainer(LinkedList<Position> container, Position position, Cell cell) {
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
