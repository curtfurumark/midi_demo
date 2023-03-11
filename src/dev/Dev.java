package dev;

import lib.MidiLogger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;

import static logger.CRBLogger.log;
import static midi.DrumSet.BASS_DRUM_1;

public class Dev {
    public static void testOne(){
        ShortMessage shortMessage = new ShortMessage();
        try {
            shortMessage.setMessage(ShortMessage.NOTE_ON, 9, BASS_DRUM_1, 100);
            int statusByte = shortMessage.getStatus();
            System.out.println("status");
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    public static boolean isStatusByte(byte b){
        log("Dev.isStatusByte()", b);
        return (b &  0b10000000) == 128;
    }
    public static void printSequence(Sequence sequence){
        log("printSequence(Sequence)");
        MidiLogger.log(sequence);
    }
    public static void randomNotes(int min, int max, int count){

    }
    public static void randomNotes(int[] notes, int count){

    }
}

