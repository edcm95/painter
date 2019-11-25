package org.academiadecodigo.gridpaint.algorithms.searchers.fill;


import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.gridpaint.algorithms.Algorithm;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class InitFill {

    public static Algorithm getFillInstance(Grid grid, Color color, Position position) {
        if (grid.getCellInPosition(position).isPainted()) {
            return new Refill(grid, color, position);
        }

        return new Fill(grid, color, position);
    }
}
