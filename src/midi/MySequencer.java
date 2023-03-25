package midi;

import lib.MidiLogger;

import javax.sound.midi.*;
import java.io.*;

import static logger.CRBLogger.log;

public class MySequencer {
    private Sequencer sequencer;
    public static boolean VERBOSE = true;
    private long current_tick = 0;
    private Synthesizer synthesizer;
    private DrawableReceiver drawableReceiver;
    private Receiver receiver;
    private int tempoBPM = 60;

    public int  getNumberTracks() {
        if( VERBOSE) log("getNumberTracks()");
        return sequencer.getSequence().getTracks().length;
    }
    public float getTempoBPM(){
        return sequencer.getTempoInBPM();
    }


    private enum State{
        PENDING, PLAYING, PAUSED, STOPPED, FINISHED
    }
    private State state = State.PENDING;

    public MySequencer(){
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            receiver = synthesizer.getReceiver();
            //receiver.
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        drawableReceiver = new DrawableReceiver();
    }

    public void pause() {
        if(VERBOSE)log("MySequencer.pause()");
        sequencer.stop();
        current_tick = sequencer.getTickPosition();
        log("current_tick", current_tick);
    }

    public void playFile(String file_name) throws IOException, InvalidMidiDataException {
        if( VERBOSE) log("MySequencer.playFile(%s)", file_name);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(file_name)));
        sequencer.setSequence(inputStream);
        state = State.PLAYING;
        sequencer.start();
    }
    public void playNote(int note){
        if(VERBOSE) log("MySequencer.playNote()", note);
        try {
            ShortMessage mess1 = new ShortMessage();
/*            mess1.setMessage(ShortMessage.PROGRAM_CHANGE, 3, 123);
            receiver.send(mess1, -1);*/
            ShortMessage mess = new ShortMessage();
            mess.setMessage(ShortMessage.NOTE_ON, 3, note, 93);
            receiver.send(mess, 0);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }
    public void playSequence(Sequence sequence){
        log("MySequence.playSequence(Sequence)");
        try {
            sequencer.setSequence(sequence);
            sequencer.setTickPosition(0);
            sequencer.setTempoInBPM(60);
            sequencer.start();
            //sequencer.s
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    public void playSequence(Sequence sequence, int bpm){
        log("MySequence.playSequence(Sequence)");
        try {
            sequencer.setSequence(sequence);
            sequencer.setTempoInBPM(bpm);
            sequencer.start();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        log("MySequencer.resume() current tick: ", current_tick);
        sequencer.start();
        //sequencer.
    }
    @Deprecated
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
        //sequencer.setTickPosition(0);
        if( current_tick != 0){
            log("should probably resume");
        }
        sequencer.start();
        MidiLogger.log(sequencer);

    }
    public void stop(){
        if( VERBOSE) log("MySequencer.stop()");
        if( sequencer.isRunning()){
            sequencer.stop();
            current_tick = sequencer.getTickPosition();
            log("current_tick", current_tick);
        }
    }

    public DrawableReceiver getMyReceiver() {
        return drawableReceiver;
    }

    public boolean isPlaying() {
        return sequencer.isRunning();
    }
}
