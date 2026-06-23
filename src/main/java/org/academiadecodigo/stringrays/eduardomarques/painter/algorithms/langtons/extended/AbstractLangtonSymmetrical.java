package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.ColorWrapper;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;

public class AbstractLangtonSymmetrical extends AbstractLangtonExtended {

    public AbstractLangtonSymmetrical(Grid grid, ColorWrapper color, Position position) {
        super(grid, color, position);
    }

    @Override
    protected void init() {
        colors = new ColorWrapper[]{
            ColorWrapper.GREEN,
            ColorWrapper.YELLOW,
            ColorWrapper.RED
        };

        directions = new boolean[]{false, false, true, true};
    }
}
