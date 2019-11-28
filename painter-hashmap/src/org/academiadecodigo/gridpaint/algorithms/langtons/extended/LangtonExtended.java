package org.academiadecodigo.gridpaint.algorithms.langtons.extended;

import org.academiadecodigo.gridpaint.algorithms.langtons.LangtonAnt;
import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonExtended extends LangtonAnt {

    protected Color[] colors;
    protected boolean[] directions;

    public LangtonExtended(Grid grid, Color color, Position position) {
        super(grid, color, position);
        init();
    }

    @Override
    protected void processCell(Cell cell) {
        if (ifCellIsThenSet(cell, Color.GREEN, false)) {
            return;
        }

        for (int i = 1; i < directions.length - 1; i++) {
            if (ifCellIsThenSet(cell, colors[i - 1], colors[i], directions[i])) {
                return;
            }
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

    private void init() {
        colors = new Color[]{
                Color.GREEN,
                Color.YELLOW,
                Color.RED
        };

        directions = new boolean[]{false, false, true, true};
    }
}
