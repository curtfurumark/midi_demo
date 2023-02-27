package midi;

import jdk.swing.interop.DispatcherWrapper;

import javax.sound.midi.*;

public class MidiUtil {

    public static MidiEvent createMidiEvent(int command, int channel, int note, int velocity, int tick) throws InvalidMidiDataException {
        System.out.format("command %d, channel %d, note %d, velocity %d tick %d\n", command, channel, note, velocity, tick);
        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setMessage(command, channel, note, velocity);
        return  new MidiEvent(shortMessage, tick);
    }
    //from c to c and octave above
    public static Sequence createChromaticSequence() throws InvalidMidiDataException {
        System.out.println("MidiUtil.createSequence)");
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
        for( int i = 0, tick = 0; i < 12; i++, tick += 2){
            track.add(createMidiEvent(ShortMessage.NOTE_ON, 1, 60 + i, 20, tick));
            track.add(createMidiEvent(ShortMessage.NOTE_OFF, 1, 60 + i, 20, tick+2));
        }
        return sequence;
    }
    public static Sequence createMetronomeSequence(){
        //Sequence se
        return null;
    }
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
}
