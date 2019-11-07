package org.academiadecodigo.gridpaint.auxiliaryclasses;

import org.academiadecodigo.gridpaint.Painter;
import org.academiadecodigo.gridpaint.Pointer;
import org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms.AlgorithmName;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class PainterKeyboard implements KeyboardHandler {

    private Pointer pointer;
    private Painter painter;

    public PainterKeyboard(Pointer pointer, Painter painter) {
        this.pointer = pointer;
        this.painter = painter;
    }

    public void keyboardSetup() {
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
                KeyboardEvent.KEY_Y,
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
                pointer.move(Direction.LEFT);
                break;

            case (KeyboardEvent.KEY_L):
                pointer.move(Direction.RIGHT);
                break;

            case (KeyboardEvent.KEY_K):
                pointer.move(Direction.DOWN);
                break;

            case (KeyboardEvent.KEY_J):
                pointer.move(Direction.UP);
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
                painter.load();
                break;

            case (KeyboardEvent.KEY_S):
                painter.save();
                break;

            case (KeyboardEvent.KEY_P):
                System.exit(0);
                break;

            case (KeyboardEvent.KEY_C):
                painter.clearGrid();
                break;

            case (KeyboardEvent.KEY_R):
                pointer.recenter();
                break;

            case (KeyboardEvent.KEY_B):
                painter.increaseSaveSlot();
                break;

            case (KeyboardEvent.KEY_N):
                painter.decreaseSaveSlot();
                break;

            case (KeyboardEvent.KEY_F):
                pointer.runAlgorithm(AlgorithmName.FILL);
                break;

            case (KeyboardEvent.KEY_M):
                pointer.runAlgorithm(AlgorithmName.MAZE);
                break;

            case (KeyboardEvent.KEY_Y):
                pointer.runAlgorithm(AlgorithmName.LANGTON_ANT);
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
}



