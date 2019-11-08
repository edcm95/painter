package org.academiadecodigo.gridpaint;

import org.academiadecodigo.gridpaint.auxiliaryclasses.MessageHandler;
import org.academiadecodigo.gridpaint.auxiliaryclasses.PainterKeyboard;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Position;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Saver;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Painter {

    private static final double BORDER = 10;
    private static Saver saver;
    private double cellSize;
    private double width;
    private double height;
    private int slot;
    private Grid grid;
    private Pointer pointer;

    public Painter(double cellSize, double width, double height) {
        this.cellSize = cellSize;
        this.slot = 0;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        //Primary instance
        Rectangle table = new Rectangle(BORDER, BORDER, width, height);

        MessageHandler messageHandler = new MessageHandler(table, this);

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

    public int getSaveSlot(){
        return slot;
    }

    // TODO: 08/11/2019 Implement this
    public void increaseSaveSlot() {
        if (slot == 3) {
            return;
        }
        slot++;
        messageHandler.updateSaveSlot();
    }

    public void decreaseSaveSlot() {
        if (slot == 0) {
            return;
        }
        slot--;
        messageHandler.updateSaveSlot();
    }
}
