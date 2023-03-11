import console.ConsoleMidi;
import dev.Dev;
import gui.Keyboard;
import gui.TimeLord;
import midi.SequenceFactory;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class Main {
    public static void main(String[] args) {
        System.out.println("midi midi midi and some midi");
        //new ConsoleMidi().startSession();
        startTimeLord();
        //new Keyboard();
        //Dev.printSequence(SequenceFactory.shortSequence());
        //CommandsDev.printInstruments();
        //new ConsoleMidi().playSequence();
        //CommandsMidi.testLoop(8);
        //new MidiDemoApp();
        //new Keyboard();
    }
    public static void startTimeLord(){
        //new ConsoleMidi().startSession();
        try {
            new TimeLord();
            //new Keyboard();
        } catch (InvalidMidiDataException | MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}