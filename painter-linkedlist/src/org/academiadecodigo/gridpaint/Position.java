package org.academiadecodigo.gridpaint;

public class Position {

    private double X;
    private double Y;

    public Position(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public Position(Position position) {
        this.X = position.getX();
        this.Y = position.getY();
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }


    public boolean isSameAs(Position position) {
        return X == position.getX() && Y == position.getY();
    }

    public void translate(double deltaX, double deltaY) {
        this.X = X + deltaX;
        this.Y = Y + deltaY;
    }

    public void setPosition(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }
}
