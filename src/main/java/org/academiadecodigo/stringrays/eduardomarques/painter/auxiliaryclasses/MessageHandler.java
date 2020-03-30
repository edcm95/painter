package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import org.academiadecodigo.stringrays.eduardomarques.painter.Painter;
import org.academiadecodigo.stringrays.eduardomarques.painter.algorithms.AlgorithmName;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;

import java.util.HashMap;

public class MessageHandler {

    private Rectangle board;
    private Painter painter;
    private HashMap<String, Text> textMap;

    public MessageHandler(Rectangle board, Painter painter) {
        this.textMap = new HashMap<>();
        this.board = board;
        this.painter = painter;
        init();
    }

    private void init() {
        //Make instructions
        textMap.put("0" ,new Text(board.getWidth() + board.getX() + 10, board.getY(), "Instructions:"));
        textMap.put("1", new Text(textMap.get("0").getX(), textMap.get("0").getY() + 40, "Movement: VIm, P - Exit"));
        textMap.put("2", new Text(textMap.get("1").getX(), textMap.get("1").getY() + 20, "R - Recenter pointer, C - Clear"));
        textMap.put("3", new Text(textMap.get("2").getX(), textMap.get("2").getY() + 40, "1 - Cyan, 2 - Yellow, 3 - Green"));
        textMap.put("4", new Text(textMap.get("3").getX(), textMap.get("3").getY() + 20, "4 - Pink, 5 - Magenta, 6 - Red"));
        textMap.put("5", new Text(textMap.get("4").getX(), textMap.get("4").getY() + 20, "7 - Blue, 8 - Black, 9 - White"));
        textMap.put("6", new Text(textMap.get("5").getX(), textMap.get("5").getY() + 40, "N - Cycle algorithm, E - Execute algorithm"));
        textMap.put("7", new Text(textMap.get("6").getX(), textMap.get("6").getY() + 20, "B - Cycle save slot, S - Save, X - Load"));
        textMap.put("saveSlot", new Text(textMap.get("7").getX(), textMap.get("7").getY() + 40, "Save slot: " + painter.getSaveSlot()));
        textMap.put("algorithmName", new Text(textMap.get("saveSlot").getX(), textMap.get("saveSlot").getY() + 20, "Algorithm: " + painter.getCurrentAlgorithm()));

        //Draw
        for(Text value : textMap.values()) {
            value.draw();
        }
    }

    public void updateSaveSlot() {
        textMap.get("saveSlot").delete();
        textMap.get("saveSlot").setText("Save slot: " + painter.getSaveSlot());
        textMap.get("saveSlot").draw();
    }

    public void updateAlgorithm(AlgorithmName algorithm) {
        textMap.get("algorithmName").delete();
        textMap.get("algorithmName").setText("Algorithm: " + algorithm);
        textMap.get("algorithmName").draw();
    }
}
