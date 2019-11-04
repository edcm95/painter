package org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.fill;


import org.academiadecodigo.gridpaint.Grid;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Position;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.Algorithm;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class InitFill {

    public static Algorithm getFillInstance(Grid grid, Color color, Position position, double cellSize) {
        if (grid.getCellInPosition(position).isPainted()) {
            return new Refill(grid, color, position, cellSize);
        }

        return new Fill(grid, color, position, cellSize);
    }
}
