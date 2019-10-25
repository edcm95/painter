package org.academiadecodigo.gridpaint;

import org.academiadecodigo.gridpaint.auxiliaryclasses.*;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

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

    //---------------------------MOVEMENT METHODS
    public void moveLeft() {
        if (position.getX() < grid.getBoard().getX() + cellSize) {
            return;
        }

        position.translate(-cellSize, 0);
        pointerShape.translate(-cellSize, 0);
        paintCell();
    }

    public void moveRight() {
        if (position.getX() > grid.getBoard().getWidth() - cellSize) {
            return;
        }

        position.translate(cellSize, 0);
        pointerShape.translate(cellSize, 0);
        paintCell();
    }

    public void moveUp() {
        if (position.getY() < grid.getBoard().getY() + cellSize) {
            return;
        }

        position.translate(0, -cellSize);
        pointerShape.translate(0, -cellSize);
        paintCell();
    }

    public void moveDown() {
        if (position.getY() > grid.getBoard().getHeight() - cellSize) {
            return;
        }

        position.translate(0, cellSize);
        pointerShape.translate(0, cellSize);
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

    public void fill() {

        if(grid.getCellInPosition(position).isPainted()) {
            threadPool.execute(new RepaintFill(grid,
                    color,
                    grid.getCellInPosition(position).getColor(),
                    position,
                    cellSize));
            System.out.println(Thread.activeCount());
            return;
        }

        threadPool.execute(new Fill(grid, color, position, cellSize));
        System.out.println(Thread.activeCount());
    }

    public void doTheMaze() {
        new Thread(new Maze(color, grid, position, cellSize)).start();

        System.out.println(Thread.activeCount());
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
