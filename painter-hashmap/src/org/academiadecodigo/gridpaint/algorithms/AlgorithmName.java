package org.academiadecodigo.gridpaint.algorithms;

public enum AlgorithmName {
    FILL("Fill"),
    MAZE("Maze"),
    LANGTON_ANT("Langton's ant"),
    LANGTON_SYMMETRICAL("Langton's Ant LLRR"),
    LANGTON_SQUARE("Langton's Ant LRRRRRLLR"),
    LANGTON_CHAOS("Langton's Ant RLR"),
    LANGTON_TRIANGLE("Langton's Ant RRLLLRLLLRRR"),
    LANGTON_CONVOLUTED("Langton's Ant LLRRRLRLRLLR");

    private String name;

    AlgorithmName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
