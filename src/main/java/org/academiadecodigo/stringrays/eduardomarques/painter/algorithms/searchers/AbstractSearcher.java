package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.Direction;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
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

    protected void processNeighbouringCells(Position current, LinkedList<Position> positionContainer) {
        Position toLeft = new Position(current);
        toLeft.translate(Direction.LEFT);
        checkCellAndAddToContainer(grid.getCellInPosition(toLeft), toLeft, positionContainer);

        Position toDown = new Position(current);
        toDown.translate(Direction.DOWN);
        checkCellAndAddToContainer(grid.getCellInPosition(toDown), toDown, positionContainer);

        Position toRight = new Position(current);
        toRight.translate(Direction.RIGHT);
        checkCellAndAddToContainer(grid.getCellInPosition(toRight), toRight, positionContainer);

        Position toUp = new Position(current);
        toUp.translate(Direction.UP);
        checkCellAndAddToContainer(grid.getCellInPosition(toUp), toUp, positionContainer);
    }

    protected abstract void checkCellAndAddToContainer(Cell tempCell, Position position, LinkedList<Position> list);
}
