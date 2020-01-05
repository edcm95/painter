package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonChaos extends LangtonExtended {

    public LangtonChaos(Grid grid, Color color, Position position) {
        super(grid, color, position);
        init();
    }

    @Override
    public void processCell(Cell cell) {
        if (ifCellIsThenSet(cell, Color.PINK, true)) {
            return;
        }
        // I know... xD
        for (int i = 1; i < directions.length - 1; i++) {
            if (ifCellIsThenSet(cell, colors[i - 1], colors[i], directions[i])) {
                return;
            }
        }

        ifCellIsThenErase(cell, Color.GREEN, true);
    }

    private void init() {
        colors = new Color[]{
                Color.PINK,
                Color.GREEN
        };

        directions = new boolean[]{true, false, true};
    }
}
