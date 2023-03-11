package lib;

import midi.MySynthesizer;

import javax.sound.midi.MidiUnavailableException;

import static lib.MidiLogger.log;

public class CommandsDev {
    public static void printInstruments(){
        log("CommandsDev.printInstruments()");
        MySynthesizer synthesizer = null;
        try {
            synthesizer = new MySynthesizer();
            synthesizer.printInstruments();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }


    }
}
