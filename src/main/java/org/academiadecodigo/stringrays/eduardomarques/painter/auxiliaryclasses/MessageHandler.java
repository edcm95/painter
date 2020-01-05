package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import org.academiadecodigo.stringrays.eduardomarques.painter.Painter;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.AlgorithmName;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;

public class MessageHandler {

    private Rectangle board;
    private Painter painter;
    private Text saveSlot;
    private Text algorithmName;

    public MessageHandler(Rectangle board, Painter painter) {
        this.board = board;
        this.painter = painter;
        init();
    }

    private void init() {
        //Make instructions
        Text text0 = new Text(board.getWidth() + board.getX() + 10, board.getY(), "Instructions:");
        Text text1 = new Text(text0.getX(), text0.getY() + 40, "Movement: VIm, P - Exit");
        Text text2 = new Text(text1.getX(), text1.getY() + 20, "R - Recenter pointer, C - Clear");
        Text text3 = new Text(text2.getX(), text2.getY() + 40, "1 - Cyan, 2 - Yellow, 3 - Green");
        Text text4 = new Text(text3.getX(), text3.getY() + 20, "4 - Pink, 5 - Magenta, 6 - Red");
        Text text5 = new Text(text4.getX(), text4.getY() + 20, "7 - Blue, 8 - Black, 9 - White");
        Text text6 = new Text(text5.getX(), text5.getY() + 40, "N - Cycle algorithm, E - Execute algorithm");
        Text text7 = new Text(text6.getX(), text6.getY() + 20, "B - Cycle save slot, S - Save, X - Load");
        saveSlot = new Text(text7.getX(), text7.getY() + 40, "Save slot: " + painter.getSaveSlot());
        algorithmName = new Text(saveSlot.getX(), saveSlot.getY() + 20, "Algorithm: " + painter.getCurrentAlgorithm());

        //Draw
        text0.draw();
        text1.draw();
        text2.draw();
        text3.draw();
        text4.draw();
        text5.draw();
        text6.draw();
        text7.draw();
        saveSlot.draw();
        algorithmName.draw();
    }

    public void updateSaveSlot() {
        saveSlot.delete();
        saveSlot.setText("Save slot: " + painter.getSaveSlot());
        saveSlot.draw();
    }

    public void updateAlgorithm(AlgorithmName algorithm) {
        algorithmName.delete();
        algorithmName.setText("Algorithm: " + algorithm);
        algorithmName.draw();
    }
}
