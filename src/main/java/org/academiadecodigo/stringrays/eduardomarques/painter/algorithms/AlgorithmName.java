package org.academiadecodigo.stringrays.eduardomarques.painter.algorithms;

public enum AlgorithmName {
    FILL("Fill"),
    MAZE("Maze"),
    LANGTON_ANT("Langton's Ant"),
    LANGTON_SYMMETRICAL("Langton's Ant Brain "),
    LANGTON_SQUARE("Langton's Ant Square"),
    LANGTON_CHAOS("Langton's Ant Chaos"),
    LANGTON_TRIANGLE("Langton's Ant Spiderman"),
    LANGTON_CONVOLUTED("Langton's Ant Convoluted"),
    GAME_OF_LIFE("Conway's Game of Life"),
    COVID_19("SARS-Cov2");

    private String name;

    AlgorithmName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
