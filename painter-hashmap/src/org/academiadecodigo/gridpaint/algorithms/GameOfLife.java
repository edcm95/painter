package org.academiadecodigo.gridpaint.algorithms;

import org.academiadecodigo.gridpaint.algorithms.searchers.AbstractSearcher;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Direction;
import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// TODO: 28/11/2019 Work on this
public class GameOfLife extends AbstractSearcher implements Algorithm {

    private LinkedList<Cell> cells;
    private Map<Cell, Cell[]> mapOfNeighbours;

    public GameOfLife(Grid grid, Color color, Position position) {
        super(grid, color, position);
        this.cells = new LinkedList<>();
        this.mapOfNeighbours = new HashMap<>();
    }

    @Override
    public void run() {
        // get cells in area
        populateCells();

        // map each cell's neighbouring cells
        mapNeighbours();

        // verify neighbours
        boolean cellAlive = false;

        if (!cells.isEmpty()) {
            cellAlive = true;
        }

        while (cellAlive) {
            cellAlive = false;

            System.out.println("entering loop");

            for (Cell cell : cells) {
                System.out.println(countAliveNeighbours(cell));

                if (cell.isPainted()) {
                    cellAlive = true;
                }

                if (cell.isPainted() && countAliveNeighbours(cell) < 2) {
                    cell.erase();
                    continue;
                }

                if (cell.isPainted() && countAliveNeighbours(cell) > 3) {
                    cell.erase();
                    continue;
                }

                if (!cell.isPainted() && countAliveNeighbours(cell) == 3) {
                    cellAlive = true;
                    cell.setColor(color);
                    cell.draw();
                }
            }

        }

        System.out.println("Ended.");
    }

    private int countAliveNeighbours(Cell cell) {
        Cell[] neighbours = mapOfNeighbours.get(cell);
        int count = 0;

        for (Cell neighbour : neighbours) {
            if (neighbour.isPainted() && neighbour.getColor() != Color.BLACK) {
                count = count + 1;
            }
        }

        return count;
    }


    private void mapNeighbours() {

        for (Cell cell : cells) {
            mapOfNeighbours.put(cell, getNeighbours(cell));
        }
    }

    /**
     * @param cell
     * @return the number of neighbours currently alive;
     */
    private Cell[] getNeighbours(Cell cell) {
        Cell[] neighbours = new Cell[8];

        //get left neighbour
        Position left = new Position(cell.getPosition());
        left.translate(Direction.LEFT);
        neighbours[0] = (grid.getCellInPosition(left));

        //ger right neighbour
        Position right = new Position(cell.getPosition());
        left.translate(Direction.RIGHT);
        neighbours[1] = (grid.getCellInPosition(right));

        //get upper
        Position up = new Position(cell.getPosition());
        left.translate(Direction.UP);
        neighbours[2] = (grid.getCellInPosition(up));

        //get lower
        Position down = new Position(cell.getPosition());
        left.translate(Direction.DOWN);
        neighbours[3] = (grid.getCellInPosition(down));


        // TODO: 29/11/2019 Finish the next 4 diagonal cells





        // count alive ones
        return neighbours;

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

        cleanSelection();

    }

    private void cleanSelection() {
        for (Cell cell : cells) {
            if (cell.isPainted() && cell.getColor() == color && cell.isPainted() && cell.getColor() == Color.BLACK) {
                continue;
            }

            cell.erase();
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

        cells.add(tempCell);
        queue.offer(position);
    }
}
