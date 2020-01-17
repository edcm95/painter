package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.LangtonAnt;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public abstract class LangtonExtended extends LangtonAnt {

    protected Color[] colors;
    protected boolean[] directions;

    public LangtonExtended(Grid grid, Color color, Position position) {
        super(grid, color, position);
        init();
    }

    @Override
    protected void processCell(Cell cell) {
        if (ifCellIsThenSet(cell, colors[0], directions[0])) {
            return;
        }

        for (int i = 1; i < directions.length - 1; i++) {
            if (ifCellIsThenSet(cell, colors[i - 1], colors[i], directions[i])) {
                return;
            }
        }

        ifCellIsThenErase(cell, colors[colors.length - 1], directions[directions.length - 1]);
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
            cell.paint();
            processUpdateDirection(clockWise);
            return true;
        }

        return false;
    }

    protected boolean ifCellIsThenSet(Cell cell, Color is, Color toBe, boolean clockWise) {
        if (cell.isPainted() && cell.getColor() == is) {
            cell.setColor(toBe);
            cell.paint();
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

    protected abstract void init();
}
