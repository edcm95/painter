package org.academiadecodigo.gridpaint;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.util.HashMap;
import java.util.Iterator;

public class Grid implements Iterable<Position> {

    private double cellSize;
    private HashMap<Position, Cell> mapOfCells;
    private Rectangle board;

    public Grid(double cellSize, Rectangle board) {
        this.cellSize = cellSize;
        this.board = board;
        init();
    }

    private void init() {
        mapOfCells = new HashMap<>();

        double numberOfRows = (board.getHeight() / cellSize);
        double numberOfCols = (board.getWidth() / cellSize);
        long start = System.currentTimeMillis();
        System.out.println("GRID init: Creating cells");

        //Add first cell
        Position firstCellPos = new Position(board.getX(), board.getY());
        mapOfCells.put(firstCellPos, new Cell(firstCellPos, cellSize));

        //Populate map with the rest of the cells
        for (int i = 0; i < numberOfRows; i++) {

            for (int j = 0; j < numberOfCols; j++) {
                Position newCellPos = new Position((board.getX() + j * cellSize), (board.getY() + i * cellSize));
                Cell newCell = new Cell(newCellPos, cellSize);
                mapOfCells.put(newCellPos, newCell);
                newCell.initCell();
            }
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

    public double getCellSize() {
        return cellSize;
    }

    public Rectangle getBoard() {
        return board;
    }
}
