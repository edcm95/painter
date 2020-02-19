package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Saver {

    private FileOutputStream outputStream;
    private FileInputStream inputStream;

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
            for (Position position : map.keySet()) {
                bOut.write(decomposeCell(map.get(position)));
            }
            bOut.flush();

        } catch (IOException e) {
            System.out.println("SAVER: Something went wrong writing file.");

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
            for (Position position : map.keySet()) {
                byte[] bytes = bIn.readNBytes(2);
                map.get(position).writeCell(bytes);
            }

        } catch (IOException e) {
            System.out.println("SAVER: Something went wrong loading the data.");

        } finally {
            System.out.println("Operation took: " + (System.currentTimeMillis() - timeStamp));
            closeStreams();
        }
    }

    private byte[] decomposeCell(Cell cell) {

        byte[] bytes = new byte[2];

        //---------IS PAINTED?
        if (cell.isPainted()) {
            bytes[0] = 1;
        }

        //------------COLORS
        if (cell.getColor() == Color.CYAN) {
            bytes[1] = 1;
        }

        if (cell.getColor() == Color.YELLOW) {
            bytes[1] = 2;
        }

        if (cell.getColor() == Color.PINK) {
            bytes[1] = 3;
        }

        if (cell.getColor() == Color.GREEN) {
            bytes[1] = 4;
        }

        if (cell.getColor() == Color.MAGENTA) {
            bytes[1] = 5;
        }

        if (cell.getColor() == Color.RED) {
            bytes[1] = 6;
        }

        if (cell.getColor() == Color.BLUE) {
            bytes[1] = 7;
        }

        if (cell.getColor() == Color.BLACK) {
            bytes[1] = 8;
        }

        if (cell.getColor() == Color.WHITE) {
            bytes[1] = 9;
        }
        return bytes;
    }

    private void initOutput() {
        try {
            outputStream = new FileOutputStream(filepath);
            bOut = new BufferedOutputStream(outputStream);

        } catch (IOException e) {
            System.out.println("Something went wrong opening output.");
        }
    }

    private void initInput() {
        try {
            inputStream = new FileInputStream(filepath);
            bIn = new BufferedInputStream(inputStream);

        } catch (IOException e) {
            System.out.println("SAVER: Something went wrong opening input.");
        }
    }

    private void closeStreams() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            System.out.println("SAVER: Some went wrong closing streams.");
        }
    }

    public void saveSlot(int value) {
        filepath = "save/save-file" + value;
    }
}
