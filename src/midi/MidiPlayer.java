package midi;

import javax.sound.midi.*;

import static logger.CRBLogger.log;
import static logger.CRBLogger.logException;

public class MidiPlayer {
    private Receiver receiver;
    private Synthesizer synthesizer;
    private static final int TIMESTAMP = -1;
    public static boolean VERBOSE = false;

    public MidiPlayer() {
        try {
            receiver = MidiSystem.getReceiver();
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playNote(int note) {
        try {
            if (VERBOSE) log("MidiPlayer.playNote: ", note);
            ShortMessage mess = new ShortMessage();
            //mess.setMessage(ShortMessage.NOTE_ON, 0, 63, 93);
            mess.setMessage(ShortMessage.NOTE_ON, 0, note, 93);
            long timeStamp = -1;
            receiver.send(mess, TIMESTAMP);
            //if( VERBOSE) log("message sent");
        } catch (InvalidMidiDataException ex) {
            logException(ex);
        }
    }
    public void noteOff(int note){
        if(VERBOSE) log("MidiPlayer.noteOff()");
        try {
            if (VERBOSE) log("MidiPlayer.playNote: ", note);
            ShortMessage mess = new ShortMessage();
            mess.setMessage(ShortMessage.NOTE_OFF, 0, note, 93);
            long timeStamp = -1;
            receiver.send(mess, TIMESTAMP);
        } catch (InvalidMidiDataException ex) {
            logException(ex);
        }

    }

    public void playNote(int note, int instrument) {
        int velocity = 100;
        try {
            if (VERBOSE) log("MidiPlayer.playNote: ", note);
            ShortMessage mess = new ShortMessage();
            //mess.setMessage(ShortMessage.NOTE_ON, 0, 63, 93);
            mess.setMessage(ShortMessage.NOTE_ON, 0, note, velocity);
            ShortMessage instrumentMessage = new ShortMessage();
            instrumentMessage.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
            long timeStamp = -1;
            receiver.send(instrumentMessage, TIMESTAMP);
            receiver.send(mess, TIMESTAMP);
            Thread.sleep(3000);
            System.out.println("woke up");
            ShortMessage noteOff = new ShortMessage();
            noteOff.setMessage(ShortMessage.NOTE_OFF, 0, note,velocity);
            receiver.send(noteOff, TIMESTAMP);
        } catch (InvalidMidiDataException ex) {
            logException(ex);
            //Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Instrument[] getLoadedInstruments() throws MidiUnavailableException {
        log("MidiPlayer.getLoadedInstruments");
        return synthesizer.getLoadedInstruments();
    }

    public void send(ShortMessage message) {
        log("MidiPlayer.send(ShortMessage)");
        receiver.send(message, -1);
    }
}
