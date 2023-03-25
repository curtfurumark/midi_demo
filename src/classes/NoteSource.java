package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteSource {
    private List<Integer> notes = new ArrayList<>();
    public void addNote(Integer note){
        notes.add(note);
    }
    public NoteSource(){
        notes.add(Note.C);
        notes.add(Note.D);
        notes.add(Note.E);
        notes.add(Note.F);
        notes.add(Note.G);
        notes.add(Note.A);
        notes.add(Note.B);
        notes.add(Note.C1);
    }
    public List<Integer> getMelody(int number_notes){
        Random random = new Random();
        List<Integer> melody = new ArrayList<>();
        for( int i = 0; i < number_notes; i++){
            melody.add(notes.get(random.nextInt(notes.size())));
        }
        return melody;
    }
}
