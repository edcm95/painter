package org.academiadecodigo.gridpaint.algorithms;

import org.academiadecodigo.gridpaint.algorithms.searchers.AbstractSearcher;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Direction;
import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.LinkedList;
import java.util.List;

// TODO: 28/11/2019 Work on this
public class GameOfLife extends AbstractSearcher implements Algorithm {

    LinkedList<Position> cellPositions;

    public GameOfLife(Grid grid, Color color, Position position) {
        super(grid, color, position);
        this.cellPositions = new LinkedList<>();
    }

    @Override
    public void run() {
        populateCells();

        // verify neighbours
        boolean cellAlive = false;

        if (!cellPositions.isEmpty()) {
            cellAlive = true;
        }

        while (cellAlive) {
            cellAlive = false;

            for (Position position : cellPositions) {
                Cell cell = grid.getCellInPosition(position);

                if (cell.isPainted()) {
                    cellAlive = true;
                }

                if (cell.isPainted() && getNeighbours(cell, position) < 2) {
                    cell.erase();
                    continue;
                }

                if (cell.isPainted() && getNeighbours(cell, position) > 3) {
                    cell.erase();
                    continue;
                }

                if (!cell.isPainted() && getNeighbours(cell, position) == 3) {
                    cell.setColor(color);
                    cell.draw();
                }
            }
        }
    }

    /**
     * @param cell
     * @return the number of neighbours currently alive;
     */
    private int getNeighbours(Cell cell, Position position) {

        List<Cell> neighbours = new LinkedList<>();

        //get left neighbour
        Position left = new Position(position);
        left.translate(Direction.LEFT);
        neighbours.add(grid.getCellInPosition(left));

        //ger right neighbour
        Position right = new Position(position);
        left.translate(Direction.RIGHT);
        neighbours.add(grid.getCellInPosition(right));

        //get upper
        Position up = new Position(position);
        left.translate(Direction.UP);
        neighbours.add(grid.getCellInPosition(up));

        //get lower
        Position down = new Position(position);
        left.translate(Direction.DOWN);
        neighbours.add(grid.getCellInPosition(down));


        // count alive ones
        int count = 0;
        for (Cell neighbour : neighbours) {
            if (neighbour.isPainted()) {
                count = count + 1;
            }
        }


        return count;
    }

    private void populateCells() {
        LinkedList<Position> queue = new LinkedList<>();

        queue.add(position);

        while (!queue.isEmpty()) {

            //get cell
            Position current = queue.pop();
            Cell tempCell = grid.getCellInPosition(current);

            //is cell valid for process?
            if (!isCellValidToProcess(tempCell)) {
                continue;
            }

            //get neighbours a process
            processNeighbouringCells(current, tempCell, queue);
        }

        // clean area
        for(Position position : cellPositions){
            Cell cell = grid.getCellInPosition(position);

            if(cell.isPainted() && cell.getColor() != color && cell.isPainted() && cell.getColor() != Color.BLACK){
                cell.erase();
            }
        }

    }

    private boolean isCellValidToProcess(Cell cell) {
        if (cell.isPainted()) {
            return false;
        }

        cell.setColor(Color.WHITE);
        cell.draw();
        return true;
    }

    @Override
    protected void checkCellAndAddToContainer(Cell tempCell, Position position, LinkedList<Position> queue) {
        if (tempCell == null) {
            return;
        }

        if (tempCell.isPainted() && tempCell.getColor() != color) {
            return;
        }

        queue.offer(position);
        cellPositions.add(position);
    }
}
