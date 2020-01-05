package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int dx;
    private int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDeltaX(){
        return dx;
    }

    public int getDeltaY(){
        return dy;
    }
}
