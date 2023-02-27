package midi;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.swing.plaf.synth.SynthButtonUI;

public class TestMyReceiver {
    private MyReceiver myReceiver = new MyReceiver();
    public TestMyReceiver(){
        System.out.println("TestMyReceiver()");
        try {
            init();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    private void init() throws MidiUnavailableException {
        System.out.println("init()");
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        //Transmitter transmitter = synthesizer.getTransmitter();
        //ransmitter.setReceiver(myReceiver);
        //synthesizer.get
        synthesizer.getChannels()[10].noteOn(60, 90);
        //synthesizer.


    }
}
