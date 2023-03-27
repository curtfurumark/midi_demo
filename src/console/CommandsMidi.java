package console;

import classes.Note;
import ear_master.EarTrainer;

import javax.sound.midi.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static logger.CRBLogger.log;
import static logger.CRBLogger.logException;

public class CommandsMidi {
    public static boolean VERBOSE = true;
    public static void playNote(int note, int channel){
        log(String.format("MidiPlayer.playNote(note %d, channel %d))", note, channel));
        try {
            ShortMessage mess = new ShortMessage();
            //mess.setMessage(ShortMessage.NOTE_ON, 0, 63, 93);
            mess.setMessage(ShortMessage.NOTE_ON, channel, note, 93);
            long timeStamp = -1;
            Receiver receiver = MidiSystem.getReceiver();
            receiver.send(mess, timeStamp);
            if( VERBOSE) log("message sent");
        } catch (InvalidMidiDataException | MidiUnavailableException ex) {
            logException(ex);
            //Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void testLoop(int num_notes){
        log("CommandsMidi.testLoop()", num_notes);
        for(int i = 5; i < (4 * num_notes) + 5; i += 4){
            System.out.println("i " + i);
        }
    }
    public static List<Integer> notes;
    public static void initSource(){
        notes = new ArrayList<>();
        notes.add(Note.C);
        notes.add(Note.F);
        notes.add(Note.G);
        notes.add(Note.A);
    }
    public static void getRandomNotes(int[] source, int number_notes){
        log("CommandsMidi.getRandomNotes()");
        List<Integer> melody = new ArrayList<>();
        Random random = new Random();
        for( int i = 0; i < number_notes; i++){
            melody.add(source[random.nextInt(source.length)]);
        }
        System.out.println("printing random melody");
        for(Integer i: melody){
            log("note ", i);
        }
    }
    public static void testRandomNotes(){
        log("CommandsMidi.testRandomNotes()");
        int[] source = {1, 3, 5,6};
        getRandomNotes(source, 5);

    }

    public static void startEarTrainer() {
        log("...startEarTrainer()");
        new EarTrainer();
    }
}
