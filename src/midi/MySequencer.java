package midi;

import lib.MidiLogger;

import javax.sound.midi.*;
import java.io.*;

import static logger.CRBLogger.log;

public class MySequencer {
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    private DrawableReceiver drawableReceiver;
    private int tempoBPM = 60;

    public MySequencer(){
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        drawableReceiver = new DrawableReceiver();
    }
    public void playFile(String file_name) throws IOException, InvalidMidiDataException {
        System.out.format("MySequencer.playFile(%s)\n", file_name);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(file_name)));
        sequencer.setSequence(inputStream);
        //sequencer.set
        sequencer.start();
    }
    public void playSequence(Sequence sequence){
        log("MySequence.playSequence(Sequence)");
        try {
            sequencer.setSequence(sequence);
            //float bpm = sequencer.getTempoInBPM();
            //log("bpm", bpm);
            //sequencer.setL
            sequencer.setTempoInBPM(60);
            sequencer.start();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    public void setSequence(Sequence sequence) throws InvalidMidiDataException, MidiUnavailableException {
        System.out.println("MySequencer.playSequence()");
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(tempoBPM);
        sequencer.setLoopStartPoint(0);
        sequencer.setLoopEndPoint(8);
        sequencer.setLoopCount(5);
        Transmitter transmitter = sequencer.getTransmitter();
        transmitter.setReceiver(drawableReceiver);
    }
    public void setLoop(long start, long end, int loop_count){
        sequencer.setLoopStartPoint(start);
        sequencer.setLoopEndPoint(end);
        sequencer.setLoopCount(loop_count);

    }
    public void setTempoBPM(int tempoBPM){
        this.tempoBPM = tempoBPM;
    }
    public void play(){
        System.out.println("MySequencer.play()");
        if( sequencer.isRunning()){
            sequencer.stop();
            drawableReceiver = new DrawableReceiver();
        }
        sequencer.setTickPosition(0);
        sequencer.start();
        MidiLogger.log(sequencer);

    }
    public void stop(){
        System.out.println("MySequencer.stop()");
        if( sequencer.isRunning()){
            sequencer.stop();
        }
    }

    public DrawableReceiver getMyReceiver() {
        return drawableReceiver;
    }

    public boolean isPlaying() {
        return sequencer.isRunning();
    }
}
