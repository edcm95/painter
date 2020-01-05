package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers.fill;


import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.Algorithm;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class InitFill {

    public static Algorithm getFillInstance(Grid grid, Color color, Position position) {
        if (grid.getCellInPosition(position).isPainted()) {
            return new Refill(grid, color, position);
        }

        return new Fill(grid, color, position);
    }
}
