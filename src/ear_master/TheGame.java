package ear_master;

import classes.Melody;

import java.util.List;

public class TheGame {
    private Melody currentMelody;
    private List<Integer> notes;
    private int index = 0;
    public TheGame(){


    }
    public void newGame(){

    }
    public boolean checkNote(int note){
        if( index >= notes.size()){
            return false;
        }
        return notes.get(index++) == note;

    }
}
