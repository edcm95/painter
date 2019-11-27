package org.academiadecodigo.gridpaint.algorithms.langtons;

import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonSquare extends LangtonExtended {

    public LangtonSquare(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    @Override
    public void processCell(Cell cell) {
        if (ifCellIsThenSet(cell, Color.WHITE, true)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.WHITE, Color.YELLOW, false)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.YELLOW, Color.GREEN, false)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.GREEN, Color.BLACK, false)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.BLACK, Color.PINK, false)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.PINK, Color.CYAN, false)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.CYAN, Color.MAGENTA, true)) {
            return;
        }

        if (ifCellIsThenSet(cell, Color.MAGENTA, Color.ORANGE, true)) {
            return;
        }

        ifCellIsThenErase(cell, Color.ORANGE, false);
    }
}
