package midi;

import lib.MidiLogger;

import javax.sound.midi.*;
import java.awt.event.KeyEvent;

public class Playground {
    private Receiver synthReceiver;
    private Sequencer sequencer;
    private Transmitter sequencer_transmitter;
    private Synthesizer synthesizer;
    //private int channelIndex = 0;

    public Playground(){
        System.out.println("midi.Playground()");
        //new StackOverflow();
        //synthesizerChannels();
        //listLoadedInstruments();
        /*
        try {
            synthesizer = MidiSystem.getSynthesizer();
            //getSynthesizerDefaultSoundbank();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
        exploreMidiSystem();

         */
    }
    public void listLoadedInstruments(){
        System.out.println("listLoadedInstruments()");
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            Instrument[] instruments = synthesizer.getLoadedInstruments();
            MidiLogger.log(instruments, "synth loaded instruments");
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public void listAvailableInstruments(){
        System.out.println("listAvailableInstruments()");
        try {
            synthesizer = MidiSystem.getSynthesizer();
            Instrument[] instruments = synthesizer.getAvailableInstruments();
            MidiLogger.log(instruments, "synth available instruments");
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public void exploreMidiSystem(){
        System.out.println("exploreMidiSystem()");
        MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
        MidiLogger.log(midiDeviceInfo, "MidiSystem.getMidiDeviceInfo()");

    }

    public void testSynthesizer(){
        System.out.println("testSynthesizer()");
        Synthesizer s = null;
        try {
            s = MidiSystem.getSynthesizer();
            //s.loadInstrument()
            Instrument[] instruments = s.getAvailableInstruments();
            for( int i = 0; i < instruments.length; i++){
                System.out.format("\tinstrument[%d] = %s\n", i ,instruments[i].getName());
                //instruments[i].getPatch().getProgram();
            }
            Instrument applause = instruments[126];
            if( s.loadInstrument(applause)){
                System.out.println("\tapplause loaded");
            }
            Receiver syntRec = s.getReceiver();
            //syntRec.
            syntRec.send(getMidiMessage(), -1);
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }

    }
    public void getSynthesizerDefaultSoundbank(){
        System.out.println("getSynthesizerDefaultSoundbank");
        if( synthesizer == null){
            try {
                synthesizer = MidiSystem.getSynthesizer();
            } catch (MidiUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
        Soundbank defaultSoundbank = synthesizer.getDefaultSoundbank();
        MidiLogger.log(defaultSoundbank, "synth default soundbank");
    }
    private ShortMessage getMidiMessage() throws InvalidMidiDataException {
        ShortMessage mess = new ShortMessage();
        mess.setMessage(ShortMessage.NOTE_ON, 0, 63, 93);
        return mess;

    }
    public void synthesizerPlayNoteChannel(int channelIndex){
        System.out.format("synthesizerPlayNoteChannel(%d)\n", channelIndex);
        try {
            if ( synthesizer == null) {
                synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();
            }
            /*
            MidiChannel channels[] = syn.getChannels();
            for(int i = 0; i < channels.length; i++){
                MidiChannel channel = channels[i];
                if( channel != null){
                    System.out.format("channel[%d] %s \n", i, channel.toString());
                }
            }

             */
            synthesizer.getChannels()[2].programChange(12);
            System.out.println("\tsound on channel " + channelIndex);
            synthesizer.getChannels()[channelIndex].noteOn(60, 90);
            //channels[channelIndex++].noteOn(60, 90);
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public  void changeProgram(){
        System.out.println("changeProgram()");
        if( synthesizer == null){
            try {
                synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();

            } catch (MidiUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
        synthesizer.getChannels()[3].programChange(0, 56);
        synthesizer.getChannels()[3].noteOn(69, 90);
        System.out.println("sound?");

    }
    public void synthesizerChannels2(){
        System.out.println("synthesizerChannels2()");
        try {
            Synthesizer syn = MidiSystem.getSynthesizer();
            syn.open();
            ShortMessage shortMessage = new ShortMessage();
            shortMessage.setMessage(ShortMessage.NOTE_ON, 5, 60,93);
            Receiver receiver = syn.getReceiver();
            receiver.send(shortMessage, -1);
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }
    public void whatever(){
        System.out.println("mess to seq to synth, at least thats what i think");
        try {
            sequencer = MidiSystem.getSequencer();
            //sequencer.
            //Debug.log(sequencer);
            sequencer_transmitter = sequencer.getTransmitter();
            synthesizer = MidiSystem.getSynthesizer();
            synthReceiver = synthesizer.getReceiver();
            //ShortMessage mess = new ShortMessage();
            //mess.setMessage(ShortMessage.NOTE_ON, 0, 63, 93);
            //synthReceiver.send(mess, -1);


            sequencer_transmitter.setReceiver(synthReceiver);
            Receiver seqRec = sequencer.getReceiver();

        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public void playNote(KeyEvent e){
        System.out.println("playNote(KeyEvent e)");
        int note = 63;
        switch( e.getKeyCode()){
            case KeyEvent.VK_C:note = 63;break;
            case KeyEvent.VK_D:note = 65;break;
            case KeyEvent.VK_E:note = 67;break;
            case KeyEvent.VK_F:note = 68;break;
            case KeyEvent.VK_G:note = 70;break;
            case KeyEvent.VK_A:note = 72;break;
            case KeyEvent.VK_B:note = 74;break;
        }
        playNote(note);
    }
    public void playNote(int note){
        /*
        VK_C = 67
        VK_D = 68
        VK_E = 69
        VK_F = 70
        VK_G = 71
        VK_A = 65
        VK_B = 66
         */
        try {
            System.out.println("playNote");
            ShortMessage mess = new ShortMessage();
            mess.setMessage(ShortMessage.NOTE_ON, 0, note, 93);
            long timeStamp = -1;
            Receiver receiver = MidiSystem.getReceiver();
            MidiLogger.log(receiver);
            receiver.send(mess, timeStamp);
            System.out.println("message sent");
        } catch (InvalidMidiDataException | MidiUnavailableException ex) {
            System.out.println(ex.toString());
            //Logger.getLogger(CFEarTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            System.out.println("finally");
        }
    }
    public void oneMessageMultipleReceivers(){
        System.out.println("oneMessageMultipleReceivers()");
        /*
        try{

            Synthesizer synth = MidiSystem.getSynthesizer();
            // inputPort = synth.g
            Sequencer siq;
            Transmitter inputPortTrans1, inputPortTrans2;
            Receiver receiverSynth;
            Receiver receiverSeq;
            inputPortTrans1 = inputPort.getTransmitter();
            synthReceiver = synth.getReceiver();
            inputPortTrans1.setReceiver(synthReceiver);
            inputPortTrans2 = inputPort.getTransmitter();
            receiverSeq = sequencer.getReceiver();
            inputPortTrans2.setReceiver(receiverSeq);



        }catch (MidiUnavailableException e){

        }
*/
    }
}
