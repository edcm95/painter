package org.academiadecodigo.stringrays.eduardomarques.painter.entities;

import org.academiadecodigo.stringrays.eduardomarques.painter.config.Config;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Grid implements Iterable<Position> {

    private Map<Position, Cell> mapOfCells;
    private Rectangle board;

    public Grid(Rectangle board) {
        this.board = board;
        init();
    }

    private void init() {
        System.out.println("GRID init: Creating cells");
        mapOfCells = new HashMap<>();

        double numberOfRows = (board.getHeight() / Config.CELL_SIZE);
        double numberOfCols = (board.getWidth() / Config.CELL_SIZE);
        long start = System.currentTimeMillis();

        //populate map with the rest of the cells
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {
                Position newCellPos = new Position(
                        (board.getX() + j * Config.CELL_SIZE),
                        (board.getY() + i * Config.CELL_SIZE)
                );
                mapOfCells.put(newCellPos, new Cell(newCellPos));
            }
        }

        mapOfCells.values().forEach(Cell::initCell);

        long elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("GRID init: Finished creating "
                + mapOfCells.size()
                + ", cells, it took "
                + elapsedTime + " ms."
        );
    }

    public void resetGrid() {
        mapOfCells.values().parallelStream().forEach((Cell::initCell));
        System.out.println("GRID: Cells reset.");
    }

    public Cell getCellInPosition(Position position) {
        return mapOfCells.get(position);
    }

    @Override
    public Iterator<Position> iterator() {
        return mapOfCells.keySet().iterator();
    }

    public Map<Position, Cell> getMapOfCells() {
        return mapOfCells;
    }

    public Rectangle getBoard() {
        return board;
    }
}
