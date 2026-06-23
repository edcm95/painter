package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.ColorWrapper;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;

public class AbstractLangtonConvoluted extends AbstractLangtonExtended {

    public AbstractLangtonConvoluted(Grid grid, ColorWrapper color, Position position) {
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

        directions = new boolean[]{false, false, true, true, true, false, true, false, true, false, false, true};
    }
}
