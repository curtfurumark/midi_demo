package console;

import javax.sound.midi.*;

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
}
