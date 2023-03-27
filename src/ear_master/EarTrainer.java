package ear_master;

import classes.Melody;
import classes.MelodyFactory;
import classes.Note;
import lib.MidiLogger;
import midi.MySequencer;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

import static logger.CRBLogger.log;

public class EarTrainer extends JFrame implements ActionListener, MouseListener {
    //menu
    private TheGame game;
    private JMenuBar menuBar;
    private JSlider tempoSlider;
    private JMenu menuActions;
    private JMenuItem menuItem_notes;
    private JPanel panelNorth;
    private JPanel panelSouth;
    //panel west
    private JPanel panelWest;
    private JButton buttonGenerateMelody;
    private JTextField textField_lengthOfMelody;
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
    //private Sequence currentMelody;
    private JButton buttonPlay;
    private JButton buttonRepeat;
    private List<Integer> notes;
    private int length_of_melody;
    private Melody melody;
    private enum Mode{
        PENDING, CHOOSE_NOTES, GAME_ON
    }
    private Mode mode = Mode.PENDING;

    public EarTrainer(){
        log("...EarTrainer()");
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("ear master");

        //melodyFactory = new MelodyFactory();
        initDevStuff();
        initMenuBar();
        initPanelWest();//sort of center...
        initPanelNorth();
        initPanelSouth();

        setVisible(true);
    }

    private void createMelody() {
        log("EarTrainer.createMelody()");
        try {
            Melody melody = MelodyFactory.getMelody(length_of_melody, notes);
            MidiLogger.log(melody);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "error creating melody");
        }
    }
    /**
     * basically i'm hardcoding stuff here, stuff which at a later stage will be handed over to the user to set
     */
    private void initDevStuff() {
        log("EarTrainer.initDevStuff()");
        notes = Arrays.asList(63, 65, 67);
        length_of_melody = 5;
    }

    private void initMenuBar() {
        log("EarTrainer.initMenuBar()");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("whatever");
        JMenuItem menuItem_newMelody = new JMenuItem("new melody");
        menuItem_newMelody.addActionListener(e -> createMelody());
        menuBar.add(menu);
        menu.add(menuItem_newMelody);
        setJMenuBar(menuBar);
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
        button_c.addMouseListener(this);

        button_d = new JButton("D");
        button_d.addMouseListener(this);

        button_e = new JButton("E");
        button_e.addMouseListener(this);

        button_f = new JButton("F");
        button_f.addMouseListener(this);

        button_g = new JButton("G");
        button_g.addMouseListener(this);

        button_a = new JButton("A");
        button_a.addMouseListener(this);

        button_b = new JButton("B");
        button_b.addMouseListener(this);

        button_c1 = new JButton("C");
        button_c1.addMouseListener(this);

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
        buttonGenerateMelody = new JButton("generate melody");
        buttonGenerateMelody.addActionListener(a->generateMelody());
        textField_lengthOfMelody = new JTextField(5);
        panelWest.add(buttonGenerateMelody);
        panelWest.add(textField_lengthOfMelody);
        initTempoSlider();
        panelWest.add(tempoSlider);
        add(panelWest, BorderLayout.CENTER);
    }

    private void initTempoSlider(){
        log("EarTrainer.initTempoSlider()");
        tempoSlider = new JSlider();
        tempoSlider.setPaintTicks(true);
        tempoSlider.setMinorTickSpacing(5);
        tempoSlider.setPaintTrack(true);
        tempoSlider.setMajorTickSpacing(10);
        tempoSlider.setPaintLabels(true);
        tempoSlider.setPreferredSize(new Dimension(400, 40));
        tempoSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //tempo_label.setText(String.valueOf(tempoSlider.getValue()));
                int tempo = tempoSlider.getValue();
                mySequencer.setTempoBPM(tempo);
                log("tempo", tempo);
                //metronome.setTempo(tempoSlider.getValue());
            }
        });
    }

    private void generateMelody() {
        log("EarTrainer.generateMelody()");
        List<Integer> notes = Arrays.asList(61, 63, 65, 66);
        if( textField_lengthOfMelody.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "number of notes please");
            return;
        }
        int length_of_melody = Integer.parseInt(textField_lengthOfMelody.getText());
        try {
            melody = MelodyFactory.getMelody(length_of_melody,notes );
            MidiLogger.log(melody);
            textField.setText(Arrays.toString(melody.getNotes().toArray()));
            mode = Mode.GAME_ON;
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "error generating melody");
        }
    }


    private void playMelody() throws InvalidMidiDataException {
        log("...playMelody()");
        if(!mode.equals(Mode.GAME_ON)){
            System.out.println("not GAME_ON");
            return;
        }
        mySequencer.playSequence(melody.getSequence());
    }

    private void repeatMelody() {
        log("EarTrainer.repeatMelody()");
        mySequencer.playSequence(melody.getSequence());
    }

    /**
     * use this one for "game" stuff
     * like user input of notes
     * and to check melody against user input
     * thereby not having loads of ifs and buts in the play section...
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        log("EarTrainer.actionPerformed(ActionEvent)");
        Object key = e.getSource();
        if( mode.equals(Mode.CHOOSE_NOTES)) {
            if (key == button_c) {
                if( !game.checkNote(Note.C)){
                    log("error not c");
                }else{
                    log("correct note is c");
                }
            } else if (key == button_d) {
                game.checkNote(Note.D);
            } else if (key == button_e) {
                System.out.println("E");
                game.checkNote(Note.E);
            } else if (key == button_f) {
                System.out.println("F");
                game.checkNote(Note.F);
            } else if (key == button_g) {
                System.out.println("G");
                game.checkNote(Note.G);
            } else if (key == button_a) {
                System.out.println("A");
                game.checkNote(Note.A);
            } else if (key == button_b) {
                System.out.println("B");
                game.checkNote(Note.B);
            } else if (key == button_c1) {
                System.out.println("C1");
                game.checkNote(Note.C1);
            }
        }else if( mode.equals(Mode.GAME_ON)){
            System.out.println("not implemented");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
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

    @Override
    public void mouseReleased(MouseEvent e) {
        Object key = e.getSource();
        if( key == button_c){
            mySequencer.noteOff(Note.C);
        }else if( key == button_d){
            mySequencer.noteOff(Note.D);
        }else if(key == button_e){
            mySequencer.noteOff(Note.E);
        }else if(key == button_f){
            mySequencer.noteOff(Note.F);
        }else if(key == button_g){
            mySequencer.noteOff(Note.G);
        }else if(key == button_a) {
            mySequencer.noteOff(Note.A);
        }else if(key == button_b){
            mySequencer.noteOff(Note.B);
        }else if(key == button_c1){
            mySequencer.noteOff(Note.C1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
