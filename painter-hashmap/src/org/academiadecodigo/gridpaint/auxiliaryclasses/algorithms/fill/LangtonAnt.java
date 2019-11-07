package org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.fill;

import org.academiadecodigo.gridpaint.Grid;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Cell;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Direction;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Position;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.Algorithm;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonAnt implements Algorithm {

    private Grid grid;
    private Color color;
    private Position position;
    private Direction direction;
    private double cellSize;

    public LangtonAnt(Grid grid, Color color, Position position, double cellSize) {
        this.grid = grid;
        this.color = color;
        this.position = new Position(position.getX(), position.getY());
        this.cellSize = cellSize;
        this.direction = initDirection();
    }

    private Direction initDirection() {
        int number = (int) (Math.floor(Math.random() * 3) + 1);
        return Direction.values()[number];
    }

    @Override
    public void run() {
        int steps = 0;

        while (true) {
            position.translate(direction, cellSize);
            Cell currentCell = grid.getCellInPosition(position);

            if (currentCell == null) {
                break;
            }
            processCell(currentCell);
            steps++;
        }
        System.out.println("Langton's ant got tired. It ran " + steps + " steps.");
    }

    private void processCell(Cell currentCell) {
        if (currentCell.isPainted()) {
            currentCell.erase();
            updateDirectionAntiClockwise();
            return;
        }

        currentCell.setColor(color);
        currentCell.draw();
        updateDirectionClockwise();
    }

    private void updateDirectionClockwise() {
        switch (direction) {
            case UP:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.LEFT;
                break;
            case LEFT:
                direction = Direction.UP;
                break;
        }
    }

    private void updateDirectionAntiClockwise() {
        switch (direction) {
            case UP:
                direction = Direction.LEFT;
                break;
            case LEFT:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.UP;
                break;
        }
    }
}
