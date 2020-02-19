package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.exceptions.CellColorException;
import org.academiadecodigo.stringrays.eduardomarques.painter.config.Constants;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Saver {

    private BufferedOutputStream bOut;
    private BufferedInputStream bIn;
    private String filepath;

    public Saver() {
        filepath = "save/save-file";
    }

    public void saveData(HashMap<Position, Cell> map) {
        long timeStamp = System.currentTimeMillis();
        /*

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filepath), map);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            System.out.println("Saving process finished, took " + (System.currentTimeMillis() - timeStamp) + " ms.");
        }
        */


        initOutput();

        try {
            for (Cell cell : map.values()) {
                bOut.write(decomposeCell(cell));
            }
            bOut.flush();

        } catch (IOException e) {
            System.out.println("SAVER: Something went wrong writing file.");

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            closeStreams();
        }

        System.out.println("SAVER: Finished saving to file, took " + (System.currentTimeMillis() - timeStamp) + " ms.");
    }

    public void loadData(Map<Position, Cell> map) {
        long timeStamp = System.currentTimeMillis();

        /*
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<Position, Cell> fetched = null;

        TypeReference<HashMap<Position, Cell>> typeReference = new TypeReference<HashMap<Position, Cell>>() {
        };

        try {
            fetched = objectMapper.readValue(new File(filepath), typeReference);
            grid.loadMap(fetched);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            System.out.println("Loading process finished, took " + (System.currentTimeMillis() - timeStamp) + " ms.");
        }
        */

        initInput();

        try {
            for (Cell cell : map.values()) {
                byte value = bIn.readNBytes(1)[0];
                cell.writeCell(value);
            }

        } catch (IOException | CellColorException e) {
            System.out.println("SAVER: Something went wrong loading the data. ");
            System.out.println(e.getMessage());

        } finally {
            System.out.println("Operation took: " + (System.currentTimeMillis() - timeStamp));
            closeStreams();
        }
    }

    private byte decomposeCell(Cell cell) throws Exception {

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
