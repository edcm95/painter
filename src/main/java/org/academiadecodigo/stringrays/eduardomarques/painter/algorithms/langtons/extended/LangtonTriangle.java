package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class LangtonTriangle extends LangtonExtended {

    public LangtonTriangle(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void init() {
        colors = new Color[]{
                Color.GREEN,
                Color.ORANGE,
                Color.WHITE,
                Color.YELLOW,
                Color.PINK,
                Color.RED,
                Color.MAGENTA,
                Color.CYAN,
                Color.BLACK,
                Color.DARK_GRAY,
                Color.LIGHT_GRAY
        };

        directions = new boolean[]{true, true, false, false, false, true, false, false, false, true, true, true};
    }
}
