package midi;

import jdk.swing.interop.DispatcherWrapper;

import javax.sound.midi.*;

public class MidiUtil {

    public static String commandToString(int command){
        switch (command) {
            case ShortMessage.NOTE_ON:
                return "NOTE_ON";
            case ShortMessage.NOTE_OFF:
                return "NOTE_OFF";
            case ShortMessage.START:
                return "START";
            case ShortMessage.CONTROL_CHANGE:
                return "CONTROL_CHANGE";
        }
        return "unknown";
    }
    public static String noteNumToChar(int note){
        switch(note) {
            case 60:
            case 72:
                return "C";
            case 61:
            case 73:
                return "C#";
            case 74:
            case 62:
                return "D";
            case 63:
                return "D#";
            case 64:
                return "E";
            case 65:
                return "F";
            case 66:
                return "F#";
            case 67:
                return "G";
            case 68:
                return "G#";
            case 69:
                return "A";
            case 70:
                return "Bb";
            case 71:
                return "B";

            default:
                return "X";
        }
    }
    public static void info(){
        MidiSystem.getMidiDeviceInfo();

    }
}
