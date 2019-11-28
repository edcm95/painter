package org.academiadecodigo.gridpaint.algorithms.langtons;

import org.academiadecodigo.gridpaint.algorithms.Algorithm;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Direction;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonAnt implements Algorithm {

    private Grid grid;
    private Color color;
    private Position position;
    private Direction direction;

    public LangtonAnt(Grid grid, Color color, Position position) {
        this.grid = grid;
        this.color = color;
        this.position = new Position(position.getX(), position.getY());
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
            position.translate(direction);
            Cell currentCell = grid.getCellInPosition(position);

            if (currentCell == null) {
                break;
            }
            processCell(currentCell);
            steps++;
        }
        System.out.println("Langton's ant got tired. It ran " + steps + " steps.");
    }

    protected void processCell(Cell currentCell) {
        if(currentCell.isPainted()){
            currentCell.erase();
            updateDirectionAntiClockwise();
            return;
        }

        currentCell.setColor(color);
        currentCell.draw();
        updateDirectionClockwise();
    }

    protected void updateDirectionClockwise() {
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

    protected void updateDirectionAntiClockwise() {
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
