package gui;

import classes.Note;
import classes.NoteSource;
import midi.MySequencer;
import classes.SequenceFactory;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import static logger.CRBLogger.log;

public class EarTrainer extends JFrame implements ActionListener {
    private JPanel panelNorth;
    private JPanel panelSouth;
    private JPanel panelWest;
    private JButton button_c;
    private JButton button_d;
    private JButton button_e;
    private JButton button_f;
    private JButton button_g;
    private JButton button_a;
    private JButton button_b;
    private JButton button_c1;
    private JTextField textField;
    private MySequencer mySequencer = new MySequencer();
    private Sequence currentMelody;
    private JButton buttonPlay;
    private JButton buttonRepeat;

    public EarTrainer(){
        log("...EarTrainer()");
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initPanelWest();
        initPanelNorth();
        initPanelSouth();

        setVisible(true);
    }

    private void initPanelNorth() {
        log("...initPanelNorth()");
        panelNorth = new JPanel();
        panelNorth.setBackground(Color.BLUE);
        panelNorth.setPreferredSize(new Dimension(300, 100));
        buttonPlay = new JButton("play");
        buttonPlay.addActionListener(e-> {
            try {
                playMelody();
            } catch (InvalidMidiDataException ex) {
                ex.printStackTrace();
            }
        });
        buttonRepeat = new JButton("repeat");
        buttonRepeat.addActionListener(a->repeatMelody());
        textField = new JTextField(40);
        textField.setText("hello");
        panelNorth.add(buttonPlay);
        panelNorth.add(buttonRepeat);
        panelNorth.add(textField);
        add(panelNorth, BorderLayout.NORTH);
    }
    private void initPanelSouth(){
        log("EarTrainer.initPanelSouth()");
        panelSouth = new JPanel();
        panelSouth.setBackground(Color.DARK_GRAY);
        panelSouth.setPreferredSize(new Dimension(200, 230));
        panelSouth.setLayout(new GridLayout(1,8));
        button_c = new JButton("C");
        button_c.addActionListener(this);
        button_d = new JButton("D");
        button_d.addActionListener(this);
        button_e = new JButton("E");
        button_e.addActionListener(this);
        button_f = new JButton("F");
        button_f.addActionListener(this);
        button_g = new JButton("G");
        button_g.addActionListener(this);
        button_a = new JButton("A");
        button_a.addActionListener(this);
        button_b = new JButton("B");
        button_b.addActionListener(this);
        button_c1 = new JButton("C");
        button_c1.addActionListener(this);

        panelSouth.add(button_c);
        panelSouth.add(button_d);
        panelSouth.add(button_e);
        panelSouth.add(button_f);
        panelSouth.add(button_g);
        panelSouth.add(button_a);
        panelSouth.add(button_b);
        panelSouth.add(button_c1);
        add(panelSouth, BorderLayout.SOUTH);
    }

    private void initPanelWest(){
        log("...initPanelWest()");
        panelWest = new JPanel();
        panelWest.setPreferredSize(new Dimension(100, 200));
        panelWest.setBackground(Color.RED);
        add(panelWest, BorderLayout.CENTER);

    }


    private void playMelody() throws InvalidMidiDataException {
        log("...playMelody()");
        NoteSource source = new NoteSource();
        List<Integer> melody= source.getMelody(4);
        textField.setText(Arrays.toString(melody.toArray()));
        currentMelody = SequenceFactory.getSequence(melody);
        mySequencer.playSequence(currentMelody);
    }

    private void repeatMelody() {
        log("EarTrainer.repeatMelody()");
        mySequencer.playSequence(currentMelody);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log("EarTrainer.actionPerformed(ActionEvent)");
        Object key = e.getSource();
        if( key == button_c){
            System.out.println("C");
            mySequencer.playNote(Note.C);
        }else if( key == button_d){
            System.out.println("D");
            mySequencer.playNote(Note.D);
        }else if(key == button_e){
            System.out.println("E");
            mySequencer.playNote(Note.E);
        }else if(key == button_f){
            System.out.println("F");
            mySequencer.playNote(Note.F);
        }else if(key == button_g){
            System.out.println("G");
            mySequencer.playNote(Note.G);
        }else if(key == button_a) {
            System.out.println("A");
            mySequencer.playNote(Note.A);
        }else if(key == button_b){
            System.out.println("B");
            mySequencer.playNote(Note.B);
        }else if(key == button_c1){
            System.out.println("C1");
            mySequencer.playNote(Note.C1);
        }
    }
}
