package midi;

import ui.DrawAble;

import java.awt.*;

public class DrawableNote implements DrawAble {
    private String note;
    private int x, y;
    public DrawableNote(int note, int x){
        this.x = x;
        this.note = MidiUtil.noteNumToChar(note);
    }
    @Override
    public void update() {
        this.y+= 40;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        graphics.drawString(this.note, x, y );
    }

    @Override
    public boolean isInside(int x, int y) {
        return false;
    }

    @Override
    public void setPosition(int x, int y) {

    }
}
