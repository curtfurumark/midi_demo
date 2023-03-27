package classes;

import javax.sound.midi.Sequence;
import java.util.List;

import static logger.CRBLogger.log;

public class Melody {
    private List<Integer> notes;
    private Sequence sequence;
    private int index = 0;
    public Melody(Sequence sequence, List<Integer> notes){
        this.sequence = sequence;
        this.notes = notes;
        this.index = 0;
    }
    public boolean checkNote(int note){
        if( index >= notes.size()){
            return false;
        }
        return notes.get(index++) == note;
    }

    /**
     *
     * @return a list of notes, describe by their midi number, ie middle c = 61 or whatever
     */
    public List<Integer> getNotes() {
        log("Melody.getNotes()");
        return notes;
    }

    public Sequence getSequence() {
        return sequence;
    }
}
