package gui;

import javax.swing.*;
import java.awt.*;

import static logger.CRBLogger.log;

public class MidiPanel extends JPanel {
    private Button button_play = new Button("play");
    private Button button_stop = new Button("stop");
    private JPanel panel_west = new JPanel();
    public MidiPanel(){
        log("MidiPanel() constructor");
        panel_west.setPreferredSize(new Dimension(100, 100));
        panel_west.setBackground(Color.BLUE);
        this.setLayout(new BorderLayout());
        this.add(panel_west, BorderLayout.WEST);
        this.setVisible(true);
        //this.add(button_stop, BorderLayout.WEST);

    }
}
