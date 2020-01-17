package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.LangtonAnt;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.langtons.extended.*;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers.Maze;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.searchers.fill.InitFill;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;

public class AlgorithmFactory {

    public static Algorithm getInstanceOf(AlgorithmName name, Grid grid, Color color, Position position) {

        switch (name) {
            case FILL:
                return InitFill.getFillInstance(grid, color, position);

            case MAZE:
                return new Maze(grid, color, position);

            case LANGTON_ANT:
                return new LangtonAnt(grid, color, position);

            case LANGTON_CHAOS:
                return new LangtonChaos(grid, color, position);

            case LANGTON_SQUARE:
                return new LangtonSquare(grid, color, position);

            case LANGTON_TRIANGLE:
                return new LangtonTriangle(grid, color, position);

            case LANGTON_CONVOLUTED:
                return new LangtonConvoluted(grid, color, position);

            case LANGTON_SYMMETRICAL:
                return new LangtonSymmetrical(grid, color, position);

            case GAME_OF_LIFE:
                return new GameOfLife(grid, color);

            default:
                return null;
        }
    }
}
