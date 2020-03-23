package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.exceptions.CellColorException;
import org.academiadecodigo.stringrays.eduardomarques.painter.config.Constants;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Saver {

    private BufferedOutputStream bOut;
    private BufferedInputStream bIn;
    private String filepath;

    public Saver() {
        filepath = "save/save-file";
    }

    public void saveData(HashMap<Position, Cell> map) {
        long timeStamp = System.currentTimeMillis();
        initOutput();

        try {

            map.values().parallelStream().forEachOrdered((cell) -> {
                try {
                    bOut.write(decomposeCell(cell));

                } catch (CellColorException | IOException e) {
                    e.printStackTrace();
                }

            });

            bOut.flush();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            closeStreams();
        }
        System.out.println("SAVER: Finished saving to file, took "
                + (System.currentTimeMillis() - timeStamp)
                + " ms.");
    }

    public void loadData(Map<Position, Cell> map) {
        long timeStamp = System.currentTimeMillis();
        initInput();

        try {

            map.values().parallelStream().forEachOrdered((cell) -> {
                byte value;
                try {
                    value = bIn.readNBytes(1)[0];
                    cell.writeCell(value);

                } catch (IOException | CellColorException e) {
                    System.out.println("SAVER: Something went wrong loading the data. \n" + e.getMessage());
                }
            });

        } finally {
            System.out.println("Operation took: " + (System.currentTimeMillis() - timeStamp));
            closeStreams();
        }
    }

    private byte decomposeCell(Cell cell) throws CellColorException {

        //---------IS PAINTED?
        if (!cell.isPainted()) {
            return 0;
        }

        //------------COLORS
        if (cell.getColor() == Color.CYAN) {
            return 1;
        }

        if (cell.getColor() == Color.YELLOW) {
            return 2;
        }

        if (cell.getColor() == Color.PINK) {
            return 3;
        }

        if (cell.getColor() == Color.GREEN) {
            return 4;
        }

        if (cell.getColor() == Color.MAGENTA) {
            return 5;
        }

        if (cell.getColor() == Color.RED) {
            return 6;
        }

        if (cell.getColor() == Color.BLUE) {
            return 7;
        }

        if (cell.getColor() == Color.BLACK) {
            return 8;
        }

        if (cell.getColor() == Color.WHITE) {
            return 9;
        }

        throw new CellColorException(Constants.ERR_COLOR_EXCEPTION);
    }

    private void initOutput() {
        try {
            bOut = new BufferedOutputStream(new FileOutputStream(filepath));

        } catch (IOException e) {
            System.out.println("Something went wrong opening output.");
        }
    }

    private void initInput() {
        try {
            bIn = new BufferedInputStream(new FileInputStream(filepath));

        } catch (IOException e) {
            System.out.println("SAVER: Something went wrong opening input.");
        }
    }

    private void closeStreams() {
        try {
            if (bOut != null) {
                bOut.close();
            }

            if (bIn != null) {
                bIn.close();
            }
        } catch (IOException e) {
            System.out.println("SAVER: Some went wrong closing streams.");
        }
    }

    public void saveSlot(int value) {
        filepath = "save/save-file" + value;
    }
}
