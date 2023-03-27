package classes;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static logger.CRBLogger.log;

public class MelodyFactory {
    private List<Integer> notes = new ArrayList<>();
    public void addNote(Integer note){
        notes.add(note);
    }
    public MelodyFactory(){

    }
    public static Melody getMelody(int number_notes, List<Integer> notes) throws InvalidMidiDataException {
        List<Integer> intMelody = getIntegerMelody(number_notes, notes);
        Sequence sequence = SequenceFactory.getSequence(intMelody);
        return new Melody(sequence, intMelody);
    }
    private static List<Integer> getIntegerMelody(int number_notes, List<Integer> notes){
        Random random = new Random();
        List<Integer> melody = new ArrayList<>();
        for( int i = 0; i < number_notes; i++){
            melody.add(notes.get(random.nextInt(notes.size())));
        }
        return melody;
    }

    /**
     * c
     * @param srcNotes which notes to use in composing the melody
     * @param numberNotes how "long" the melody should be
     * @return something that you can send to a sequence
     */
    public Sequence getSequence(List<Integer> srcNotes, int numberNotes) {
        log("MelodyFactory.getSequence(List<Integer>, number of notes");
        return null;
    }

}
