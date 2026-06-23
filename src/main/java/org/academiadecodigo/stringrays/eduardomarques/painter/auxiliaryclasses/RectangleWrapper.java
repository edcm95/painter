package org.academiadecodigo.stringrays.eduardomarques.painter.auxiliaryclasses;

import com.codeforall.simplegraphics.graphics.Rectangle;
import java.io.Serializable;

public class RectangleWrapper extends Rectangle implements Serializable {

    public RectangleWrapper(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}
