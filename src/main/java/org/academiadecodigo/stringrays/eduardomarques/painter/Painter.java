package org.academiadecodigo.stringrays.eduardomarques.painter;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.MessageHandler;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.PainterKeyboard;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Grid;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Pointer;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.Saver;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.AlgorithmName;
import org.academiadecodigo.stringrays.eduardomarques.painter.config.Constants;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Painter {

    private static Saver saver;
    private int slot;
    private int algorithmIndex;
    private Grid grid;
    private Pointer pointer;
    private MessageHandler messageHandler;

    public Painter() {
        this.slot = 0;
        this.algorithmIndex = 0;
        init();
    }

    /**
     * Initializes some properties, extends contructor's logic
     * Also draws the board(main app shape) and the pointer (entity that moves and allows
     * cell selection and interaction)
     */
    private void init() {
        //Primary instance
        Rectangle board = new Rectangle(Constants.BORDER, Constants.BORDER, Constants.WIDTH, Constants.HEIGHT);

        messageHandler = new MessageHandler(board, this);

        //Primary draw
        board.draw();

        //Final instance
        grid = new Grid(board);
        Position newPointerPosition = new Position(board.getX(), board.getY());
        pointer = new Pointer(newPointerPosition, grid);
        saver = new Saver();

        //Secondary draw
        pointer.draw();
    }

    /**
     * Initializes player input
     */
    public void start() {
        PainterKeyboard painterKeyboard = new PainterKeyboard(pointer, this);
        painterKeyboard.keyboardSetup();
    }

    /**
     * Erases all cells in the grid
     */
    public void clearGrid() {
        grid.resetGrid();
    }

    /**
     * Syncs save slot in saver entity, and saves cell's state
     */
    public void save() {
        saver.saveSlot(slot);
        saver.saveData(grid.getMapOfCells());
    }

    /**
     * Syncs save slot in saver entity, and loads cell's state
     */
    public void load() {
        saver.saveSlot(slot);
        saver.loadData(grid.getMapOfCells());
    }

    public int getSaveSlot() {
        return slot;
    }

    /**
     * @return the current algotithm enum object
     */
    public AlgorithmName getCurrentAlgorithm() {
        return AlgorithmName.values()[algorithmIndex];
    }

    /**
     * Cycles the save slot
     */
    public void cycleSaveSlot() {
        slot++;
        if (slot > 10) {
            slot = 0;
        }
        messageHandler.updateSaveSlot();
    }

    /**
     * Cycles the algorithm
     */
    public void cycleAlgorithm() {
        algorithmIndex++;

        if (algorithmIndex >= AlgorithmName.values().length) {
            algorithmIndex = 0;
        }
        messageHandler.updateAlgorithm(AlgorithmName.values()[algorithmIndex]);
    }
}
