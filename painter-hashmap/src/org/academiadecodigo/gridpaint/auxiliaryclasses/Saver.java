package org.academiadecodigo.gridpaint.auxiliaryclasses;

import org.academiadecodigo.simplegraphics.graphics.Color;

import java.io.*;
import java.util.HashMap;


public class Saver {

    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private String filepath;

    public Saver() {
        filepath = "save/save-file";
    }

    public void saveData(HashMap<Position, Cell> map) {
        initOutput();

        try {
            for(Position position : map.keySet()) {
                outputStream.write(decomposeCell(map.get(position)));
            }


        } catch (IOException e) {
            System.out.println("SAVER: Something went wrong writing file.");

        } finally {
            closeStreams();
        }
        System.out.println("SAVER: Finished saving to file.");
    }

    public void loadData(HashMap<Position, Cell> map) {
        initInput();

        try {
            for (Position position : map.keySet()) {
                byte[] bytes = inputStream.readNBytes(2);
                map.get(position).writeCell(bytes);
            }

        } catch (IOException e) {
            System.out.println("SAVER: Something went wrong loading the data.");

        } finally {
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

        } catch (IOException e) {
            System.out.println("Something went wrong opening output.");
        }
    }

    private void initInput() {
        try {
            inputStream = new FileInputStream(filepath);

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
        switch (value) {
            case 3:
                filepath = "save/save-file3";
                break;
            case 2:
                filepath = "save/save-file2";
                break;
            case 1:
                filepath = "save/save-file1";
                break;
            default:
                filepath = "save/save-file";
                break;
        }
    }
}
