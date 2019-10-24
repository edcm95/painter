package org.academiadecodigo.gridpaint;

import org.academiadecodigo.gridpaint.auxiliaryclasses.PainterKeyboard;
import org.academiadecodigo.gridpaint.auxiliaryclasses.Saver;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Painter {

    private static final double BORDER = 10;
    private static Saver saver;
    private double cellSize;
    private double width;
    private double height;
    private int slot;
    private Text saveSlot;
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


        //Make instructions
        Text text0 = new Text(table.getWidth() + table.getX() + 10, table.getY(), "Instructions:");
        Text text1 = new Text(text0.getX(), text0.getY() + 20, "S - Save, X - Load, C - Clear");
        Text text2 = new Text(text1.getX(), text1.getY() + 40, "H - Left, J - Up, K - Down, L - Right");
        Text text3 = new Text(text2.getX(), text2.getY() + 40, "1 - Cyan, 2 - Yellow, 3 - Green");
        Text text4 = new Text(text3.getX(), text3.getY() + 20, "4 - Pink, 5 - Magenta, 6 - Red");
        Text text5 = new Text(text4.getX(), text4.getY() + 20, "7 - Blue, 8 - Black, 9 - White");
        Text text6 = new Text(text5.getX(), text5.getY() + 40, "R - Recenter pointer, F - Fill, M - Maze");
        Text text7 = new Text(text6.getX(), text6.getY() + 40, "B - Increase slot, N - Decrease slot, P - Exit");
        saveSlot = new Text(text7.getX(), text7.getY() + 20, "Save slot: " + slot);

        //Primary draw
        table.draw();
        text0.draw();
        text1.draw();
        text2.draw();
        text3.draw();
        text4.draw();
        text5.draw();
        text6.draw();
        text7.draw();

        //Final instance
        grid = new Grid(cellSize, table);
        Position newPointerPosition = new Position(table.getX(), table.getY());
        pointer = new Pointer(newPointerPosition, grid);
        saver = new Saver();

        //Secondary draw
        saveSlot.draw();
        pointer.draw();
    }

    public void start() {
        PainterKeyboard painterKeyboard = new PainterKeyboard(pointer, this);
        painterKeyboard.keyboardSetup();
    }

    public void clearGrid(){
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

    public void changeSlot(int value) {
        saveSlot.delete();
        slot = slot + value;

        if (slot < 0) {
            slot = 0;
        }

        if (slot > 3) {
            slot = 3;
        }

        saveSlot.setText("Save slot: " + slot);
        saveSlot.draw();
    }
}
