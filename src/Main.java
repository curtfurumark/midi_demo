import ear_master.ConsoleEarMaster;
import ear_master.EarTrainer;
import gui.MidiFilePlayer;
import gui.TimeLord;
import logger.CRBLogger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import java.io.IOException;

import static logger.CRBLogger.log;

public class Main {
    public static void main(String[] args) {
        System.out.println("midi midi midi and some midi");
        try {
            CRBLogger.startLogging("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //new ConsoleMidi().startSession();
        //startTimeLord();
        //new MySynth();
        new EarTrainer();

        //new ConsoleEarMaster().generateMelody();
        //new Keyboard();
        //new NotePlayer().startSession();

        //CommandsMidi.testRandomNotes();
        //CommandsMidi.startEarTrainer();
        //startMidiFilePlayer();
        //new Keyboard();
        //new ShortMessageGUI();
        //Dev.printSequence(SequenceFactory.shortSequence());
        //CommandsDev.printInstruments();
        //new ConsoleMidi().playSequence();
        //CommandsMidi.testLoop(8);
        //new MidiDemoApp();
        //new Keyboard();
    }
    public static void startMidiFilePlayer(){
        log("startMidiFilePlayer()");
        new MidiFilePlayer();

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