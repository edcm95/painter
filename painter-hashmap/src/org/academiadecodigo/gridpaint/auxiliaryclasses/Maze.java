package org.academiadecodigo.gridpaint.auxiliaryclasses;

import org.academiadecodigo.gridpaint.Cell;
import org.academiadecodigo.gridpaint.Grid;
import org.academiadecodigo.gridpaint.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;
import java.util.LinkedList;

public class Maze implements Runnable {

    private Color color;
    private Grid grid;
    private Position root;
    private double cellSize;

    public Maze(Color color, Grid grid, Position root, double cellSize){
        this.grid = grid;
        this.root = root;
        this.color = color;
        this.cellSize = cellSize;
    }

    public void run() {
        long start = System.nanoTime();
        Position rootPosition = new Position(root.getX(), root.getY());

        LinkedList<Position> queue = new LinkedList<>();
        queue.push(rootPosition);

        while (!queue.isEmpty()) {

            if (queue.size() > 8000000) {
                System.out.println("POINTER: BFS Queue size exceeded 8 Million objects, operation aborted.");
                break;
            }

            //Get a cell from the container
            Position current = queue.poll();
            Cell tempCell = grid.getCellInPosition(current);

            //--------------------------------------------------------PROCESS CURRENT

            //If cell is the seeked cell, empty the container and reverse the Path
            if (tempCell != null && tempCell.isPainted() && tempCell.getColor() == color) {
                emptyContainer(queue);
                reversePath(current);
                break;
            }

            processCleanCell(tempCell);

            //---------------------------------------GET CELLS

            //Get lower cell
            Position toDown = new Position(current);
            toDown.translate(0, cellSize);
            tempCell = grid.getCellInPosition(toDown);

            checkProcessedCellAndAddToContainer(queue, toDown, tempCell);

            //Get upper cell
            Position toUp = new Position(current);
            toUp.translate(0, -cellSize);
            tempCell = grid.getCellInPosition(toUp);

            checkProcessedCellAndAddToContainer(queue, toUp, tempCell);

            //Get right cell
            Position toRight = new Position(current);
            toRight.translate(cellSize, 0);
            tempCell = grid.getCellInPosition(toRight);

            checkProcessedCellAndAddToContainer(queue, toRight, tempCell);

            //Get left cell
            Position toLeft = new Position(current);
            toLeft.translate(-cellSize, 0);
            tempCell = grid.getCellInPosition(toLeft);

            checkProcessedCellAndAddToContainer(queue, toLeft, tempCell);
        }
        System.out.println("POINTER: Operation took " + (System.nanoTime() - start) / 1000000 + " ms.");
    }

    private void checkProcessedCellAndAddToContainer(LinkedList<Position> container, Position position, Cell cell) {
        //If the cell is not painter or matches the seeked cell, add it to container
        if (cell != null && cell.isPainted() && cell.getColor() == color) {
            container.offer(position);
        }

        if (cell != null && !cell.isPainted()) {
            container.offer(position);
        }
    }

    private void processCleanCell(Cell tempCell) {
        //If the cell is clear paint it white so it doesn't reenter the queue
        if (tempCell != null && !tempCell.isPainted()) {
            tempCell.setColor(Color.WHITE);
            tempCell.draw();
        }
    }

    private void emptyContainer(LinkedList<Position> list) {

        while (!list.isEmpty()) {
            Position current = list.poll();
            Cell newCell = grid.getCellInPosition(current);
            newCell.setColor(Color.GREEN);
            newCell.draw();
        }
    }

    private void reversePath(Position position) {

        if (position.getOrigin() == null) {
            return;
        }

        Position origin = position.getOrigin();
        Cell lastCell = grid.getCellInPosition(origin);

        lastCell.setColor(color);
        lastCell.draw();

        reversePath(origin);
    }
}
