package org.academiadecodigo.gridpaint.auxiliaryclasses.algorithms;

public enum AlgorithmName {
    FILL("Fill"),
    MAZE("Maze"),
    LANGTON_ANT("Langton's ant");

    private String name;

    AlgorithmName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
