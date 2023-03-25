package classes;

import javax.sound.midi.*;

import java.util.List;

import static logger.CRBLogger.log;
import static classes.DrumSet.*;

public class SequenceFactory {
    public Sequence getMetronomeSeq() {
        System.out.println("SequenceFactory.getMetronomeSeq()");

        return null;
    }
    public static String[] getSeqKeys(){
        return new String[]{"drums", "metronome1", "metronome2", "long metronome"};
    }
    public static  Sequence getSequence(String name) throws InvalidMidiDataException {
        switch (name){
            case "drums":
                return getDrumSeq(8);
            case "metronome1":
                return metronomeOneBar();
            case "metronome2":
                return metronomeOneBarV2();
            case "long metronome":
                return longMetronome();
            default:
                return null;
        }
    }
    public static Sequence getSequence(List<Integer> notes) throws InvalidMidiDataException {
        log("getSequence(List<Integer>)");
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
        for( int i = 0, tick = 0; i < notes.size(); i++, tick += 2){
            track.add(createMidiEvent(ShortMessage.NOTE_ON, 1, notes.get(i), 100, tick));
            track.add(createMidiEvent(ShortMessage.NOTE_OFF, 1, notes.get(i), 100, tick + 1));
        }
        return sequence;
    }

    public static Sequence getDrumSeq(int num_notes) throws InvalidMidiDataException {
        System.out.println("SequenceFactory.getMetronomeSeq()");
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
        int note = 35;
        int tick = 0;
        for(int i = 5; i < (4 * num_notes) + 5; i += 4 ){
            note = (note == 35) ? 38: 35;
            track.add(createMidiEvent(ShortMessage.NOTE_ON, 9, note, 100, i));
            track.add(createMidiEvent(ShortMessage.NOTE_OFF, 9, note, 100, i+2));
        }
        return sequence;
    }
    public static Sequence fourToTheBar() {
        log("SequenceFactory.fourToTheBar()");
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, BASS_DRUM_1, 100), 1));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,BASS_DRUM_1, 100), 2));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,ACOUSTIC_SNARE, 100), 5));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,ACOUSTIC_SNARE, 100), 7));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, BASS_DRUM_1, 100), 9));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, BASS_DRUM_1, 100), 11));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,ACOUSTIC_SNARE, 100), 13));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,ACOUSTIC_SNARE, 100), 15));
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return sequence;
    }

    /**
     * 4 beats of closed hi hat
     * might it be that the same
     * @return
     */
    public static Sequence metronomeOneBar() {
        log("SequenceFactory.metronome2()");
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,CLOSED_HI_HAT, 100), 0));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, CLOSED_HI_HAT, 100), 1));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 1));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, CLOSED_HI_HAT, 100), 2));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 2));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, CLOSED_HI_HAT, 100), 3));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 3));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, CLOSED_HI_HAT, 100), 4));

/*
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, SIDE_STICK, 100), 0));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,SIDE_STICK, 100), 2));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,SIDE_STICK, 100), 4));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,SIDE_STICK, 100), 7));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, SIDE_STICK, 100), 8));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, SIDE_STICK, 100), 11));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,SIDE_STICK, 100), 12));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,SIDE_STICK, 100), 15));*/
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return sequence;
    }

    /**
     * same as first one  BUT every other beat is either a closed hi hat or a side stick
     * thus no ambigous note on, note off at the same time for same instrument (note drum whatever...)
     * turns out not to be the problem
     * @return
     */
    public static Sequence metronomeOneBarV2() {
        log("SequenceFactory.metronome2()");
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,CLOSED_HI_HAT, 100), 0));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, CLOSED_HI_HAT, 100), 1));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, SIDE_STICK, 100), 1));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, SIDE_STICK, 100), 2));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 2));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, CLOSED_HI_HAT, 100), 3));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, SIDE_STICK, 100), 3));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, SIDE_STICK, 100), 4));

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return sequence;
    }
    public static Sequence longMetronome() throws InvalidMidiDataException {
        log("SequenceFactory.longMetronome()");
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
        for (int i = 0; i < 1024;) {
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,OPEN_HI_HAT, 100), i++));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,OPEN_HI_HAT, 100), i++));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,CLOSED_HI_HAT, 100), i++));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,CLOSED_HI_HAT, 100), i++));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,CLOSED_HI_HAT, 100), i++));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,CLOSED_HI_HAT, 100), i++));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9,CLOSED_HI_HAT, 100), i++));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9,CLOSED_HI_HAT, 100), i++));
        }
        return sequence;
    }

    public static Sequence shortSequence() {
        log("SequenceFactory.shortSequence()");
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, BASS_DRUM_1, 100), 0));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 1));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, BASS_DRUM_1, 100), 1));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 2));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, BASS_DRUM_1, 100), 2));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 3));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, BASS_DRUM_1, 100), 4));

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return sequence;
    }
    public static Sequence withMetaMessages(){
        log("SequenceFactory.withMetaMessages");
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, BASS_DRUM_1, 100), 0));
            track.add(new MidiEvent(new MetaMessage(MetaMessage.META, "hello".getBytes(), "hello".length()), 1));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, BASS_DRUM_1, 100), 1));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 2));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 9, BASS_DRUM_1, 100), 2));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, CLOSED_HI_HAT, 100), 3));
            //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 9, BASS_DRUM_1, 100), 4));

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return sequence;
    }
    public static MidiEvent createMidiEvent(int command, int channel, int note, int velocity, int tick) throws InvalidMidiDataException {
        System.out.format("command %d, channel %d, note %d, velocity %d tick %d\n", command, channel, note, velocity, tick);
        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setMessage(command, channel, note, velocity);
        return  new MidiEvent(shortMessage, tick);
    }

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

}