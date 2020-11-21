package org.academiadecodigo.stringrays.eduardomarques.painter.entities;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.Algorithm;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.AlgorithmFactory;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.AlgorithmName;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.Direction;
import org.academiadecodigo.stringrays.eduardomarques.painter.config.Config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pointer {

    private boolean writing;
    private Position position;
    private Rectangle pointerShape;
    private Grid grid;
    private Color color;
    private ExecutorService threadPool;

    public Pointer(Position position, Grid grid) {
        this.threadPool = Executors.newSingleThreadExecutor();
        this.grid = grid;
        this.position = position;
        this.pointerShape = new Rectangle(position.getX(), position.getY(), Config.CELL_SIZE, Config.CELL_SIZE);


        this.color = Color.BLACK;
    }

    /**
     * @param direction defines the movement direction
     *                  Moves the pointer
     */
    public void move(Direction direction) {
        if (availBoundaries(direction)) {
            return;
        }

        position.translate(direction);
        pointerShape.translate(direction.getDeltaX() * Config.CELL_SIZE, direction.getDeltaY() * Config.CELL_SIZE);
        paintCell();
    }

    private boolean availBoundaries(Direction direction) {
        return (direction == Direction.LEFT && position.getX() < grid.getBoard().getX() + Config.CELL_SIZE) ||
                (direction == Direction.RIGHT && position.getX() > grid.getBoard().getWidth() - Config.CELL_SIZE) ||
                (direction == Direction.UP && position.getY() < grid.getBoard().getY() + Config.CELL_SIZE) ||
                (direction == Direction.DOWN && position.getY() > grid.getBoard().getHeight() - Config.CELL_SIZE);
    }

    public void recenter() {
        Position lastPosition = new Position(position.getX(), position.getY());
        position.setPosition(grid.getBoard().getX(), grid.getBoard().getY());

        double deltaX = position.getX() - lastPosition.getX();
        double deltaY = position.getY() - lastPosition.getY();

        pointerShape.translate(deltaX, deltaY);
    }

    public void paintCell() {
        if (!writing) {
            return;
        }

        Cell cell = grid.getCellInPosition(position);

        if (cell.isPainted()) {
            cell.initCell();
            return;
        }

        cell.setColor(color);
        cell.paint();
    }

    public void runAlgorithm(AlgorithmName algorithmName) {
        Algorithm algorithm = AlgorithmFactory.getInstanceOf(algorithmName, grid, color, position);

        if (algorithm == null) {
            System.out.println("Internal error: Algorithm not found.");
            return;
        }

        threadPool.execute(algorithm);
        System.out.println("Threads active: " + Thread.activeCount());
    }

    public void setWriting(boolean value) {
        writing = value;
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
