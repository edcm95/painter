package org.academiadecodigo.gridpaint.entities;

import org.academiadecodigo.gridpaint.algorithms.langtons.LangtonExtended;
import org.academiadecodigo.gridpaint.algorithms.langtons.LangtonSquare;
import org.academiadecodigo.gridpaint.auxiliaryclasses.*;
import org.academiadecodigo.gridpaint.algorithms.*;
import org.academiadecodigo.gridpaint.algorithms.searchers.fill.InitFill;
import org.academiadecodigo.gridpaint.algorithms.langtons.LangtonAnt;
import org.academiadecodigo.gridpaint.algorithms.searchers.Maze;
import org.academiadecodigo.gridpaint.config.Constants;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import java.util.HashMap;
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
        this.threadPool = Executors.newCachedThreadPool();
        this.grid = grid;
        this.position = position;
        this.pointerShape = new Rectangle(position.getX(), position.getY(), Constants.CELL_SIZE, Constants.CELL_SIZE);

        //Because I like cyan
        this.color = Color.CYAN;
    }

    public void move(Direction direction) {
        if(availBoundaries(direction)){
            return;
        }

        position.translate(direction.getDeltaX() * Constants.CELL_SIZE, direction.getDeltaY() * Constants.CELL_SIZE);
        pointerShape.translate(direction.getDeltaX() * Constants.CELL_SIZE, direction.getDeltaY() * Constants.CELL_SIZE);
        paintCell();
    }

    private boolean availBoundaries(Direction direction){
        return  (direction == Direction.LEFT && position.getX() < grid.getBoard().getX() + Constants.CELL_SIZE) ||
                (direction == Direction.RIGHT && position.getX() > grid.getBoard().getWidth() - Constants.CELL_SIZE) ||
                (direction == Direction.UP && position.getY() < grid.getBoard().getY() + Constants.CELL_SIZE) ||
                (direction == Direction.DOWN && position.getY() > grid.getBoard().getHeight() - Constants.CELL_SIZE);
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

        algorithmMap.put(AlgorithmName.FILL, InitFill.getFillInstance(grid, color, position));
        algorithmMap.put(AlgorithmName.MAZE, new Maze(grid, color, position));
        algorithmMap.put(AlgorithmName.LANGTON_ANT, new LangtonAnt(grid, color, position));
        algorithmMap.put(AlgorithmName.LANGTON_SYMMETRICAL, new LangtonExtended(grid, color, position));
        algorithmMap.put(AlgorithmName.LANGTON_SQUARE, new LangtonSquare(grid, color, position));

        threadPool.execute(algorithmMap.get(algorithmName));
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
