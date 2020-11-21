package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Cell;
import org.academiadecodigo.stringrays.eduardomarques.painter.entities.Position;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Saver {

    private ObjectOutputStream os;
    private ObjectInputStream is;
    private String filepath;

    public Saver() {
        filepath = "save/save-file";
    }

    public void saveData(Map<Position, Cell> map) {
        long timeStamp = System.currentTimeMillis();


        initOutput();
        try {

            os.writeObject(map);
            os.flush();

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
        System.out.println("Loading data.");
        long timeStamp = System.currentTimeMillis();

        initInput();

        try {
            HashMap<Position, Cell> loadedMap = (HashMap<Position, Cell>) is.readObject();
            loadedMap.keySet().parallelStream().forEachOrdered(key -> {
                if (!map.containsKey(key)) {
                    return;
                }

                map.get(key).copyState(loadedMap.get(key));
            });

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeStreams();
        }

        System.out.println("Operation took: " + (System.currentTimeMillis() - timeStamp));
    }

    private void initOutput() {
        try {
            os = new ObjectOutputStream(new FileOutputStream(filepath));

        } catch (IOException e) {
            System.out.println("Something went wrong opening output.");
        }
    }

    private void initInput() {
        try {
            is = new ObjectInputStream(new FileInputStream(filepath));

        } catch (IOException e) {
            System.out.println("SAVER: Something went wrong opening input.");
        }
    }

    private void closeStreams() {
        try {
            if (os != null) {
                os.close();
            }

            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            System.out.println("SAVER: Some went wrong closing streams.");
        }
    }

    public void saveSlot(int value) {
        filepath = "save/save-file" + value;
    }
}
