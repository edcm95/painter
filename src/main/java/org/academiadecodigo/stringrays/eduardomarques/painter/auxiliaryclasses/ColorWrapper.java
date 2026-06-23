package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import com.codeforall.simplegraphics.graphics.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ColorWrapper implements Serializable {

    public static final ColorWrapper RED = wrap(new Color(255, 0, 0));
    public static final ColorWrapper GREEN = wrap(new Color(0, 255, 0));
    public static final ColorWrapper BLUE = wrap(new Color(0, 0, 255));
    public static final ColorWrapper WHITE = wrap(new Color(255, 255, 255));
    public static final ColorWrapper LIGHT_GRAY = wrap(new Color(192, 192, 192));
    public static final ColorWrapper GRAY = wrap(new Color(128, 128, 128));
    public static final ColorWrapper DARK_GRAY = wrap(new Color(64, 64, 64));
    public static final ColorWrapper BLACK = wrap(new Color(0, 0, 0));
    public static final ColorWrapper CYAN = wrap(new Color(0, 255, 255));
    public static final ColorWrapper MAGENTA = wrap(new Color(255, 0, 255));
    public static final ColorWrapper YELLOW = wrap(new Color(255, 255, 0));
    public static final ColorWrapper PINK = wrap(new Color(255, 175, 175));
    public static final ColorWrapper ORANGE = wrap(new Color(255, 200, 0));

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

    public int getRed() {
        return this.wrapped.getRed();
    }

    public int getGreen() {
        return this.wrapped.getGreen();
    }

    public int getBlue() {
        return this.wrapped.getBlue();
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
