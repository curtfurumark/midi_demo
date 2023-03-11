package midi;

import lib.MidiLogger;

import javax.sound.midi.*;

import static logger.CRBLogger.log;

public class MySynthesizer {
    private Synthesizer synthesizer;
    private Receiver receiver;
    public MySynthesizer() throws MidiUnavailableException {
        log("MySynthesizer constructor");
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
}
