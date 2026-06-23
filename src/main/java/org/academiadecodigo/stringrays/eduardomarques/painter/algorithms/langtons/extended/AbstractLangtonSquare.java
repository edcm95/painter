package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.ColorWrapper;

public class AbstractLangtonSquare extends AbstractLangtonExtended {

    public AbstractLangtonSquare(Grid grid, ColorWrapper color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void init() {
        colors = new ColorWrapper[]{
            ColorWrapper.YELLOW,
            ColorWrapper.WHITE,
            ColorWrapper.GREEN,
            ColorWrapper.LIGHT_GRAY,
            ColorWrapper.PINK,
            ColorWrapper.CYAN,
            ColorWrapper.MAGENTA,
            ColorWrapper.ORANGE,
        };

        directions = new boolean[]{true, false, false, false, false, false, true, true, false};
    }
}
