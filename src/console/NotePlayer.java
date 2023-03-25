/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;


import classes.Note;

import javax.sound.midi.*;

import java.util.Scanner;

import static logger.CRBLogger.log;

/**
 * @author curt rune
 */
public class NotePlayer {
    private Receiver receiver;
    private Scanner scanner;


    public NotePlayer() {
        log("CFNotePlayer() constructor");
        try {
            receiver = MidiSystem.getReceiver();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void changeInstrument(){
        log("NotePlayer.changeInstrument");
        System.out.print("channel: ");
        int channel = scanner.nextInt();
        System.out.print("instrument: ");
        int instrument = scanner.nextInt();
        scanner.nextLine();
        System.out.printf("channel %d, instrument %d\n", channel, instrument);
        try {
            changeInstrument(channel, instrument);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    public void changeInstrument(int channel, int instrument) throws InvalidMidiDataException {
        log("NotePlayer.changeInstrument(channel, instrument) ch: " + channel + ", instr: " + instrument);
        ShortMessage message = new ShortMessage();
        message.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0);
        receiver.send(message, -1);
    }
    public void changeInstrumentHardCoded(){
        log("NotePlayer.changeInstrumentHardCoded()");
        log("will go for instrument 78, haven't got the foggiest...");
        ShortMessage shortMessage = new ShortMessage();
        try {
            shortMessage.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 78, 0);
            receiver.send(shortMessage, -1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * im guessing that default receiver is connected to default synthesizer
     * but i don't know this....
     */
    public void listLoadedInstruments(){
        log("NotePlayer.listLoadedInstruments()");
        Synthesizer synthesizer = null;
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            Instrument[] instruments = synthesizer.getLoadedInstruments();
            for(int i = 0; i < instruments.length; i++){
                System.out.printf("%d %s\n", i, instruments[i].getName());
            }
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    public void playNoteC() {
        log("NotePlayer.playNoteC() channel 0");
        try {
            ShortMessage mess = new ShortMessage();
            mess.setMessage(ShortMessage.NOTE_ON, 0, Note.C, 93);
            receiver.send(mess, -1);
            System.out.println("message sent");
        } catch (InvalidMidiDataException ex) {
            ex.printStackTrace();
        }
    }

    public void playNote(int note) {
        log("NotePlayer.playNote(int note)", note);
        try {
            ShortMessage mess = new ShortMessage();
            mess.setMessage(ShortMessage.NOTE_ON, 0, note, 93);
            long timeStamp = -1;
            Receiver receiver = MidiSystem.getReceiver();
            receiver.send(mess, timeStamp);
            System.out.println("message sent");
        } catch (InvalidMidiDataException | MidiUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void noteOffC() {
        log("NotePlayer.playOffC() channel 0");
        try {
            ShortMessage mess = new ShortMessage();
            mess.setMessage(ShortMessage.NOTE_OFF, 0, Note.C, 93);
            receiver.send(mess, -1);
            System.out.println("message sent");
        } catch (InvalidMidiDataException ex) {
            ex.printStackTrace();
        }
    }
    private void printUsage(){
        System.out.println("'quit' to quit");
        System.out.println("'help' to print this");
        System.out.println("'list loaded' to list loaded instruments");
        System.out.println("'play c' to play a c note");
        System.out.println("'change instrument' to change instrument!");

    }
    public void startSession(){
        log("welcome to a glorious note player session");
        printUsage();
        scanner = new Scanner(System.in);
        String cmd;
        System.out.print("cmd: ");
        while(!(cmd = scanner.nextLine()).equalsIgnoreCase("quit")){
            switch(cmd){
                case "change instrument":
                    changeInstrument();
                    break;
                case "help":
                    printUsage();
                    break;
                case "list loaded":
                    listLoadedInstruments();
                    break;
                case "play c":
                    playNoteC();
                    System.out.print("<enter> to stop note: ");
                    scanner.nextLine();
                    noteOffC();
                    break;


                default:
                    System.out.println("unknown command: " + cmd);

            }
            System.out.print("cmd: ");


        }

    }
}
