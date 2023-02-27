package midi;

import javax.sound.midi.*;

public class Drums {
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    private MyReceiver myReceiver;
    public Drums(){
        System.out.println("Drums() ctor");
        try {
            init();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }
    private void init() throws MidiUnavailableException, InvalidMidiDataException {
        System.out.println("Drums.init()");
        synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        myReceiver = new MyReceiver();
        int num_notes = 10;
        boolean running = true;
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        /*
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
        int note = 35;
        int tick = 0;
        for(int i = 5; i < (4 * num_notes) + 5; i += 4 ){
            note = (note == 35) ? 38: 35;
            track.add(MidiUtil.createMidiEvent(ShortMessage.NOTE_ON, 9, note, 100, i));
            track.add(MidiUtil.createMidiEvent(ShortMessage.NOTE_OFF, 9, note, 100, i+2));
            sequencer.setSequence(sequence);
        }*/
        sequencer.setSequence(SequenceFactory.getDrumSeq(10));
        sequencer.setTempoInBPM(100);
        //sequencer.setLoopStartPoint(5);
        //sequencer.setLoopEndPoint(8);
        //sequencer.setLoopCount(10);
        Transmitter transmitter = sequencer.getTransmitter();
        transmitter.setReceiver(myReceiver);

    }

    public void start() throws MidiUnavailableException, InvalidMidiDataException {
        System.out.println("Drums.start()");
        sequencer.start();
        /*
        while ( running){
            if( !sequencer.isRunning()){
                sequencer.close();
                running = false;
            }
        }

         */
    }
    public MyReceiver getMyReceiver(){
        return myReceiver;
    }

    public void trySynthesizer(){
        System.out.println("trySynthesizer()");
        MidiChannel[] channels = synthesizer.getChannels();
        channels[9].noteOn(60, 90);
    }

}
