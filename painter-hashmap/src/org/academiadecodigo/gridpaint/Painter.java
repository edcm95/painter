package org.academiadecodigo.gridpaint;

import org.academiadecodigo.gridpaint.auxiliaryclasses.Saver;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Painter implements KeyboardHandler {

    private static final double BORDER = 10;
    private Text saveSlot;
    private Grid grid;
    private Pointer pointer;
    private Saver saver;
    private double cellSize;
    private double width;
    private double height;
    private int slot;

    public Painter(double cellSize, double width, double height) {
        this.cellSize = cellSize;
        this.slot = 0;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        Rectangle table = new Rectangle(BORDER, BORDER, width, height);


        //Make pointer
        Position newPointerPosition = new Position(table.getX(), table.getY());


        //Make instructions
        Text inst = new Text(table.getWidth() + table.getX() + 10, table.getY(), "Instructions:");
        Text inst1 = new Text(inst.getX(), inst.getY() + 20, "S - Save, X - Load, C - Clear");
        Text inst2 = new Text(inst1.getX(), inst1.getY() + 40, "H - Left, J - Up, K - Down, L - Right");
        Text inst3 = new Text(inst2.getX(), inst2.getY() + 40, "1 - Cyan, 2 - Yellow, 3 - Green");
        Text inst4 = new Text(inst3.getX(), inst3.getY() + 20, "4 - Pink, 5 - Magenta, 6 - Red");
        Text inst5 = new Text(inst4.getX(), inst4.getY() + 20, "7 - Blue, 8 - Black, 9 - White");
        Text inst6 = new Text(inst5.getX(), inst5.getY() + 40, "R - Recenter pointer, F - Fill, M - Maze");
        Text inst7 = new Text(inst6.getX(), inst6.getY() + 40, "B - Increase slot, N - Decrease slot, P - Exit");
        saveSlot = new Text(inst7.getX(), inst7.getY() + 20, "Save slot: " + slot);

        //Primary draw
        //table.setColor(Color.BLACK);
        table.draw();
        inst.draw();
        inst1.draw();
        inst2.draw();
        inst3.draw();
        inst4.draw();
        inst5.draw();
        inst6.draw();
        inst7.draw();

        //Instance final elements
        grid = new Grid(cellSize, table);
        pointer = new Pointer(newPointerPosition, grid);
        saver = new Saver();

        //Secondary draw
        saveSlot.draw();
        pointer.draw();
    }

    void start() {


        keyboardSetup();
    }

    //----------------------------------------------------------------------------------KEYBOARD SETUP------------------
    // TODO: 24/10/2019 Pack keyboard stuff into a new class to make this clearner looking
    private void keyboardSetup() {
        Keyboard keyboard = new Keyboard(this);

        int[] keys = new int[]{
                KeyboardEvent.KEY_1,
                KeyboardEvent.KEY_2,
                KeyboardEvent.KEY_3,
                KeyboardEvent.KEY_4,
                KeyboardEvent.KEY_5,
                KeyboardEvent.KEY_6,
                KeyboardEvent.KEY_7,
                KeyboardEvent.KEY_8,
                KeyboardEvent.KEY_9,
                KeyboardEvent.KEY_J,
                KeyboardEvent.KEY_K,
                KeyboardEvent.KEY_H,
                KeyboardEvent.KEY_L,
                KeyboardEvent.KEY_SPACE,
                KeyboardEvent.KEY_S,
                KeyboardEvent.KEY_P,
                KeyboardEvent.KEY_X,
                KeyboardEvent.KEY_Z,
                KeyboardEvent.KEY_C,
                KeyboardEvent.KEY_R,
                KeyboardEvent.KEY_B,
                KeyboardEvent.KEY_N,
                KeyboardEvent.KEY_F,
                KeyboardEvent.KEY_M,
        };


        for (int key : keys) {
            createKeyboardEvent(key, KeyboardEventType.KEY_PRESSED, keyboard);
            createKeyboardEvent(key, KeyboardEventType.KEY_RELEASED, keyboard);
        }
    }

    private void createKeyboardEvent(int key, KeyboardEventType type, Keyboard keyboard) {
        KeyboardEvent keyboardEvent = new KeyboardEvent();
        keyboardEvent.setKey(key);
        keyboardEvent.setKeyboardEventType(type);
        keyboard.addEventListener(keyboardEvent);
    }

    @Override
    public void keyPressed(KeyboardEvent event) {

        switch (event.getKey()) {
            case (KeyboardEvent.KEY_H):
                pointer.moveLeft();
                break;

            case (KeyboardEvent.KEY_L):
                pointer.moveRight();
                break;

            case (KeyboardEvent.KEY_K):
                pointer.moveDown();
                break;

            case (KeyboardEvent.KEY_J):
                pointer.moveUp();
                break;

            case (KeyboardEvent.KEY_SPACE):
                pointer.setPointerWritingStatus(true);
                pointer.paintCell();
                break;

            case (KeyboardEvent.KEY_1):
                pointer.setColor(Color.CYAN);
                break;

            case (KeyboardEvent.KEY_2):
                pointer.setColor(Color.YELLOW);
                break;

            case (KeyboardEvent.KEY_3):
                pointer.setColor(Color.GREEN);
                break;

            case (KeyboardEvent.KEY_4):
                pointer.setColor(Color.PINK);
                break;

            case (KeyboardEvent.KEY_5):
                pointer.setColor(Color.MAGENTA);
                break;

            case (KeyboardEvent.KEY_6):
                pointer.setColor(Color.RED);
                break;

            case (KeyboardEvent.KEY_7):
                pointer.setColor(Color.BLUE);
                break;

            case (KeyboardEvent.KEY_8):
                pointer.setColor(Color.BLACK);
                break;

            case (KeyboardEvent.KEY_9):
                pointer.setColor(Color.WHITE);
                break;

            case (KeyboardEvent.KEY_X):
                load();
                break;

            case (KeyboardEvent.KEY_S):
                save();
                break;

            case (KeyboardEvent.KEY_P):
                System.exit(0);
                break;

            case (KeyboardEvent.KEY_C):
                grid.resetGrid();
                break;

            case (KeyboardEvent.KEY_R):
                pointer.recenter();
                break;

            case (KeyboardEvent.KEY_B):
                changeSlot(1);
                break;

            case (KeyboardEvent.KEY_N):
                changeSlot(-1);
                break;

            case (KeyboardEvent.KEY_F):
                pointer.fill();
                break;

            case (KeyboardEvent.KEY_M):
                pointer.doTheMaze();
                break;

        }

    }

    @Override
    public void keyReleased(KeyboardEvent kEvent) {
        switch (kEvent.getKey()) {
            case (KeyboardEvent.KEY_SPACE):
                pointer.setPointerWritingStatus(false);
                break;
        }

    }
    //------------------------------------------------------------------------------END OF KEYBOARD SETUP---------------

    private void save() {
        saver.saveSlot(slot);
        saver.saveData(grid.getMapOfCells());
    }

    private void load() {
        saver.saveSlot(slot);
        saver.loadData(grid.getMapOfCells());
    }

    private void changeSlot(int value) {
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
