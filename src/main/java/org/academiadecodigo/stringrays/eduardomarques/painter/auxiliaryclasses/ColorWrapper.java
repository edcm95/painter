package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import com.codeforall.simplegraphics.graphics.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ColorWrapper implements Serializable {

    public static final ColorWrapper RED = wrap(Color.RED);
    public static final ColorWrapper GREEN = wrap(Color.GREEN);
    public static final ColorWrapper BLUE = wrap(Color.BLUE);
    public static final ColorWrapper WHITE = wrap(Color.WHITE);
    public static final ColorWrapper LIGHT_GRAY = wrap(Color.LIGHT_GRAY);
    public static final ColorWrapper GRAY = wrap(Color.GRAY);
    public static final ColorWrapper DARK_GRAY = wrap(Color.DARK_GRAY);
    public static final ColorWrapper BLACK = wrap(Color.BLACK);
    public static final ColorWrapper CYAN = wrap(Color.CYAN);
    public static final ColorWrapper MAGENTA = wrap(Color.MAGENTA);
    public static final ColorWrapper YELLOW = wrap(Color.YELLOW);
    public static final ColorWrapper PINK = wrap(Color.PINK);
    public static final ColorWrapper ORANGE = wrap(Color.ORANGE);

    private transient Color wrapped;

    public ColorWrapper(int red, int green, int blue) {
        this.wrapped = new Color(red, green, blue);
    }

    public ColorWrapper() {
        this.wrapped = Color.GRAY;
    }

    private static ColorWrapper wrap(Color color) {
        return new ColorWrapper(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Color unwrap() {
        return this.wrapped;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(wrapped.getRed());
        out.writeInt(wrapped.getGreen());
        out.writeInt(wrapped.getBlue());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        wrapped = new Color(in.readInt(), in.readInt(), in.readInt());
    }


}
