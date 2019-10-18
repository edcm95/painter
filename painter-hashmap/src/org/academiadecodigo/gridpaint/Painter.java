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
    private static Text warning;
    public static Text message;
    private Text text7;
    private Grid grid;
    private Pointer pointer;
    private Saver saver;
    private double cellSize;
    private double width;
    private double height;
    private int slot;
    private boolean ready;

    public Painter(double cellSize, double width, double height) {
        warning = new Text(BORDER, BORDER, "");
        this.cellSize = cellSize;
        this.slot = 0;
        this.width = width;
        this.height = height;
    }

    private void init() {
        Rectangle table = new Rectangle(BORDER, BORDER, width, height);
        grid = new Grid(cellSize, table);

        table.setColor(Color.BLACK);
        table.draw();

        //Make pointer
        Position newPointerPosition = new Position(table.getX(), table.getY());
        pointer = new Pointer(newPointerPosition, grid);

        //Draw instructions
        Text instructions = new Text(table.getWidth() + table.getX() + 10, table.getY(), "Instructions:");
        Text text = new Text(instructions.getX(), instructions.getY() + 20, "S - Save, X - Load, C - Clear");
        Text text1 = new Text(text.getX(), text.getY() + 40, "H - Left, J - Up, K - Down, L - Right");
        Text text2 = new Text(text1.getX(), text1.getY() + 40, "1 - Cyan, 2 - Yellow, 3 - Green");
        Text text3 = new Text(text2.getX(), text2.getY() + 20, "4 - Pink, 5 - Magenta, 6 - Red");
        Text text4 = new Text(text3.getX(), text3.getY() + 20, "7 - Blue, 8 - Black, 9 - White");
        Text text5 = new Text(text4.getX(), text4.getY() + 40, "R - Recenter pointer, F - Fill, M - Maze");
        Text text6 = new Text(text5.getX(), text5.getY() + 40, "B - Increase slot, N - Decrease slot, P - Exit");
        text7 = new Text(text6.getX(), text6.getY() + 20, "Save slot: " + slot);
        message = new Text(text7.getX(), text7.getY() + 40, "");

        //Initial draw
        for (Position position : grid) {
            grid.getCellInPosition(position).initCell();
        }

        instructions.draw();
        text.draw();
        text1.draw();
        text2.draw();
        text3.draw();
        text4.draw();
        text5.draw();
        text6.draw();
        text7.draw();

        pointer.draw();

        saver = new Saver();

        ready = true;
    }

    void start() throws InterruptedException {

        //Fake loading looks fancier
        while (!ready) {
            warning.setText("Loading.");
            warning.draw();
            Thread.sleep(200);
            warning.setText("Loading..");
            warning.draw();
            Thread.sleep(200);
            warning.setText("Loading...");
            warning.draw();
            Thread.sleep(200);
            warning.setText("");
            warning.draw();
            init();
        }
        keyboardSetup();
    }

    //----------------------------------------------------------------------------------KEYBOARD SETUP------------------
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
                message.setText("");
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
        text7.delete();
        slot = slot + value;

        if (slot < 0) {
            slot = 0;
        }

        if (slot > 3) {
            slot = 3;
        }

        text7.setText("Save slot: " + slot);
        text7.draw();
    }
}
