package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.Direction;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;

import java.util.HashMap;
import java.util.Map;

public class Covid19 implements Algorithm {

    private final Color RECOVERED = Color.GREEN;
    private final Color DEAD = Color.BLACK;
    private Grid grid;
    private Color color;
    private Map<Cell, Cell[]> mapOfNeighbours;
    private Map<Cell, Integer> cellsInfectedTime;
    private Map<Cell, Integer> action; // 9 == infect, 1 == recover; 2 = die

    private boolean cellInfected;

    // rates
    private final int MIN_RECOVERY_TIME = 15;
    private final int MIN_DEATH_TIME = 15;
    private final double RECOVERY_PROB = 0.05;
    private final double DEATH_PROB = 0.0033;
    private final double INFECTION_RATE = 0.11; // for each infected neighbour a cell is 20% chance to get infected

    public Covid19(Grid grid, Color color) {
        this.grid = grid;
        this.color = color;
    }

    private void initialize() {
        this.cellsInfectedTime = new HashMap<>();
        this.mapOfNeighbours = new HashMap<>();
        this.action = new HashMap<>();
        mapNeighbours();
    }

    @Override
    public void run() {
        initialize();

        while (cellInfected) {
            cellInfected = false;

            // evaluate each cell
            grid.getMapOfCells().values().forEach(this::evaluateCell);

            sleep(28);

            // execute iteration events
            action.keySet().forEach(this::executeCell);
            action.clear();
        }

        int dead = 0;
        int recovered = 0;
        for (Cell cell : grid.getMapOfCells().values()) {
            if( cell.getColor().equals(DEAD)) {
                dead++;
                continue;
            }

            if(cell.getColor().equals(RECOVERED)) {
                recovered++;
            }
        }

        System.out.println("COVID-19 was erradicated: \n" +
                "Recovered: " + recovered + "\n" +
                "Dead: "+ dead);
    }

    private void evaluateCell(Cell cell) {
        // if cell is Dead or recovered or something else, do nothing
        if(cell.isPainted() && cell.getColor() == RECOVERED) {
            return;
        }

        if(cell.isPainted() && cell.getColor() == DEAD) {
            return;
        }

        if(cell.isPainted() && cell.getColor() != color) {
            // cell is a wall
            return;
        }

        if(cell.isPainted() && cell.getColor() == color) {
            cellInfected = true;

            // compute event for infected cells
            int time = cellsInfectedTime.get(cell);
            cellsInfectedTime.replace(cell, ++time);


            if(time > MIN_DEATH_TIME && verifyLikelihood(DEATH_PROB)) {
                action.put(cell, 2);
                return;
            }

            if(time > MIN_RECOVERY_TIME && verifyLikelihood(RECOVERY_PROB)) {
                action.put(cell, 1);
            }
            return;
        }



        // compute infection
        byte infectedNeighbours = countInfectedNeighbours(cell);
        if(!cell.isPainted() && infectedNeighbours != 0 && verifyLikelihood(infectedNeighbours * INFECTION_RATE)) {
            action.put(cell, 0);
        }

    }

    private void executeCell(Cell cell) {

        switch (action.get(cell)) {
            case 0:
                cell.setColor(color);
                cell.paint();
                cellsInfectedTime.put(cell, 1);
                cellInfected = true;
                break;
            case 1:
                cell.setColor(RECOVERED);
                cell.paint();
                cellsInfectedTime.remove(cell);
                break;
            case 2:
                cell.setColor(DEAD);
                cell.paint();
                cellsInfectedTime.remove(cell);
        }
    }

    /**
     * @param cell in map
     * @return how many neighbouring cells are alive (int)
     */
    private byte countInfectedNeighbours(Cell cell) {
        Cell[] neighbours = mapOfNeighbours.get(cell);

        byte count = 0;
        for (Cell neighbour : neighbours) {
            if (neighbour != null && neighbour.isPainted() && neighbour.getColor() == color) {
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

            if(cell.isPainted() && cell.getColor() == color) {
                cellsInfectedTime.put(cell, 1);
                cellInfected = true;
            }
            mapOfNeighbours.put(cell, getNeighbours(cell));
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean verifyLikelihood(double probability) {
        return Math.random() < probability;
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
