package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.ColorWrapper;

public class AbstractLangtonChaos extends AbstractLangtonExtended {

    public AbstractLangtonChaos(Grid grid, ColorWrapper color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void init() {
        colors = new ColorWrapper[]{
            ColorWrapper.PINK,
            ColorWrapper.GREEN
        };

        directions = new boolean[]{true, false, true};
    }
}
