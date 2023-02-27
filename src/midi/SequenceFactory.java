package midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SequenceFactory {
    public Sequence getMetronomeSeq() {
        System.out.println("SequenceFactory.getMetronomeSeq()");

        return null;
    }

    public static Sequence getDrumSeq(int num_notes) throws InvalidMidiDataException {
        System.out.println("SequenceFactory.getMetronomeSeq()");
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
        int note = 35;
        int tick = 0;
        for(int i = 5; i < (4 * num_notes) + 5; i += 4 ){
            note = (note == 35) ? 38: 35;
            track.add(MidiUtil.createMidiEvent(ShortMessage.NOTE_ON, 9, note, 100, i));
            track.add(MidiUtil.createMidiEvent(ShortMessage.NOTE_OFF, 9, note, 100, i+2));
        }
        return sequence;
    }
}