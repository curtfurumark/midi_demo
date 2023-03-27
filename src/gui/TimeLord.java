package gui;

import midi.Metronome;
import classes.SequenceFactory;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

import static logger.CRBLogger.log;

public class TimeLord extends JFrame implements Metronome.Callback{
    private JButton button = new JButton("start");
    private JComboBox comboBox_sequences;
    private JSlider sliderTempo;
    private static int DEFAULT_TEMPO = 60;
    private int tempo = DEFAULT_TEMPO;
    private Metronome metronome;
    private int counter = 0;
    private JSlider tempoSlider = new JSlider(30, 200, 70);
    private JPanel panelNorth = new JPanel();
    private JPanel panelWest = new JPanel();
    private JPanel panelCenter = new JPanel();
    private JPanel panelSouth = new JPanel();
    private JLabel tempo_label = new JLabel("70 bpm");

    private JLabel textField_count = new JLabel("1");
    public TimeLord() throws InvalidMidiDataException, MidiUnavailableException {
        log("TimeLord constructor()");
        metronome = new Metronome(this);
        metronome.setTempo(DEFAULT_TEMPO);
        setSize(450, 450);
        setTitle("time lord");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPanelNorth();
        setPanelWest();
        setPanelCenter();
        setPanelSouth();

        this.add(panelWest, BorderLayout.WEST);
        this.add(panelNorth,BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void setPanelCenter(){
        log("setPanelCenter()");
        textField_count.setFont(new Font(null, Font.PLAIN, 36));
        panelCenter.add(textField_count);
    }


    private void setPanelNorth(){
        panelNorth.setPreferredSize(new Dimension(400, 100));
        panelNorth.setBackground(Color.lightGray);
        tempoSlider.setPaintTicks(true);
        tempoSlider.setMinorTickSpacing(5);
        tempoSlider.setPaintTrack(true);
        tempoSlider.setMajorTickSpacing(10);
        tempoSlider.setPaintLabels(true);
        tempoSlider.setPreferredSize(new Dimension(400, 40));
        tempoSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tempo_label.setText(String.valueOf(tempoSlider.getValue()));
                metronome.setTempo(tempoSlider.getValue());
            }
        });

        panelNorth.add(tempoSlider);
    }
    private void setPanelSouth(){
        log("setPanelSouth()");
        comboBox_sequences = new JComboBox<String>(SequenceFactory.getSeqKeys());
        comboBox_sequences.addActionListener(cb-> setSequence());
        panelSouth.add(comboBox_sequences);

    }

    private void setSequence() {
        log("TimeLord.setSequence()");
        String name  = (String) comboBox_sequences.getSelectedItem();
        log("selected sequence..", name);
        try {
            metronome.setSequence(SequenceFactory.getSequence(name));
        } catch (InvalidMidiDataException e) {
            JOptionPane.showMessageDialog(this, "error getting sequence");
        }
    }

    private void toggle(){
        log("TimeLord.toggle()");
        if( metronome.isRunning()){
            metronome.stop();
            button.setText("start");
        }else{
            metronome.setTempo(tempoSlider.getValue());
            counter = 0;
            metronome.start();
            button.setText("stop");
        }
    }
    private void setPanelWest(){
        log("setPanelWest()");
        panelWest.setPreferredSize(new Dimension(100, 200));
        button.addActionListener(e->toggle());
        panelWest.add(button);
        tempo_label.setFont(new Font(null, Font.PLAIN, 20));
        panelWest.add(tempo_label);
    }

    @Override
    public void onTick() {
        textField_count.setText(String.valueOf(counter % 4 + 1));
        counter++;
    }
}
