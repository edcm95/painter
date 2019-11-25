package org.academiadecodigo.gridpaint;

import org.academiadecodigo.gridpaint.auxiliaryclasses.MessageHandler;
import org.academiadecodigo.gridpaint.auxiliaryclasses.PainterKeyboard;
import org.academiadecodigo.gridpaint.entities.Grid;
import org.academiadecodigo.gridpaint.entities.Pointer;
import org.academiadecodigo.gridpaint.entities.Position;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Saver;
import org.academiadecodigo.gridpaint.algorithms.AlgorithmName;
import org.academiadecodigo.gridpaint.config.Constants;
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

    public void start() {
        PainterKeyboard painterKeyboard = new PainterKeyboard(pointer, this);
        painterKeyboard.keyboardSetup();
    }

    public void clearGrid() {
        grid.resetGrid();
    }

    public void save() {
        saver.saveSlot(slot);
        saver.saveData(grid.getMapOfCells());
    }

    public void load() {
        saver.saveSlot(slot);
        saver.loadData(grid.getMapOfCells());
    }

    public int getSaveSlot() {
        return slot;
    }

    public AlgorithmName getCurrentAlgorithm() {
        return AlgorithmName.values()[algorithmIndex];
    }

    public void cycleSaveSlot() {
        slot++;
        if (slot > 3) {
            slot = 0;
        }
        messageHandler.updateSaveSlot();
    }

    public void cycleAlgorithm() {
        algorithmIndex++;

        if (algorithmIndex >= AlgorithmName.values().length) {
            algorithmIndex = 0;
        }
        messageHandler.updateAlgorithm(AlgorithmName.values()[algorithmIndex]);
    }
}
