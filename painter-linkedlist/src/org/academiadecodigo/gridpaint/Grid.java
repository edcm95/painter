package org.academiadecodigo.gridpaint;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.util.Iterator;
import java.util.LinkedList;

public class Grid implements Iterable<Cell> {

    private LinkedList<Cell> listOfCells;
    private double cellSize;
    private Rectangle board;

    public Grid(double cellSize, Rectangle board) {
        this.cellSize = cellSize;
        this.board = board;

        init();
    }

    private void init() {
        listOfCells = new LinkedList<>();

        double numberOfCells = (board.getWidth() / cellSize) * (board.getHeight() / cellSize);
        long start = System.nanoTime();
        System.out.println("GRID init: Creating cells");

        //Populate listOfCells with cells

        //Add first cell
        Position firstCellPos = new Position(board.getX(), board.getY());
        listOfCells.add(new Cell(firstCellPos, cellSize));

        while (listOfCells.size() < numberOfCells) {
            double lastCellX = listOfCells.getLast().getPosition().getX();
            double lastCellY = listOfCells.getLast().getPosition().getY();

            if (lastCellX + cellSize > board.getX() + board.getWidth() - cellSize) {
                lastCellX = board.getX();
                lastCellY = lastCellY + cellSize;

                double newCellY = lastCellY;
                double newCellX = lastCellX;

                Position newCellPos = new Position(newCellX, newCellY);
                Cell newCell = new Cell(newCellPos, cellSize);

                listOfCells.add(newCell);
                continue;
            }

            double newCellY = lastCellY;
            double newCellX = lastCellX + cellSize;

            Position newCellPos = new Position(newCellX, newCellY);
            Cell newCell = new Cell(newCellPos, cellSize);

            listOfCells.add(newCell);

        }

        long elapsedTime = (System.nanoTime() - start) / 1000000;
        System.out.println("GRID init: Finished creating cells, it took " + elapsedTime + " ms.");

    }

    public void resetGrid() {

        for (Cell cell : this) {
            cell.erase();
        }
        System.out.println("GRID: Cells cleared.");
    }

    public Cell getCellInPosition(Position position) {

        for (Cell cell : this) {
            if (cell.getPosition().isSameAs(position)) {
                return cell;
            }
        }
        return null;
    }

    @Override
    public Iterator<Cell> iterator() {
        return listOfCells.iterator();
    }

    public LinkedList<Cell> getListOfCells() {
        return listOfCells;
    }

    public double getCellSize() {
        return cellSize;
    }

    public Rectangle getBoard() {
        return board;
    }
}
