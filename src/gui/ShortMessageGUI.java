package gui;

import logger.CRBLogger;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

import static logger.CRBLogger.log;

public class ShortMessageGUI extends JFrame {
    private enum Commands{
        NOTE_ON , NOTE_OFF, PROGRAM_CHANGE, CONTROL_CHANGE;

        public static int getInt(Commands command) {
            switch (command){
                case NOTE_ON:
                    return 144;
                case NOTE_OFF:
                    return 128;
                case CONTROL_CHANGE:
                    return 176;
                case PROGRAM_CHANGE:
                    return 192;
            }
            return 0;
        }
    }
    private Receiver receiver;
    private JPanel northPanel;
    private JButton jButtonSend = new JButton("send");
    //private JTextField jTextField_command = new JTextField(5);
    private JComboBox comboBox_commands;
    private JTextField jTextField_channel = new JTextField(5);
    private JTextField jTextField_data1 = new JTextField(5);
    private JTextField jTextField_data2 = new JTextField(5);
    public ShortMessageGUI(){

        setSize(600, 400);
        setTitle("short message demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setNorthPanel();
        try {
            receiver = MidiSystem.getReceiver();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        setVisible(true);

    }

    private void setNorthPanel() {
        northPanel = new JPanel();
        northPanel.setBackground(Color.LIGHT_GRAY);
        northPanel.setPreferredSize(new Dimension(400, 100));
        comboBox_commands = new JComboBox(Commands.values());
        comboBox_commands.addActionListener(e->onCommandChange());
        northPanel.add(comboBox_commands);
        //northPanel.add(jTextField_command);
        northPanel.add(jTextField_channel);
        northPanel.add(jTextField_data1);
        northPanel.add(jTextField_data2);
        jButtonSend.addActionListener(e->sendMessage());
        northPanel.add(jButtonSend);
        add(northPanel, BorderLayout.NORTH);

    }

    private void onCommandChange() {
        log("ShortMessageGUI.onCommandChange()");
        Commands command = (Commands) comboBox_commands.getSelectedItem();
        log("command", command.toString());
    }

    private void sendMessage() {
        log("ShortMessageGUI.sendMessage()");
        //int command = Integer.parseInt(jTextField_command.getText());
        int command = Commands.getInt((Commands) comboBox_commands.getSelectedItem());
        int channel = Integer.parseInt(jTextField_channel.getText());
        int data1 = Integer.parseInt(jTextField_data1.getText());
        int data2 = Integer.parseInt(jTextField_data2.getText());
        log("command", command);
        log("channel", channel);
        log("data1", data1);
        log("data2", data2);
        ShortMessage shortMessage = new ShortMessage();
        try {
            shortMessage.setMessage(command, channel, data1, data2);
            receiver.send(shortMessage, 0);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }

    }
}
