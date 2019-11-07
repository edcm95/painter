package org.academiadecodigo.gridpaint;

import org.academiadecodigo.gridpaint.auxiliaryclasses.*;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.*;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.fill.InitFill;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.LangtonAnt;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pointer {

    private boolean pointerWritingStatus;
    private double cellSize;
    private Position position;
    private Rectangle pointerShape;
    private Grid grid;
    private Color color;
    private ExecutorService threadPool;

    public Pointer(Position position, Grid grid) {
        this.threadPool = Executors.newCachedThreadPool();

        this.grid = grid;
        this.cellSize = grid.getCellSize();
        this.position = position;
        this.pointerShape = new Rectangle(position.getX(), position.getY(), cellSize, cellSize);

        //Because I like cyan
        this.color = Color.CYAN;
    }

    public void move(Direction direction) {
        if (direction == Direction.LEFT && position.getX() < grid.getBoard().getX() + cellSize) {
            return;
        }

        if (direction == Direction.RIGHT && position.getX() > grid.getBoard().getWidth() - cellSize) {
            return;
        }

        if (direction == Direction.UP && position.getY() < grid.getBoard().getY() + cellSize) {
            return;
        }

        if (direction == Direction.DOWN && position.getY() > grid.getBoard().getHeight() - cellSize) {
            return;
        }

        position.translate(direction.getDeltaX() * cellSize, direction.getDeltaY() * cellSize);
        pointerShape.translate(direction.getDeltaX() * cellSize, direction.getDeltaY() * cellSize);
        paintCell();
    }

    public void recenter() {
        Position lastPosition = new Position(position.getX(), position.getY());
        position.setPosition(grid.getBoard().getX(), grid.getBoard().getY());

        double deltaX = position.getX() - lastPosition.getX();
        double deltaY = position.getY() - lastPosition.getY();

        pointerShape.translate(deltaX, deltaY);
    }

    public void paintCell() {
        if (!pointerWritingStatus) {
            return;
        }

        Cell cell = grid.getCellInPosition(position);

        if (cell == null) {
            System.out.println("Cell is null.");
            return;
        }

        if (cell.isPainted()) {
            cell.erase();
            return;
        }

        cell.setColor(color);
        cell.draw();
    }

    public void runAlgorithm(AlgorithmName algorithmName) {
        HashMap<AlgorithmName, Algorithm> algorithmMap = new HashMap<>();

        algorithmMap.put(AlgorithmName.FILL, InitFill.getFillInstance(grid, color, position, cellSize));
        algorithmMap.put(AlgorithmName.MAZE, new Maze(grid, color, position, cellSize));
        algorithmMap.put(AlgorithmName.LANGTON_ANT, new LangtonAnt(grid, color, position, cellSize));

        threadPool.execute(algorithmMap.get(algorithmName));
        System.out.println("Threads active: " + Thread.activeCount());
    }

    public void setPointerWritingStatus(boolean value) {
        pointerWritingStatus = value;
    }

    public void setColor(Color color) {
        this.color = color;
        draw();
    }

    public void draw() {
        pointerShape.setColor(color);
        pointerShape.fill();
    }
}
