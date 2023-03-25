package gui;

import lib.Constants;
import midi.MySequencer;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static logger.CRBLogger.log;

public class MidiFilePlayer extends JFrame {
    private Button button_play = new Button("play");
    private Button button_stop = new Button("stop");
    private Button button_pause = new Button("pause");
    private  Button button_resume = new Button("resume");
    public static boolean VERBOSE = true;
    private JMenuBar menuBar = new JMenuBar();
    private JLabel jLabel_current_file;
    private JLabel jLabel_number_tracks;
    private JLabel jLabel_tempo;
    private String current_file;

    private JMenu menuFiles = new JMenu("file");
    private JMenuItem menuItem_open = new JMenuItem("open");

    private JPanel panel_north = new JPanel();
    private JPanel panel_south = new JPanel();
    private MySequencer mySequencer = new MySequencer();
    public MidiFilePlayer() {
        System.out.println("Frame() ctor");

        setTitle("midi playground");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        current_file = Constants.ZARATHUSTRA_MIDI;

        setPanelNorth();
        setPanelSouth();
        createMenu();
        this.add(panel_north, BorderLayout.NORTH);
        setVisible(true);
    }
    private void setPanelNorth(){
        if( VERBOSE)log("MidiFilePlayer.setPanelNorth()");
        button_play.addActionListener((a)->playCurrentFile());
        button_stop.addActionListener((a)->stop());
        button_resume.addActionListener(a->resume());
        button_pause.addActionListener(a->pause());
        panel_north.setPreferredSize(new Dimension(100, 100));
        panel_north.setBackground(Color.BLUE);
        panel_north.add(button_play);
        panel_north.add(button_stop);
        panel_north.add(button_pause);
        panel_north.add(button_resume);

    }

    private void resume() {
        log("MidiFilePlayer.resume()");
        mySequencer.resume();
    }

    private void setPanelSouth() {
        log("setPanelSouth()");
        jLabel_current_file = new JLabel(Constants.ZARATHUSTRA_MIDI);
        jLabel_current_file.setBounds(20, 20, 300, 20);
        jLabel_number_tracks = new JLabel("number of tracks: ");
        jLabel_number_tracks.setBounds(20, 50, 150, 20);
        jLabel_tempo = new JLabel("tempo");
        jLabel_tempo.setBounds(20 ,80, 80, 20 );
        panel_south.setLayout(null);
        panel_south.add(jLabel_current_file);
        panel_south.add(jLabel_number_tracks);
        panel_south.add(jLabel_tempo);
        add(panel_south);

    }

    private void createMenu(){
        menuFiles.add(menuItem_open);
        menuBar.add(menuFiles);
        menuItem_open.addActionListener(a->openFileChooser());
        this.setJMenuBar(menuBar);

    }

    private void openFileChooser(){
        JFileChooser fileChooser = new JFileChooser(Constants.DEFAULT_MIDI_FOLDER);
        if( JFileChooser.APPROVE_OPTION ==fileChooser.showOpenDialog(this)){
              File file = fileChooser.getSelectedFile();
              log("file", file.getAbsolutePath());
              jLabel_current_file.setText(file.getAbsolutePath());
              current_file = file.getAbsolutePath();
        }else{
            log("user cancelled");
        }
    }
    private void playMidiFile() {
        log("MidiApp.playMidiFile()");
    }

    private void pause(){
        log("MidiFilePlayer.pause()");
        mySequencer.pause();
    }

    private void playCurrentFile(){
        log("MidiFilePlayer.playCurrentFile()");
         try {
                mySequencer.playFile(current_file);
                int nTracks = mySequencer.getNumberTracks();
                String strTracks = String.format("number of tracks: %d", nTracks);

                float bpm = mySequencer.getTempoBPM();
                String str_bmp = String.format("%.0f bpm", bpm);
                jLabel_tempo.setText(str_bmp);
                jLabel_number_tracks.setText(strTracks);
         } catch (IOException e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(this, e.toString(), "error", JOptionPane.ERROR_MESSAGE);
         } catch (InvalidMidiDataException e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(this, e.toString(), "error", JOptionPane.ERROR_MESSAGE);
         }catch(Exception e){
             e.printStackTrace();
             JOptionPane.showMessageDialog(this, e.toString(), "error", JOptionPane.ERROR_MESSAGE);
         }
    }
    private void playMidiFile(String fpath){
        log("...playMidiFile(String) ", fpath);
        try {
            mySequencer.playFile(fpath);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "error playing file " + fpath);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "error playing file " + fpath);
        }

    }
    private void stop(){
        log("stopZara()");
        mySequencer.stop();

    }
}
