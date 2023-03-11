package midi;

import gui.DrawAble;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import gui.Panel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static logger.CRBLogger.log;

public class DrawableReceiver implements Receiver, DrawAble {
    private int x = 50, y = 300;
    private Panel panel;
    private boolean draw = false;
    private boolean VERBOSE = false;
    private List<DrawableNote> playedNotes = new ArrayList<>();
    private int note;
    public DrawableReceiver(){


    }
    @Override
    public void send(MidiMessage message, long timeStamp) {
        if( VERBOSE) log("MyReceiver.send()");
        //message.
        ShortMessage sm = (ShortMessage) message;
        note = sm.getData1();
        if( draw) {
            addNote(note);
        }

    }
    private synchronized void addNote(int note){
        if ( note < 127) {
            playedNotes.add(new DrawableNote(note, x += 40));
            update();
            //Debug.log((ShortMessage) message);
        }
    }

    @Override
    public void close() {

    }
    public void setDraw(boolean draw){
        this.draw = draw;
    }

    @Override
    public synchronized void update() {
        if ( panel != null) {
            //synchronized ()
            for( DrawableNote note: playedNotes){
                note.update();
            }
            panel.repaint();
        }else{
            System.out.println("Myreceiver.update(), panel is null");
        }
    }

    @Override
    public synchronized void draw(Graphics graphics) {
        if(VERBOSE)log("MyReceiver.draw()");
        for( DrawableNote note: playedNotes){
            note.draw(graphics);
        }
        /*
        graphics.setColor(Color.GREEN);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        graphics.drawString(MidiUtil.noteNumToChar(note), x, y );

         */
    }

    @Override
    public boolean isInside(int x, int y) {
        return false;
    }

    public void setPanel(gui.Panel panel){
        this.panel = panel;
    }
    @Override
    public void setPosition(int x, int y) {

    }
}
