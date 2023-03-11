package midi;

import lib.MidiLogger;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import static lib.MidiLogger.log;


public class SoutReceiver implements Receiver {
    private Metronome.Callback callback;
    public SoutReceiver(Metronome.Callback callback){
        this.callback = callback;

    }
    @Override
    public void send(MidiMessage message, long timeStamp) {
        System.out.println(message.toString());
        if( message instanceof ShortMessage){
            log("timestamp", timeStamp);
            MidiLogger.log((ShortMessage) message);
/*            if( ((ShortMessage)message).getCommand() == ShortMessage.NOTE_ON){
                System.out.println("a note on message");
            }else{
                System.out.println("command: " + ((ShortMessage)message).getCommand());
            }*/
            //int data1 = ((ShortMessage)message).getData1();
        }/*else{
            System.out.println(message.getClass().getName());
        }*/
    }
    @Override
    public void close() {

    }
    public boolean isNoteOnMessage(ShortMessage message){
        return false;
    }
}
