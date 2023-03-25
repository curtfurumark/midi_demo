package gui;


import lib.MidiLogger;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;


import java.awt.*;

import static logger.CRBLogger.log;

public class MySynth extends JFrame {
    private Synthesizer synthesizer;
    private Instrument[] loaded_instruments;
    private JList jListInstruments;
    private JScrollPane scrollPane;
    private JTextField textField_synth;
    private JPanel panelNorth;
    private JPanel panelCenter;
    public MySynth(){
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addPanelNorth();
        try {
            init();
            initPanelCenter();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        setVisible(true);
    }

    private void initPanelCenter() {
        log("...initPanelCenter()");
        loaded_instruments = synthesizer.getLoadedInstruments();
        MidiLogger.logInstruments(loaded_instruments);
        panelCenter = new JPanel();
        panelCenter.setBackground(Color.BLUE);
        jListInstruments = new JList(loaded_instruments);
        scrollPane = new JScrollPane(jListInstruments);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        panelCenter.add(scrollPane);
        add(panelCenter, BorderLayout.CENTER);
    }

    private void init() throws MidiUnavailableException {
        synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        String name = synthesizer.getDeviceInfo().getName();
        textField_synth.setText(name);
    }

    private void addPanelNorth() {
        log("...addPanelNorth()");
        textField_synth = new JTextField(40);
        panelNorth = new JPanel();
        panelNorth.setBackground(Color.GREEN);
        panelNorth.add(textField_synth);
        add(panelNorth, BorderLayout.NORTH);
    }
}
