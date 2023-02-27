package midi;

import lib.Debug;

import javax.sound.midi.*;
import java.io.*;

public class MySequencer {
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    private MyReceiver myReceiver;
    private int tempoBPM = 60;

    public MySequencer(){
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        myReceiver = new MyReceiver();
    }
    public void playFile(String file_name) throws IOException, InvalidMidiDataException {
        System.out.format("MySequencer.playFile(%s)\n", file_name);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(file_name)));
        sequencer.setSequence(inputStream);
        //sequencer.set
        sequencer.start();
    }

    public void setSequence(Sequence sequence) throws InvalidMidiDataException, MidiUnavailableException {
        System.out.println("MySequencer.playSequence()");
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(tempoBPM);
        sequencer.setLoopStartPoint(0);
        sequencer.setLoopEndPoint(8);
        sequencer.setLoopCount(5);
        Transmitter transmitter = sequencer.getTransmitter();
        transmitter.setReceiver(myReceiver);
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
            myReceiver = new MyReceiver();
        }
        sequencer.setTickPosition(0);
        sequencer.start();
        Debug.log(sequencer);

    }
    public void stop(){
        System.out.println("MySequencer.stop()");
        if( sequencer.isRunning()){
            sequencer.stop();
        }
    }

    public MyReceiver getMyReceiver() {
        return myReceiver;
    }

    public boolean isPlaying() {
        return sequencer.isRunning();
    }
}
