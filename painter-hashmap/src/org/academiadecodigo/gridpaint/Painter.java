package org.academiadecodigo.gridpaint;

import org.academiadecodigo.gridpaint.auxiliaryclasses.MessageHandler;
import org.academiadecodigo.gridpaint.auxiliaryclasses.PainterKeyboard;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Position;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Saver;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.AlgorithmName;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Painter {

    private static final double BORDER = 10;
    private static Saver saver;
    private double cellSize;
    private double width;
    private double height;
    private int slot;
    private int algorithmIndex;
    private Grid grid;
    private Pointer pointer;
    private MessageHandler messageHandler;

    public Painter(double cellSize, double width, double height) {
        this.cellSize = cellSize;
        this.slot = 0;
        this.algorithmIndex = 0;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        //Primary instance
        Rectangle table = new Rectangle(BORDER, BORDER, width, height);

        messageHandler = new MessageHandler(table, this);

        //Primary draw
        table.draw();

        //Final instance
        grid = new Grid(cellSize, table);
        Position newPointerPosition = new Position(table.getX(), table.getY());
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
