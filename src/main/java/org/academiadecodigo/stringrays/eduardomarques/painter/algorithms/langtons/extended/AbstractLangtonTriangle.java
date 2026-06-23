package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.ColorWrapper;

public class AbstractLangtonTriangle extends AbstractLangtonExtended {

    public AbstractLangtonTriangle(Grid grid, ColorWrapper color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void init() {
        colors = new ColorWrapper[]{
            ColorWrapper.GREEN,
            ColorWrapper.ORANGE,
            ColorWrapper.WHITE,
            ColorWrapper.YELLOW,
            ColorWrapper.PINK,
            ColorWrapper.RED,
            ColorWrapper.MAGENTA,
            ColorWrapper.CYAN,
            ColorWrapper.BLACK,
            ColorWrapper.DARK_GRAY,
            ColorWrapper.LIGHT_GRAY
        };

        directions = new boolean[]{true, true, false, false, false, true, false, false, false, true, true, true};
    }
}
