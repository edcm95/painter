package org.academiadecodigo.gridpaint;

import java.util.Objects;

public class Position {

    private double X;
    private double Y;
    private Position origin;

    public Position(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public Position(Position position) {
        this.X = position.getX();
        this.Y = position.getY();
        this.origin = position;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void translate(double deltaX, double deltaY) {
        this.X = X + deltaX;
        this.Y = Y + deltaY;
    }

    public void setPosition(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public Position getOrigin(){
        return origin;
    }

    //InteliJ FTW!!

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(position.getX(), getX()) == 0 &&
                Double.compare(position.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
