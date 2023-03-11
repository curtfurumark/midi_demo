package gui;

import midi.MidiPlayer;
import midi.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static lib.MidiLogger.log;
import static midi.Note.*;

public class Keyboard extends JFrame implements KeyListener {
    private JButton button_c = new JButton("C");
    private JButton button_d = new JButton("D");
    private JButton button_e = new JButton("E");
    private JButton button_f = new JButton("F");
    private JButton button_g = new JButton("G");
    private JButton button_a = new JButton("A");
    private JButton button_b = new JButton("B");
    private JButton button_c1 = new JButton("C");
    private MidiPlayer player = new MidiPlayer();
    public Keyboard(){
        setSize(new Dimension(900, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout());
        initButtons();
        setFocusable(true);
        addKeyListener(this );
        setVisible(true);
    }

    private void initButtons() {
        log("Keyboard.initButtons()");
        button_c.setPreferredSize(new Dimension(100, 300));
        button_c.addActionListener(e -> playnote(Note.C));
        add(button_c);
        button_d.setPreferredSize(new Dimension(100, 300));
        button_d.addActionListener(e -> playnote(D));
        add(button_d);
        button_e.setPreferredSize(new Dimension(100, 300));
        button_e.addActionListener(e -> playnote(E));
        add(button_e);
        button_f.setPreferredSize(new Dimension(100, 300));
        button_f.addActionListener(e -> playnote(F));
        add(button_f);
        button_g.setPreferredSize(new Dimension(100, 300));
        button_g.addActionListener(e -> playnote(G));
        add(button_g);
        button_a.setPreferredSize(new Dimension(100, 300));
        button_a.addActionListener(e -> playnote(A));
        add(button_a);
        button_b.setPreferredSize(new Dimension(100, 300));
        button_b.addActionListener(e -> playnote(B));
        add(button_b);
        button_c1.setPreferredSize(new Dimension(100, 300));
        button_c1.addActionListener(e -> playnote(C1));
        add(button_c1);
    }
    private void playnote(int note){
        log("Keyboard.playNote()");
        player.playNote(note);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        log("keyTyped(KeyEvent)");
        log(e.toString());

    }

    @Override
    public void keyPressed(KeyEvent e) {
        log("keyPressed(KeyEvent)");
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                playnote(C);
                break;
            case KeyEvent.VK_S:
                playnote(D);
                break;
            case KeyEvent.VK_D:
                playnote(E);
                break;
            case KeyEvent.VK_F:
                playnote(F);
                break;
            case KeyEvent.VK_J:
                playnote(G);
                break;
            case KeyEvent.VK_K:
                playnote(A);
                break;
            case KeyEvent.VK_L:
                playnote(B);
                break;
            case KeyEvent.VK_O:
                playnote(C1);
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        log("keyReleased(KeyEvent e)");
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent){
            //char c = keyEvent.getKeyChar();
            log("key pressed");
            switch(keyEvent.getKeyCode()){
                case KeyEvent.VK_LEFT:

                    break;
                case KeyEvent.VK_RIGHT:
                    break;
                case KeyEvent.VK_DOWN:

                    break;
                case KeyEvent.VK_SPACE:

                    break;
            }

        }
    }
}
