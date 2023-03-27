package midi;

import lib.MidiLogger;

import javax.sound.midi.*;

import java.util.Arrays;
import java.util.List;

import static logger.CRBLogger.log;

public class MySynthesizer {
    public static  boolean VERBOSE = true;
    private Synthesizer synthesizer;
    private Receiver receiver;
    public MySynthesizer() throws MidiUnavailableException {
        if( VERBOSE) log("MySynthesizer constructor");
        synthesizer = MidiSystem.getSynthesizer();
        receiver = synthesizer.getReceiver();
    }
    public void playNote(){
        log("MySynthesizer.playNote()");
    }
    public void printInstruments(){
        log("MySynthesizer.printInstruments()");
        Instrument[] instruments = synthesizer.getAvailableInstruments();
        MidiLogger.logInstruments(instruments);
    }
    public List<Instrument> getLoadedInstruments(){
        log("MySynthesizer.getLoadedInstruments()");
        return Arrays.asList(synthesizer.getLoadedInstruments());

    }
}
