package console;

import classes.Note;
import classes.SequenceFactory;
import gui.TimeLord;
import midi.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.util.Scanner;

import static lib.CommandsDev.printInstruments;
import static logger.CRBLogger.log;

public class ConsoleMidi {
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMidi(){

    }
    private void printUsage(){
        System.out.println("'quit' to quit");
        System.out.println("'change instrument' to change instrument");
        System.out.println("'drums' to drums");
        System.out.println("'ear' to start ear training");
        System.out.println("'metronome' to start metronome");
        System.out.println("'play note' to play a single note");
        System.out.println("'play instrument' a single c, different instruments");
        System.out.println("'meta' to play a short sequence with one meta message");
        System.out.println("'timelord' to start gui metronome");
        System.out.println("'print instruments' to print available instruments/default(?) synthesizer");
        System.out.println("'play channel' to play a C on requested channel");
        System.out.println("'play sequence' to play four to the bar");
        System.out.println("'list midi devices'");
    }

    public  void startSession(){
        log("welcome to a cosy midi session");
        printUsage();
        String cmd;
        System.out.print("cmd: ");
        while(!(cmd = scanner.nextLine()).equalsIgnoreCase("quit")){
            switch (cmd){
                case "metronome":
                    startMetronome();
                    break;
                case "drums":
                    startDrums();
                    break;
                case "list midi devices":
                    listMidiDevices();
                    break;
                case "meta":
                    playMetaSequence();
                    break;
                case "timelord":
                    try {
                        new TimeLord();
                    } catch (InvalidMidiDataException | MidiUnavailableException e) {
                        JOptionPane.showMessageDialog(null,"error starting timelord");
                    }
                    break;
                case "print instruments":
                    printInstruments();
                    break;
                case "play instrument":
                    playInstrument();
                    break;
                case "play sequence":
                    playSequence();
                    break;
                case "play channel":
                    playChanel();
                    break;
            }
            System.out.print("cmd: ");
        }
        System.out.println("good bye");
    }



    public void listMidiDevices(){
        log("ConsoleMidi.listMidiDevices()");
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for( MidiDevice.Info info: infos){
            log("description", info.getDescription());
            log("vendor", info.getVendor());
            log("name", info.getName());
            log("toString", info.toString());
            System.out.println();
        }

    }

    private void playMetaSequence() {
        log("ConsoleMidi.playMetaSequence()");
        MySequencer sequencer = new MySequencer();
        sequencer.playSequence(SequenceFactory.withMetaMessages());
        System.out.println("good bye");
    }

    private void playInstrument() {
        log("ConsoleMidi.playInstrument() -1 to quit");
        MidiPlayer player = new MidiPlayer();
        do {
            System.out.print("instrument: ");
            int instrument = scanner.nextInt();
            if( instrument == -1){
                scanner.nextLine();
                break;}

            player.playNote(Note.C, instrument);
        }
        while(true);
    }

    public  void playSequence() {
        log("ConsoleMidi.playSequence()");

        try {
            MySequencer sequencer = new MySequencer();
            sequencer.setSequence(SequenceFactory.fourToTheBar());
            sequencer.setTempoBPM(40);
            //sequencer.setLoop(0,4, 4);
            sequencer.play();
            //sequencer.playSequence(SequenceFactory.fourToTheBar());
            log("after");
        } catch (InvalidMidiDataException | MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    private void playChanel(){
        log("ConsoleMidi.playChannel()");
        do {
            System.out.println("channel: ");
            int channel = scanner.nextInt();
            scanner.nextLine();
            CommandsMidi.playNote(63, channel);
            System.out.print("on more time y/n? ");
            if( !scanner.nextLine().equalsIgnoreCase("y")){
                break;
            }
        }while(true);

    }

    private void startDrums() {
        System.out.println("startDrums()");
        Drums drums = new Drums();
        try {
            drums.start();
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void startMetronome() {
        log("ConsoleMidi.startMetronome()");
        Metronome metronome;
        try {
            metronome = new Metronome();
            System.out.print("tempo: ");
            int tempo = scanner.nextInt();
            scanner.nextLine();
            metronome.setTempo(tempo);
            metronome.setSequence(SequenceFactory.metronomeOneBarV2());
            metronome.start(0, 4);
            System.out.print("enter to quit");
            scanner.nextLine();
            metronome.stop();
        } catch (InvalidMidiDataException | MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

    }


}
