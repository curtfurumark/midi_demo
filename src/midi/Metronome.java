package midi;

import lib.MidiLogger;

import javax.sound.midi.*;

import static logger.CRBLogger.log;

public class Metronome implements Receiver {
    public static boolean VERBOSE = true;
    private static final int DEFAULT_TEMP0 = 120;
    private int tempo = DEFAULT_TEMP0;
    int[] types = new int[128];
    private Sequencer sequencer;

    @Override
    public void send(MidiMessage message, long timeStamp) {
        log("Metronome.send(MidiMessage message, long timeStamp ", timeStamp);
        log("class " , message.getClass().getName());
        if( message instanceof ShortMessage){
            if(((ShortMessage)message).getCommand()== ShortMessage.NOTE_ON){
                MidiLogger.log((ShortMessage) message);
                callback.onTick();
            }
        }
    }

    @Override
    public void close() {

    }

    public interface Callback{
        void onTick();
    }
    private Callback callback;

    public Metronome(Callback  callback) throws InvalidMidiDataException, MidiUnavailableException {
        this();
        this.callback = callback;
    }
    public Metronome() throws InvalidMidiDataException, MidiUnavailableException {
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.setSequence(SequenceFactory.longMetronome());
        sequencer.setTempoInBPM(DEFAULT_TEMP0);
        //sequencer.setLoopStartPoint(0);
        //sequencer.setLoopEndPoint(4);
        //sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        sequencer.getTransmitter().setReceiver(this);
    }

    public void setSequence(Sequence sequence) throws InvalidMidiDataException {
        sequencer.setSequence(sequence);
    }

    public void start(long startTick, long endTick){
        log("Metronome.start(long startTick, long endTick");
        sequencer.setLoopStartPoint(startTick);
        sequencer.setLoopEndPoint(endTick);
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        sequencer.start();
    }
    public void start(){
        if( VERBOSE) log("Metronome.start()");
        sequencer.start();
        if( VERBOSE){
            log("tempo", sequencer.getTempoInBPM());
            log("ticks", sequencer.getTickLength());
            log("loop start" , sequencer.getLoopStartPoint());
            log("loop end", sequencer.getLoopEndPoint());
        }
    }
    public void setLoopEnd(long tick){
        sequencer.setLoopEndPoint(tick);
    }
    public void setLoopStart(long tick){
        sequencer.setLoopStartPoint(tick);
    }
    public void setLoopCount(long count){
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        if( VERBOSE) log("Metronome.stop()");
        sequencer.stop();
    }
    public void setTempo(int tempo){
        if( VERBOSE) log("TimeLord.setTempo()");
        this.tempo = tempo;
        sequencer.setTempoInBPM(tempo);
    }
    public int getTempo(){
        return tempo;
    }

    public boolean isRunning() {
        return sequencer.isRunning();
    }
}
