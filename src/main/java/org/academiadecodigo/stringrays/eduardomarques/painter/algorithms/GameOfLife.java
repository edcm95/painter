package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.Direction;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameOfLife implements Algorithm {

    private final Color color;
    private final Grid grid;

    private Map<Cell, Cell[]> mapOfNeighbours;
    private Map<Cell, Boolean> events;

    private long timeStamp;
    private boolean cellAlive;

    public GameOfLife(Grid grid, Color color) {
        this.color = color;
        this.grid = grid;
        this.cellAlive = true;
    }

    private void initialize() {
        this.events = new ConcurrentHashMap<>();
        this.mapOfNeighbours = new HashMap<>();
        mapNeighbours();
    }

    /**
     * Main loop
     */
    @Override
    public void run() {
        initialize();

        int iterations = 0;

        while (cellAlive) {
            timeStamp = System.currentTimeMillis();

            cellAlive = false;
            iterations++;

            // evaluate each cell
            grid.getMapOfCells().values().parallelStream().forEach(this::availCell);

            // execute iteration events
            events.keySet().parallelStream().forEach(this::executeCell);

            sleep();
        }

        System.out.println("Life ran for " + iterations + " generations.");
    }


    /**
     * @param cell For a given cell computes alive neighbours {@link #countAliveNeighbours(Cell)}
     *             and registers the corresponding action on {@param mapOfActions}
     */
    private void availCell(Cell cell) {
        byte aliveNeighbours = countAliveNeighbours(cell);

        if (aliveNeighbours == 2) {
            events.remove(cell);
            return;
        }

        if (!cell.isPainted()) {
            if (aliveNeighbours == 3) {
                events.put(cell, true);
                cellAlive = true;
            }
            return;
        }

        if (aliveNeighbours < 2) {
            events.put(cell, false);
            return;
        }

        if (aliveNeighbours > 3) {
            events.put(cell, false);
        }
    }


    /**
     * @param cell For all cells executes the corresponding action mapped by {@link #availCell(Cell)}
     */
    private void executeCell(Cell cell) {
        if (!events.containsKey(cell)) {
            return;
        }

        if (events.get(cell)) {
            cell.setColor(color);
            cell.paint();
            return;
        }

        cell.initCell();
    }


    /**
     * @param cell in map
     * @return how many neighbouring cells are alive (int)
     */
    private byte countAliveNeighbours(Cell cell) {
        Cell[] neighbours = mapOfNeighbours.get(cell);

        byte count = 0;
        for (Cell neighbour : neighbours) {
            if (neighbour != null && neighbour.isPainted()) {
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
        long sleepTime;

        if ((sleepTime = 64 - (System.currentTimeMillis() - timeStamp)) < 0) {
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
