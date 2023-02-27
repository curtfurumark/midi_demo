package ui;

import java.awt.*;

public interface DrawAble {
    public void update();
    public void draw(Graphics graphics);
    public boolean isInside(int x, int y);
    public void setPosition(int x, int y);
}
