package org.academiadecodigo.gridpaint.algorithms.langtons;

import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonExtended extends LangtonAnt {

    public LangtonExtended(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void processCell(Cell cell) {
        if (ifCellIsThenSet(cell, Color.GREEN, false)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.GREEN, Color.YELLOW, false)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.YELLOW, Color.RED, true)) {
            return;
        }

        ifCellIsThenErase(cell, Color.RED, true);

    }

    protected boolean ifCellIsThenErase(Cell cell, Color is, boolean clockWise) {
        if (cell.isPainted() && cell.getColor() == is) {
            cell.erase();
            processUpdateDirection(clockWise);
            return true;
        }
        return false;
    }

    protected boolean ifCellIsThenSet(Cell cell, Color toBe, boolean clockWise) {
        if (!cell.isPainted()) {
            cell.setColor(toBe);
            cell.draw();
            processUpdateDirection(clockWise);
            return true;
        }

        return false;
    }

    protected boolean ifCellIsThenSet(Cell cell, Color is, Color toBe, boolean clockWise) {
        if (cell.isPainted() && cell.getColor() == is) {
            cell.setColor(toBe);
            cell.draw();
            processUpdateDirection(clockWise);
            return true;
        }
        return false;
    }

    protected void processUpdateDirection(boolean clockWise) {
        if (clockWise) {
            updateDirectionClockwise();
            return;
        }
        updateDirectionAntiClockwise();
    }


}
