package org.academiadecodigo.stringrays.eduardomarques.painter.entities;

import org.academiadecodigo.stringrays.eduardomarques.painter.config.Constants;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.util.HashMap;
import java.util.Iterator;

public class Grid implements Iterable<Position> {

    private HashMap<Position, Cell> mapOfCells;
    private Rectangle board;

    public Grid(Rectangle board) {
        this.board = board;
        init();
    }

    private void init() {
        System.out.println("GRID init: Creating cells");
        mapOfCells = new HashMap<>();

        double numberOfRows = (board.getHeight() / Constants.CELL_SIZE);
        double numberOfCols = (board.getWidth() / Constants.CELL_SIZE);
        long start = System.currentTimeMillis();

        //populate map with the rest of the cells
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {
                Position newCellPos = new Position(
                        (board.getX() + j * Constants.CELL_SIZE),
                        (board.getY() + i * Constants.CELL_SIZE)
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

    public HashMap<Position, Cell> getMapOfCells() {
        return mapOfCells;
    }

    public Rectangle getBoard() {
        return board;
    }
}
