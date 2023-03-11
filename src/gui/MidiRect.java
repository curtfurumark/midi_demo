package gui;

import java.awt.*;

public class MidiRect implements DrawAble{
    private String text;
    private int x, y, width, height;
    private int offset_x = 15, offset_y = 15;
    private Point origin;
    private Rectangle rectangle;

    public MidiRect(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        origin = new Point(x + width/2, y + height/2);
    }

    @Override
    public void draw(Graphics graphics) {
        System.out.println("MidiRect.draw(Graphics)");
        graphics.setColor(Color.GREEN);
        graphics.drawRect(x,y, width, height);
        graphics.drawString(text, x + offset_x, y + offset_y);
    }

    @Override
    public boolean isInside(int x, int y) {
        return new Rectangle(this.x, this.y, width, height).contains(x, y);
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

    }

    @Override
    public void update() {

    }

    public void move(int x, int y) {
        System.out.format("\tmove(%d, %d)\n", x, y);
        this.x += x;
        this.y += y;
    }
}
