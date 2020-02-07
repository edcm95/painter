package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class AbstractLangtonSquare extends AbstractLangtonExtended {

    public AbstractLangtonSquare(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void init() {
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
