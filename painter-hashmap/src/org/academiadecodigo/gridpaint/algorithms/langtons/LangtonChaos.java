package org.academiadecodigo.gridpaint.algorithms.langtons;

import org.academiadecodigo.gridpaint.entities.Cell;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonChaos extends LangtonExtended {

    public LangtonChaos(Grid grid, Color color, Position position) {
        super(grid, color, position);
        init();
    }

    public void init() {
        colors = new Color[]{
                Color.PINK,
                Color.GREEN
        };

        directions = new boolean[]{true, false, true};
    }

    @Override
    public void processCell(Cell cell) {
        if (ifCellIsThenSet(cell, Color.PINK, true)) {
            return;
        }
        // I know... xD
        for (int i = 1; i < directions.length - 1; i++) {
            if (ifCellIsThenSet(cell, colors[i - 1], colors[i], directions[i])){
                return;
            }
        }

        ifCellIsThenErase(cell, Color.GREEN, true);

    }
}
