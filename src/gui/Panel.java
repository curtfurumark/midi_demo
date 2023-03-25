package gui;

import classes.SequenceFactory;
import lib.Constants;
import midi.*;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * not sure how i envisioned it, so i am abandoning it for the time being
 * use MidiPanel instead
 */
public class Panel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    private MidiRect midiRect;
    private Button button_start;
    private Button getButton_stop;
    private Playground playground;
    private DrawableReceiver drawableReceiver;
    private MySequencer mySequencer;
    private List<DrawAble> drawAbles = new ArrayList<>();
    private int note = 63;
    private Point prevPoint;
    private Point currPoint;
    private Drums drums;
    public Panel(){
        System.out.println("Panel() ctor");
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        midiRect = new MidiRect("receiver",100, 100, 200, 100);
        drawAbles.add(midiRect);
        //midiRect.draw(getGraphics());
        //playground = new Playground();
        //TestMyReceiver myReceiver = new TestMyReceiver();
        //seqDrums();
        //mySequencer();
        playFile();

    }

    private void playFile() {
        mySequencer = new MySequencer();
        try {
            mySequencer.playFile("res/zarathustra.mid");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    private void seqDrums(){
        drums = new Drums();
        drawableReceiver = drums.getMyReceiver();
        drawableReceiver.setPanel(this);
    }
    private void mySequencer(){
        System.out.println("Panel.mySequencer()");
        mySequencer = new MySequencer();
        try {
            mySequencer.setSequence(SequenceFactory.createChromaticSequence());
            mySequencer.setTempoBPM(30);
            drawableReceiver = mySequencer.getMyReceiver();
            drawableReceiver.setPanel(this);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        //draw(getGraphics())
    }

    public void update(){

    }
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }
    public void draw(Graphics graphics){
        //midiRect.draw(graphics);
        if( drawableReceiver != null) {
            drawableReceiver.draw(graphics);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped()");

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_P:
                playground.playNote(note++);
                break;
            case KeyEvent.VK_X:
                playground.changeProgram();
                break;
            case KeyEvent.VK_W:
                playground.whatever();
                break;
            case KeyEvent.VK_S:
                //playground.testSynthesizer();
                //drums.start();
                if ( mySequencer.isPlaying()){
                    mySequencer.stop();
                }else {
                    mySequencer.play();
                }
                //playground.synthesizerChannels2();
                break;
            case KeyEvent.VK_T:
                break;
            case KeyEvent.VK_0:
                playground.synthesizerPlayNoteChannel(0);
                break;
            case KeyEvent.VK_1:
                playground.synthesizerPlayNoteChannel(1);
                break;
            case KeyEvent.VK_2:
                playground.synthesizerPlayNoteChannel(2);
                break;
            case KeyEvent.VK_3:
                playground.synthesizerPlayNoteChannel(3);
                break;



            case KeyEvent.VK_C:
            case KeyEvent.VK_D:
            case KeyEvent.VK_E:
            case KeyEvent.VK_F:
            case KeyEvent.VK_G:
            case KeyEvent.VK_A:
            case KeyEvent.VK_B:
                playground.playNote(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.format("mousePressed(%d, %d)", e.getX(), e.getY());
        prevPoint = new Point(e.getX(), e.getY());

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if( prevPoint== null){
            prevPoint = new Point(e.getX(), e.getY());
        }
        currPoint = new Point(e.getX(), e.getY());
        int x_offset = currPoint.x -prevPoint.x;
        int y_offset = currPoint.y - prevPoint.y;
        prevPoint = currPoint;
        System.out.format("drag offset or whatever (%d, %d)\n", x_offset, y_offset);
        System.out.format("\tmouseDragged(%d, %d) \n", x, y);
        if( midiRect.isInside(x, y)) {
            //midiRect.setPosition(x, y);
            midiRect.move(x_offset, y_offset);
            repaint();
        }else{
            System.out.println("\tnot inside");
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
