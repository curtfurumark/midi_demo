package midi;

import javax.sound.midi.*;

public class StackOverflow {
    private Sequencer sequencer;
    private boolean running = true;
    public StackOverflow(){
        try {
            init(10);
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }


    public void init(int num_notes) throws MidiUnavailableException, InvalidMidiDataException {
        System.out.println("init(int) num notes" + num_notes);
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();
        for(int i = 5; i < (4 * num_notes) + 5; i += 4 ){
            track.add(createMidiEvent(ShortMessage.NOTE_ON, 1, i, 100, i));
            track.add(createMidiEvent(ShortMessage.NOTE_OFF, 1, i, 100, i+2));
        }
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(100);
        sequencer.start();
        while ( true){
            if( !sequencer.isRunning()){
                sequencer.close();
                running = false;
            }
        }
        //System.out.println("sequence played");
    }

    private MidiEvent createMidiEvent(int command, int channel, int note, int velocity, int tick) throws InvalidMidiDataException {
        System.out.format("command %d, channel %d, note %d, velocity %d tick %d\n", command, channel, note, velocity, tick);
        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setMessage(command, channel, note, velocity);
        return  new MidiEvent(shortMessage, tick);
    }
}
