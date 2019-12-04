package org.academiadecodigo.gridpaint.algorithms;

import org.academiadecodigo.gridpaint.auxiliaryclasses.Direction;
import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class GameOfLife implements Algorithm {

    private final Color color;
    private final Grid grid;
    private Map<Cell, Cell[]> mapOfNeighbours;
    private Map<Cell, Boolean> events;

    public GameOfLife(Grid grid, Color color) {
        this.color = color;
        this.grid = grid;
        this.events = new HashMap<>();
        this.mapOfNeighbours = new HashMap<>();
    }

    @Override
    public void run() {
        // map each cell's neighbouring cells
        mapNeighbours();

        int iterations = 0;

        boolean cellAlive = true;
        while (cellAlive) {
            long timeStamp = System.currentTimeMillis();

            cellAlive = false;
            iterations++;

            // read cell and map events
            for (Cell cell : grid.getMapOfCells().values()) {

                availCell(cell, events);

                if (cell.isPainted()) {
                    cellAlive = true;
                }
            }

            // execute iteration events
            for (Cell cell : events.keySet()) {
                executeCell(cell, events);
            }

            // clear events
            events.clear();

            try {
                Thread.sleep(250 - (System.currentTimeMillis() - timeStamp));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            mapOfactions.put(cell, true);
        }
    }

    private void executeCell(Cell cell, Map<Cell, Boolean> mapOfActions) {
        if (mapOfActions.get(cell) == null) {
            return;
        }

        if (mapOfActions.get(cell)) {
            System.out.println("Cell came alive.");
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

            if (neighbour.isPainted()) {
                count++;
            }
        }

        return count;
    }


    private void mapNeighbours() {
        for (Cell cell : grid.getMapOfCells().values()) {
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
        right.translate(Direction.RIGHT);
        neighbours[1] = grid.getCellInPosition(right);

        //get upper
        Position up = new Position(cell.getPosition());
        up.translate(Direction.UP);
        neighbours[2] = grid.getCellInPosition(up);

        //get lower
        Position down = new Position(cell.getPosition());
        down.translate(Direction.DOWN);
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
        lowerRight.translate(Direction.DOWN);
        lowerRight.translate(Direction.RIGHT);
        neighbours[7] = grid.getCellInPosition(lowerRight);

        // count alive ones
        return neighbours;
    }


}
