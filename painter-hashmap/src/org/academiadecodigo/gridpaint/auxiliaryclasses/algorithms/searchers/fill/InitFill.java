package org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.searchers.fill;


import org.academiadecodigo.gridpaint.Grid;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Position;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.Algorithm;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class InitFill {

    public static Algorithm getFillInstance(Grid grid, Color color, Position position) {
        if (grid.getCellInPosition(position).isPainted()) {
            return new Refill(grid, color, position);
        }

        return new Fill(grid, color, position);
    }
}
