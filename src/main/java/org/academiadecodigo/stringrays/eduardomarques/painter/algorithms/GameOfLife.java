package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.Direction;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class GameOfLife implements Algorithm {

    private final Color color;
    private final Grid grid;
    //private Map<Cell, Cell[]> mapOfNeighbours;
    //private Map<Cell, Boolean> events;
    private Cell[][] neighbourArray;
    private byte[] events001;
    private Cell[] allCells;
    private long timeStamp;

    public GameOfLife(Grid grid, Color color) {
        this.color = color;
        this.grid = grid;
        //this.events = new HashMap<>();
        //this.mapOfNeighbours = new HashMap<>();
    }

    private void initialize() {
        int numberOfCells = grid.getMapOfCells().values().size();

        this.neighbourArray = new Cell[numberOfCells][];
        this.events001 = new byte[numberOfCells];
        this.allCells = grid.getMapOfCells().values().toArray(new Cell[numberOfCells]);

        mapNeighbours001();
    }

    /**
     * Main loop
     */
    @Override
    public void run() {
        initialize();

        int iterations = 0;

        boolean cellAlive = true;
        while (cellAlive) {
            timeStamp = System.currentTimeMillis();

            cellAlive = false;
            iterations++;

            //array way
            for (int i = 0; i < allCells.length; i++) {
                availCell001(allCells[i], i);

                if (allCells[i].isPainted()) {
                    cellAlive = true;
                }
            }

            for (int i = 0; i < allCells.length; i++) {
                executeCell001(allCells[i], i);
            }

            /*  HashMap way

            // read cell and map events
            for (Cell cell : grid.getMapOfCells().values()) {
                availCell(cell);

                if (cell.isPainted()) {
                    cellAlive = true;
                }
            }

            // execute iteration events
            for (Cell cell : events.keySet()) {
                executeCell(cell);
            }


            // clear events
            events.clear();

             */

            sleep();
        }

        System.out.println("Life ran for " + iterations + " generations.");
    }

    private void availCell001(Cell cell, int index) {
        if (cell.isPainted() && countAliveNeighbours001(index) == 2) {
            events001[index] = 1;
            return;
        }

        if (cell.isPainted() && countAliveNeighbours001(index) < 2) {
            events001[index] = 0;
            return;
        }

        if (cell.isPainted() && countAliveNeighbours001(index) > 3) {
            events001[index] = 0;
            return;
        }

        if (!cell.isPainted() && countAliveNeighbours001(index) == 3) {
            events001[index] = 127;
        }
    }

    private void executeCell001(Cell cell, int index) {
        if (events001[index] == 1) {
            return;
        }

        if (events001[index] == 127) {
            cell.setColor(color);
            cell.paint();
            return;
        }
        
        cell.erase();

    }

    /*
    /**
     * @param cell
     * For a given cell computes alive neighbours {@link #countAliveNeighbours(Cell)}
     * and registers the corresponding action on {@param mapOfActions}
     */
    /*
    private void availCell(Cell cell) {
        if (cell.isPainted() && countAliveNeighbours(cell) < 2) {
            events.put(cell, false);
            return;
        }

        if (cell.isPainted() && countAliveNeighbours(cell) > 3) {
            events.put(cell, false);
            return;
        }

        if (!cell.isPainted() && countAliveNeighbours(cell) == 3) {
            events.put(cell, true);
        }
    }
    */


    /*
    /**
     * @param cell
     * For all cells executes the corresponding action mapped by {@link #availCell(Cell)}
     */
    /*
    private void executeCell(Cell cell) {
        if (events.get(cell) == null) {
            return;
        }

        if (events.get(cell)) {
            cell.setColor(color);
            cell.paint();
            return;
        }

        cell.erase();
    }

     */

    private int countAliveNeighbours001(int index) {
        int count = 0;
        for (Cell neighbour : neighbourArray[index]) {
            if (neighbour == null) {
                continue;
            }

            if (neighbour.isPainted()) {
                count++;
            }
        }

        return count;
    }

    private void mapNeighbours001() {
        for (int i = 0; i < allCells.length; i++) {

            neighbourArray[i] = getNeighbours(allCells[i]);
        }
    }

    /*
    /**
     * @param cell in map
     * @return how many neighbouring cells are alive (int)
     */
    /*
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
    */

    /*
    /**
     * For each cell in the Game Of Life, invokes {@link #getNeighbours(Cell)}
     */
    /*
    private void mapNeighbours() {
        for (Cell cell : grid.getMapOfCells().values()) {
            mapOfNeighbours.put(cell, getNeighbours(cell));
        }
    }
    */

    /**
     * Calculates how much time the cycle processing took and computes
     * the desired waiting time
     */
    private void sleep() {
        long sleepTime;

        if ((sleepTime = 100 - (System.currentTimeMillis() - timeStamp)) < 0) {
            return;
        }

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param cell cell in map
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
