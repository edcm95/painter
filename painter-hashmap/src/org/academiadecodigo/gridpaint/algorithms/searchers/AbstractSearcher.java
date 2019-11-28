package org.academiadecodigo.gridpaint.algorithms.searchers;

import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Direction;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.LinkedList;

public abstract class AbstractSearcher {

    protected Grid grid;
    protected Color color;
    protected Color rootColor;
    protected Position position;

    public AbstractSearcher(Grid grid, Color color, Position position) {
        this.grid = grid;
        this.color = color;
        this.rootColor = grid.getCellInPosition(position).getColor();
        this.position = position;
    }

    protected void processNeighbouringCells(Position current, Cell tempCell, LinkedList<Position> positionContainer) {
        Position toRight = new Position(current);
        toRight.translate(Direction.RIGHT);
        tempCell = grid.getCellInPosition(toRight);
        checkCellAndAddToContainer(tempCell, toRight, positionContainer);

        Position toUp = new Position(current);
        toUp.translate(Direction.UP);
        tempCell = grid.getCellInPosition(toUp);
        checkCellAndAddToContainer(tempCell, toUp, positionContainer);

        Position toDown = new Position(current);
        toDown.translate(Direction.DOWN);
        tempCell = grid.getCellInPosition(toDown);
        checkCellAndAddToContainer(tempCell, toDown, positionContainer);

        Position toLeft = new Position(current);
        toLeft.translate(Direction.LEFT);
        tempCell = grid.getCellInPosition(toLeft);
        checkCellAndAddToContainer(tempCell, toLeft, positionContainer);
    }

    protected abstract void checkCellAndAddToContainer(Cell tempCell, Position position, LinkedList<Position> list);
}
