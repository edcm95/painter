package org.academiadecodigo.gridpaint.algorithms.langtons.extended;

import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonSquare extends LangtonExtended {

    public LangtonSquare(Grid grid, Color color, Position position) {
        super(grid, color, position);
        init();
    }

    @Override
    public void processCell(Cell cell) {
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

    private void init() {
        colors = new Color[]{
                Color.YELLOW,
                Color.WHITE,
                Color.GREEN,
                Color.LIGHT_GRAY,
                Color.PINK,
                Color.CYAN,
                Color.MAGENTA,
                Color.ORANGE,
        };

        directions = new boolean[]{true, false, false, false, false, false, true, true, false};
    }
}
