package console;

import midi.Drums;
import midi.MidiPlayer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.util.Scanner;

import static logger.CRBLogger.log;

public class ConsoleMidi {
    private Scanner scanner = new Scanner(System.in);

    public ConsoleMidi(){

    }
    private void printUsage(){
        System.out.println("'quit' to quit");
        System.out.println("'met' to start metronome");
        System.out.println("'dr' to drum");
        System.out.println("'play note' to play a single note");
    }

    public  void startSession(){
        log("welcome to a cosy midi session");
        printUsage();
        String cmd;
        System.out.print("cmd: ");
        while(!(cmd = scanner.nextLine()).equalsIgnoreCase("quit")){
            switch (cmd){
                case "metronome":
                    startMetronome();
                    break;
                case "drums":
                    startDrums();
                    break;
                case "play note":
                    playNote();
                    break;
            }
            System.out.print("cmd: ");
        }
        System.out.println("good bye");
    }

    private void playNote() {
        try {
            MidiPlayer player = new MidiPlayer();
            log("ConsoleMidi.playNote()");
            System.out.print("note (-1 to quit): ");
            int note;
            while( (note = scanner.nextInt()) != -1){
                scanner.nextLine();
                player.playNote(note);
                System.out.print("note: ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("bye bye");

    }

    private void startDrums() {
        System.out.println("startDrums()");
        Drums drums = new Drums();
        try {
            drums.start();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void startMetronome() {
        System.out.println("startMetronome()");
    }


}
