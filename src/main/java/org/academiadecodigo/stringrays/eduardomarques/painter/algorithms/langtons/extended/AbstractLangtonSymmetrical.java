package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;

public class AbstractLangtonSymmetrical extends AbstractLangtonExtended {

    public AbstractLangtonSymmetrical(Grid grid, Color color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void init() {
        colors = new Color[]{
                Color.GREEN,
                Color.YELLOW,
                Color.RED
        };

        directions = new boolean[]{false, false, true, true};
    }
}
