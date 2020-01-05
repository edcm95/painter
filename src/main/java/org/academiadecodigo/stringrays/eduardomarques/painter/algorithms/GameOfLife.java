package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.Direction;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class GameOfLife implements Algorithm {

    private final Color color;
    private final Grid grid;
    private Map<Cell, Cell[]> mapOfNeighbours;
    private Map<Cell, Boolean> events;
    private long timeStamp;

    public GameOfLife(Grid grid, Color color) {
        this.color = color;
        this.grid = grid;
        this.events = new HashMap<>();
        this.mapOfNeighbours = new HashMap<>();
    }

    /**
     * Main loop
     */
    @Override
    public void run() {
        // map each cell's neighbouring cells
        mapNeighbours();

        int iterations = 0;

        boolean cellAlive = true;
        while (cellAlive) {
            timeStamp = System.currentTimeMillis();

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

            sleep();
        }

        System.out.println("Life ran for " + iterations + " generations.");
    }

    /**
     * @param cell
     * @param mapOfActions
     * For a given cell computes alive neighbours {@link #countAliveNeighbours(Cell)}
     * and registers the corresponding action on {@param mapOfActions}
     */
    private void availCell(Cell cell, Map<Cell, Boolean> mapOfActions) {
        if (cell.isPainted() && countAliveNeighbours(cell) < 2) {
            mapOfActions.put(cell, false);
            return;
        }

        if (cell.isPainted() && countAliveNeighbours(cell) > 3) {
            mapOfActions.put(cell, false);
            return;
        }

        if (!cell.isPainted() && countAliveNeighbours(cell) == 3) {
            mapOfActions.put(cell, true);
        }
    }

    /**
     * @param cell
     * @param mapOfActions
     * For all cells executes the corresponding action mapped by {@link #availCell(Cell, Map)}
     */
    private void executeCell(Cell cell, Map<Cell, Boolean> mapOfActions) {
        if (mapOfActions.get(cell) == null) {
            return;
        }

        if (mapOfActions.get(cell)) {
            cell.setColor(color);
            cell.paint();
            return;
        }

        cell.erase();
    }

    /**
     * @param cell
     * @return how many neighbouring cells are alive (int)
     */
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


    /**
     * For each cell in the Game Of Life, invokes {@link #getNeighbours(Cell)}
     */
    private void mapNeighbours() {
        for (Cell cell : grid.getMapOfCells().values()) {
            mapOfNeighbours.put(cell, getNeighbours(cell));
        }
    }

    /**
     * Calculates how much time the cycle processing took and computes
     * the desired waiting time
     */
    private void sleep() {
        long sleepTime = 100 - (System.currentTimeMillis() - timeStamp);

        if(sleepTime < 0){
            return;
        }

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param cell
     * @return an array with all the cell's neighbours
     */
    private Cell[] getNeighbours(Cell cell) {
        Cell[] neighbours = new Cell[8];

        Position left = new Position(cell.getPosition());
        left.translate(Direction.LEFT);
        neighbours[0] = grid.getCellInPosition(left);

        Position right = new Position(cell.getPosition());
        right.translate(Direction.RIGHT);
        neighbours[1] = grid.getCellInPosition(right);

        Position up = new Position(cell.getPosition());
        up.translate(Direction.UP);
        neighbours[2] = grid.getCellInPosition(up);

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
