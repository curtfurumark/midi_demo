package ear_master;

import classes.MelodyFactory;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static logger.CRBLogger.log;

public class ConsoleEarMaster {
    private Scanner scanner = new Scanner(System.in);
    private void printUsage(){
        log("'quit' to quit");
        log("'help' to print this");
        log("'new melody' to create a new melody");


    }

    public void generateMelody(){
        log("ConsoleEarMaster.generateMelody()");
        System.out.print("number of notes: ");
        List<Integer> notes = new ArrayList<>();
        int numberNotes = scanner.nextInt();
        for( int i = 0; i < numberNotes; i++){
            System.out.printf("note %d: ", i);
            int note = scanner.nextInt();
            notes.add(note);
        }
        System.out.println("these are your chosen notes, good luck");
        System.out.print("length of melody: ");
        int melodyLength = scanner.nextInt();
        System.out.println("melody length " + melodyLength);
        System.out.println("and here is the melody");
        List<Integer> melody = null;
        try {
            melody = MelodyFactory.getMelody(melodyLength, notes).getNotes();
            log("and this is the melody your supposed to guess");
            for(Integer note: melody){
                System.out.print(note + " ");
            }
            System.out.println();
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }

    }
}
