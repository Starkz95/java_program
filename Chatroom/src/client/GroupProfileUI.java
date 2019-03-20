package client;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import server.DBHelper;

public class GroupProfileUI {
    private JFrame frame = new JFrame("Group's profile");
    private JPanel panel = new JPanel();
    private JLabel groupLabel = new JLabel("Group's name:");
    private JLabel groupname = new JLabel();
    private JLabel groupmembersLabel = new JLabel("Group's members:");
    private JList groupmembers;
    private DefaultListModel groupmembersModel = new DefaultListModel();
    private JScrollPane scrollPane;

    public GroupProfileUI() {

        frame.setSize(320, 450);
        panel.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);
        init();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() {
        groupLabel.setBounds(10, 20, 80, 25);
        panel.add(groupLabel);
        groupname.setBounds(150, 20, 165, 25);
        panel.add(groupname);
        groupmembersLabel.setBounds(10,50,100,25);
        panel.add(groupmembersLabel);
        groupmembers = new JList(groupmembersModel);
        scrollPane = new JScrollPane(groupmembers);
        scrollPane.setBounds(10,80,245,300);
        panel.add(scrollPane);
    }


    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getGroupLabel() {
        return groupLabel;
    }

    public JLabel getGroupname() {
        return groupname;
    }

    public JLabel getGroupmembersLabel() {
        return groupmembersLabel;
    }

    public JList getGroupmembers() {
        return groupmembers;
    }

    public DefaultListModel getGroupmembersModel() {
        return groupmembersModel;
    }

    public static void main(String[] args) {
        new GroupProfileUI();
    }
}
