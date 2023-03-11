package lib;

import logger.CRBLogger;
import midi.MidiUtil;

import javax.sound.midi.*;

public class MidiLogger extends CRBLogger {
    private static boolean debug = true;
    public static void log(Receiver receiver) {
        log("log(Receiver)");
        log("reciver toString" ,receiver.toString());
    }
    public static void log(MidiDevice midiDevice){
        if(!debug){
            return;
        }
        System.out.println("log(MidiDevice()");
        //the device's current notion of time, counting from when it was started
        // or -1 if the device does not support timestamps
        long  time = midiDevice.getMicrosecondPosition();
        midiDevice.getDeviceInfo().getDescription();
    }
    public static void log(Sequence sequence){
        log("log Sequence...");
        Track[] tracks = sequence.getTracks();
        for (Track track: tracks){
            log(track);
        }
        //sequence.
    }
    public static void log(Sequencer sequencer){
        if( !debug){
            return;
        }
        System.out.println("log(Sequencer)");
        if( sequencer instanceof Synthesizer){
            System.out.println("sequencer instance of synthesizer");
        }else{
            System.out.println("just plain old sequender");
        }
        System.out.println("\tisRunning: " + sequencer.isRunning());
        System.out.println("\tBPM: " + sequencer.getTempoInBPM());
        System.out.println("\ttickLength: " + sequencer.getTickLength());
        MidiDevice.Info info = sequencer.getDeviceInfo();

        System.out.println(info.toString());
    }
    public static void log(Track track){
        log("log Track...");
        log("ticks: ", track.ticks());
        log("size: " , track.size());
        for( int i = 0; i < track.size(); i++){
            MidiEvent event = track.get(i);
            log( event.getMessage());
        }
    }


    public static void log(Instrument instrument) {
        System.out.println("instrument " + instrument.getName());
        System.out.format("\tbank: %d program: %d\n", instrument.getPatch().getBank(),
                instrument.getPatch().getProgram());
    }

    public static void log(Instrument[] instruments , String description){
        System.out.println("log(Instrument[]) num instruments: " + instruments.length);
        System.out.println("\tdescription");
        for(int i = 0; i < instruments.length; i++){
            log(instruments[i]);
        }
    }
    public static void logInstruments(Instrument[] instruments){
        log("log Instrument[]");
        for(int i = 0; i < instruments.length; i++){
            System.out.println(i + ": " + instruments[i]);
        }


    }

    public static void log(Soundbank soundbank, String description) {
        System.out.println("log(Soundbank), String");
        System.out.format("\tname: %s, description: %s\n", soundbank.getName(), soundbank.getDescription());
        log(soundbank.getInstruments(), description);

    }

    public static void log(MidiDevice.Info[] midiDeviceInfo, String description) {
        System.out.format("log MidiDevice.Info[] size %d, %s\n", midiDeviceInfo.length, description);
        for( int i = 0; i < midiDeviceInfo.length; i++){
            log(midiDeviceInfo[i]);
        }
    }

    private static void log(MidiDevice.Info info) {
        System.out.format("\tMidiDevice.Info name %s description %s\n", info.getName(), info.getDescription());
        System.out.format("\tvendor: %s version:  %s\n", info.getVendor(), info.getVersion());
        //info.
    }
    public static void log(ShortMessage sm){
        log("log ShortMessage...");
        System.out.format("\tch %d, cmd %s (%d), data1 %d, data2 %d\n", sm.getChannel(), MidiUtil.commandToString(sm.getCommand()),
                    sm.getCommand(), sm.getData1(), sm.getData2());
        log("status byte" , Integer.toBinaryString(sm.getStatus()));
        log("type of message", (sm.getStatus() >> 4) & 0b00000111);
        log("channel", sm.getStatus() & 0b00001111);

    }
    public static void log(MidiMessage message) {
        log("log MidiMessage ");
        if( message.getStatus() == MetaMessage.META){
            System.out.println("META MESSAGE");
        }
        //System.out.println(message.toString());
        ShortMessage shortMessage = new ShortMessage();
        if ( message instanceof ShortMessage){
            System.out.println("instance of ShortMessage");
            ShortMessage sm = (ShortMessage) message;
            log(sm);
        }
        byte[] bytes = message.getMessage();
        for( int i = 0; i < bytes.length; i++){
            System.out.format("\t byte[%d]: %d\n", i , bytes[i]);
        }
    }

    public static void log(String string) {
        if( !debug){
            return;
        }
        System.out.println(string);
    }
}
