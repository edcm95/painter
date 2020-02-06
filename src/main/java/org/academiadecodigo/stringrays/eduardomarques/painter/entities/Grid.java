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
        mapOfCells = new HashMap<>();

        double numberOfRows = (board.getHeight() / Constants.CELL_SIZE);
        double numberOfCols = (board.getWidth() / Constants.CELL_SIZE);
        long start = System.currentTimeMillis();
        System.out.println("GRID init: Creating cells");

        //Add first cell
        Position firstCellPos = new Position(board.getX(), board.getY());
        mapOfCells.put(firstCellPos, new Cell(firstCellPos));

        //Populate map with the rest of the cells
        for (int i = 0; i < numberOfRows; i++) {

            for (int j = 0; j < numberOfCols; j++) {
                Position newCellPos = new Position((board.getX() + j * Constants.CELL_SIZE), (board.getY() + i * Constants.CELL_SIZE));
                Cell newCell = new Cell(newCellPos);
                mapOfCells.put(newCellPos, newCell);
            }
        }

        for (Cell cell : mapOfCells.values()) {
            cell.initCell();
        }

        long elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("GRID init: Finished creating cells, it took " + elapsedTime + " ms.");
    }

    public void resetGrid() {
        for (Position position : this) {
            mapOfCells.get(position).erase();
        }

        System.out.println("GRID: Cells cleared.");
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
