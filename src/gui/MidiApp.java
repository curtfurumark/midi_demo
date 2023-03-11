package gui;

import midi.MySequencer;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static logger.CRBLogger.log;

public class MidiApp extends JFrame {
    private Button button_play = new Button("play");
    private Button button_stop = new Button("stop");
    private JPanel panel_north = new JPanel();
    private MySequencer mySequencer = new MySequencer();
    public MidiApp() {
        System.out.println("Frame() ctor");

        setTitle("midi playground");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        button_play.addActionListener((a)->playZara());
        button_stop.addActionListener((a)->stopZara());
        panel_north.setPreferredSize(new Dimension(100, 100));
        panel_north.setBackground(Color.BLUE);
        panel_north.add(button_play);
        panel_north.add(button_stop);
        this.add(panel_north, BorderLayout.NORTH);

    }
    private void playZara(){
        log("playZara()");
         try {
                mySequencer.playFile("res/zarathustra.mid");
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
    private void stopZara(){
        log("stopZara()");
        mySequencer.stop();

    }
}
