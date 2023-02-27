package midi;

import javax.sound.midi.*;

import static logger.CRBLogger.log;
import static logger.CRBLogger.logException;

public class MidiPlayer {
    public static boolean VERBOSE = false;
    public void playNote(int note){
        log("MidiPlayer.playNote(int note))", note);
        try {
            if( VERBOSE) log("playNote: " , note);
            ShortMessage mess = new ShortMessage();
            //mess.setMessage(ShortMessage.NOTE_ON, 0, 63, 93);
            mess.setMessage(ShortMessage.NOTE_ON, 0, note, 93);
            long timeStamp = -1;
            Receiver receiver = MidiSystem.getReceiver();
            receiver.send(mess, timeStamp);
            if( VERBOSE) log("message sent");
        } catch (InvalidMidiDataException | MidiUnavailableException ex) {
            logException(ex);
            //Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
