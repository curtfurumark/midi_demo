package gui;

import lib.MidiLogger;
import midi.MidiPlayer;
import classes.Note;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static lib.MidiLogger.log;
import static classes.Note.*;

public class Keyboard extends JFrame implements KeyListener , ActionListener, MouseListener {
    private JPanel panelSouth;
    private JPanel panelNorth;
    private JComboBox<Instrument> comboBox_instruments;
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
        setTitle("keyboard hoping to grow up to be an eartrainer");
        setLayout(new FlowLayout());
        initInstrumentsPanel(); //north
        initKeyboard();//south
        setFocusable(true);
        addKeyListener(this );
        setVisible(true);
    }

    private void initInstrumentsPanel() {
        log("Keyboard.initInstrumentsPanel()...or north if you insist");
        panelNorth = new JPanel();
        panelNorth.setBackground(Color.GREEN);
        try {
            //Instrument[] instruments = player.getLoadedInstruments();
            //MidiLogger.logInstruments(instruments);
            comboBox_instruments = new JComboBox<>(player.getLoadedInstruments());
            comboBox_instruments.addActionListener(e->onChangeInstrument());
            panelNorth.add(comboBox_instruments);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "error listing loaded instruments");
        }

        this.add(panelNorth, BorderLayout.NORTH);

    }

    private void initKeyboard() {
        log("Keyboard.initKeyboard()");
        panelSouth = new JPanel();
        panelSouth.setLayout(new FlowLayout());

        button_c.setPreferredSize(new Dimension(100, 300));
        //button_c.addActionListener(this);
        button_c.addMouseListener(this);
        panelSouth.add(button_c);
        //add(button_c);

        button_d.setPreferredSize(new Dimension(100, 300));
        //button_d.addActionListener(this);
        button_d.addMouseListener(this);
        panelSouth.add(button_d);
        //add(button_d);

        button_e.setPreferredSize(new Dimension(100, 300));
        //button_e.addActionListener(this);
        button_e.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                log("...mousePressed() E");
                player.playNote(E);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                log("...mouseReleased() e");
                player.noteOff(E);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        panelSouth.add(button_e);

        button_f.setPreferredSize(new Dimension(100, 300));
        //button_f.addActionListener(this);
        button_f.addMouseListener(this);
        panelSouth.add(button_f);

        button_g.setPreferredSize(new Dimension(100, 300));
        //button_g.addActionListener(this);
        button_g.addMouseListener(this);
        panelSouth.add(button_g);

        button_a.setPreferredSize(new Dimension(100, 300));
        //button_a.addActionListener(this);
        button_a.addMouseListener(this);
        panelSouth.add(button_a);

        button_b.setPreferredSize(new Dimension(100, 300));
        //button_b.addActionListener(this);
        button_b.addMouseListener(this);
        panelSouth.add(button_b);

        button_c1.setPreferredSize(new Dimension(100, 300));
        //button_c1.addActionListener(this);
        button_c1.addMouseListener(this);
        panelSouth.add(button_c1);
        this.add(panelSouth,  BorderLayout.SOUTH);
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
            case KeyEvent.VK_H:
                playnote(G);
                break;
            case KeyEvent.VK_J:
                playnote(A);
                break;
            case KeyEvent.VK_K:
                playnote(B);
                break;
            case KeyEvent.VK_L:
                playnote(C1);
                break;
        }
        requestFocusInWindow();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        log("keyPressed(KeyEvent)");
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                noteOff(C);
                break;
            case KeyEvent.VK_S:
                noteOff(D);
                break;
            case KeyEvent.VK_D:
                noteOff(E);
                break;
            case KeyEvent.VK_F:
                noteOff(F);
                break;
            case KeyEvent.VK_H:
                noteOff(G);
                break;
            case KeyEvent.VK_J:
                noteOff(A);
                break;
            case KeyEvent.VK_K:
                noteOff(B);
                break;
            case KeyEvent.VK_L:
                noteOff(C1);
                break;
        }
        requestFocusInWindow();
    }

    private void noteOff(int note) {
        player.noteOff(note);
    }

    /**
     * listener of instruments combobox
     */
    private void onChangeInstrument(){
        log("Keyboard.onChangeInstrument()");
        int instrument = comboBox_instruments.getSelectedIndex();
        ShortMessage message = new ShortMessage();
        try {
            message.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
            player.send(message);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log("actionPerformed()");
        if( e.getSource() == button_c){
            playnote(Note.C);
        }else if( e.getSource() == button_d){
            playnote(Note.D);
        }else if(e.getSource()== button_e){
            playnote(E);
        }
        else if(e.getSource()== button_f){
            playnote(F);
        }
        else if(e.getSource()== button_g){
            playnote(G);
        }
        else if(e.getSource()== button_a){
            playnote(A);
        }
        else if(e.getSource()== button_b){
            playnote(B);
        }else if(e.getSource()== button_c1){
            playnote(C1);
        }
        requestFocusInWindow();
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

        if( e.getSource() == button_c){
            playnote(Note.C);
        }else if( e.getSource() == button_d){
            playnote(Note.D);
        }else if(e.getSource()== button_e){
            playnote(E);
        }
        else if(e.getSource()== button_f){
            playnote(F);
        }
        else if(e.getSource()== button_g){
            playnote(G);
        }
        else if(e.getSource()== button_a){
            playnote(A);
        }
        else if(e.getSource()== button_b){
            playnote(B);
        }else if(e.getSource()== button_c1){
            playnote(C1);
        }
        requestFocusInWindow();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == button_c) {
            noteOff(Note.C);
        } else if (e.getSource() == button_d) {
            noteOff(Note.D);
        } else if (e.getSource() == button_e) {
            noteOff(E);
        } else if (e.getSource() == button_f) {
            noteOff(F);
        } else if (e.getSource() == button_g) {
            noteOff(G);
        } else if (e.getSource() == button_a) {
            noteOff(A);
        } else if (e.getSource() == button_b) {
            noteOff(B);
        } else if (e.getSource() == button_c1) {
            noteOff(C1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
