package org.academiadecodigo.stringrays.eduardomarques.painter.entities;

import org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses.Direction;
import org.academiadecodigo.stringrays.eduardomarques.painter.config.Constants;

import java.util.Objects;

public class Position {

    private double X;
    private double Y;
    private Position origin;

    public Position(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * @param position Constructor overload, allows for
     *                 position instanciation based on a previous instance
     */
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

    /**
     * Translates the position (moves it based on a direction)
     */
    public void translate(Direction direction) {
        this.X = X + (Constants.CELL_SIZE * direction.getDeltaX());
        this.Y = Y + (Constants.CELL_SIZE * direction.getDeltaY());
    }

    public void setPosition(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * @return the origin
     */
    public Position getOrigin() {
        return origin;
    }

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
