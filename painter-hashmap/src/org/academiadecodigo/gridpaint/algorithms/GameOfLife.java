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
        int iterations = 0;
        Map<Cell, Boolean> mapOfActions = new HashMap<>();

        while (cellAlive) {
            cellAlive = false;
            iterations++;

            // read cell and map events
            for (Cell cell : cells) {

                availCell(cell, mapOfActions);


                if (cell.isPainted()) {
                    cellAlive = true;
                }
            }

            // execute events in atomic manner
            for (Cell cell : mapOfActions.keySet()) {
                executeCell(cell, mapOfActions);
            }

            // clear actions
            mapOfActions.clear();

        }

        System.out.println(iterations);

    }

    private void availCell(Cell cell, Map<Cell, Boolean> mapOfactions) {
        if (cell.isPainted() && countAliveNeighbours(cell) < 2) {
            mapOfactions.put(cell, false);
            return;
        }

        if (cell.isPainted() && countAliveNeighbours(cell) > 3) {
            mapOfactions.put(cell, false);
            return;
        }

        if (!cell.isPainted() && countAliveNeighbours(cell) == 3) {
            mapOfactions.put(cell, null);
        }
        mapOfactions.put(cell, null);
    }

    private void executeCell(Cell cell, Map<Cell, Boolean> mapOfActions) {
        if (mapOfActions.get(cell) == null) {
            return;
        }

        if (mapOfActions.get(cell)) {
            cell.setColor(color);
            cell.draw();
            return;
        }

        cell.erase();
    }

    private int countAliveNeighbours(Cell cell) {
        Cell[] neighbours = mapOfNeighbours.get(cell);

        int count = 0;

        for (Cell neighbour : neighbours) {
            if (neighbour == null) {
                continue;
            }

            if (neighbour.isPainted() && neighbour.getColor() != Color.BLACK) {
                count++;
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
        neighbours[0] = grid.getCellInPosition(left);

        //ger right neighbour
        Position right = new Position(cell.getPosition());
        left.translate(Direction.RIGHT);
        neighbours[1] = grid.getCellInPosition(right);

        //get upper
        Position up = new Position(cell.getPosition());
        left.translate(Direction.UP);
        neighbours[2] = (grid.getCellInPosition(up));

        //get lower
        Position down = new Position(cell.getPosition());
        left.translate(Direction.DOWN);
        neighbours[3] = grid.getCellInPosition(down);


        Position upperLeft = new Position(cell.getPosition());
        upperLeft.translate(Direction.UP);
        upperLeft.translate(Direction.LEFT);
        neighbours[4] = grid.getCellInPosition(upperLeft);

        Position upperRight = new Position(cell.getPosition());
        upperRight.translate(Direction.UP);
        upperRight.translate(Direction.RIGHT);
        neighbours[5] = grid.getCellInPosition(upperRight);

        Position lowerLeft = new Position(cell.getPosition());
        lowerLeft.translate(Direction.DOWN);
        lowerLeft.translate(Direction.LEFT);
        neighbours[6] = grid.getCellInPosition(lowerLeft);

        Position lowerRight = new Position(cell.getPosition());
        lowerRight.translate(Direction.UP);
        lowerRight.translate(Direction.RIGHT);
        neighbours[7] = grid.getCellInPosition(lowerRight);

        // count alive ones
        return neighbours;

    }

    private void populateCells() {
        LinkedList<Position> queue = new LinkedList<>();

        queue.add(position);
        System.out.println("Populating Game Of Life Grid");

        while (!queue.isEmpty()) {

            //get cell
            Position current = queue.poll();
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
        // erase root (was polled from queue and never added again)
        grid.getCellInPosition(position).erase();

        for (Cell cell : cells) {
            if (cell.isPainted() && cell.getColor() == color) {
                continue;
            }

            cell.erase();
        }
    }

    private boolean isCellValidToProcess(Cell cell) {
        if (cell.isPainted() && cell.getColor() == Color.GREEN) {
            return false;
        }

        if (cell.isPainted() && cell.getColor() == color) {
            return true;
        }

        cell.setColor(Color.GREEN);
        cell.draw();
        return true;
    }

    @Override
    protected void checkCellAndAddToContainer(Cell nextCell, Position position, LinkedList<Position> queue) {
        if (nextCell == null) {
            return;
        }

        if (nextCell.isPainted() && (nextCell.getColor() == Color.BLACK && nextCell.getColor() == Color.GREEN)) {
            return;
        }

        if (nextCell.isPainted() && nextCell.getColor() == color) {
            if (!cells.contains(nextCell)) {
                cells.add(nextCell);
            }
        }

        if (!nextCell.isPainted()) {
            cells.add(nextCell);
            queue.offer(position);
        }
    }
}
