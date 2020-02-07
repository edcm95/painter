package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class AbstractLangtonChaos extends AbstractLangtonExtended {

    public AbstractLangtonChaos(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void init() {
        colors = new Color[]{
                Color.PINK,
                Color.GREEN
        };

        directions = new boolean[]{true, false, true};
    }
}
