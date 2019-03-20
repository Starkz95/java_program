package client;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;


public class NewGroupChatUI {

    private JFrame frame;
    private JPanel northPanel;
    private JPanel southPanel;
    private JScrollPane leftScrollPane;
    private JScrollPane rightScrollPane;
    private JLabel groupChatNameLabel;
    private JTextField groupChatNameTextField;
    private JList leftList;
    private JList rightList;
    private DefaultListModel<String> leftListModel;
    private DefaultListModel<String> rightListModel;
    private JButton crateNewGroupChat;
    private JButton chose;

    public NewGroupChatUI() {

        crateNewGroupChat = new JButton("Start New Group Chat!");
        chose = new JButton("=>Chose=>");
        groupChatNameLabel = new JLabel("New Group Chat Name:");
        groupChatNameTextField = new JTextField();
        leftListModel = new DefaultListModel();
        rightListModel = new DefaultListModel();
        leftList = new JList(leftListModel);
        leftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        rightList = new JList(rightListModel);

        leftScrollPane = new JScrollPane(leftList);
        leftScrollPane.setBorder(new TitledBorder("Chose users"));
        leftScrollPane.getViewport().setOpaque(false);
        leftScrollPane.setSize(300, 500);
        rightScrollPane = new JScrollPane(rightList);
        rightScrollPane.setBorder(new TitledBorder("Selected users"));
        rightScrollPane.getViewport().setOpaque(false);
        rightScrollPane.setMaximumSize(new Dimension(200, 200));

        northPanel = new JPanel(new BorderLayout());
        northPanel.add(groupChatNameLabel, BorderLayout.NORTH);
        northPanel.add(groupChatNameTextField, BorderLayout.SOUTH);
        southPanel = new JPanel(new FlowLayout());
        southPanel.add(crateNewGroupChat);

        frame = new JFrame("New Group Chat");
        frame.setLayout(new BorderLayout());
        frame.add(leftScrollPane, BorderLayout.WEST);
        frame.add(rightScrollPane, BorderLayout.EAST);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.add(chose, BorderLayout.CENTER);

        chose.addActionListener(e -> {
            rightListModel.removeAllElements();
            ArrayList<String> users = new ArrayList<>(leftList.getSelectedValuesList());
            for (String user :
                    users) {
                rightListModel.addElement(user);
            }
        });


        frame.setSize(650, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        new NewGroupChatUI();
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getNorthPanel() {
        return northPanel;
    }

    public JPanel getSouthPanel() {
        return southPanel;
    }

    public JScrollPane getLeftScrollPane() {
        return leftScrollPane;
    }

    public JScrollPane getRightScrollPane() {
        return rightScrollPane;
    }

    public JLabel getGroupChatNameLabel() {
        return groupChatNameLabel;
    }

    public JTextField getGroupChatNameTextField() {
        return groupChatNameTextField;
    }

    public JList getLeftList() {
        return leftList;
    }

    public JList getRightList() {
        return rightList;
    }

    public JButton getCrateNewGroupChat() {
        return crateNewGroupChat;
    }

    public JButton getChose() {
        return chose;
    }

    public DefaultListModel<String> getLeftListModel() {
        return leftListModel;
    }

    public DefaultListModel<String> getRightListModel() {
        return rightListModel;
    }
}
