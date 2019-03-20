package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

public class ClientChatUI {

	private JFrame frame;
	private JList userList;
	private JList groupList;
	private JTextArea textArea;
	private JTextField textField;
	private JButton btn_send;
	private JPanel southPanel;
	private JPanel rightDownPane;
	private JScrollPane rightUpScroll;
	private JScrollPane rightDownScroll;
	private JScrollPane leftScroll;
	private JSplitPane centerSplit;
	private JSplitPane rightSplit;
	private JTabbedPane tabs;
	private JPanel leftPanel;
	private DefaultListModel userListModel;
	private DefaultListModel groupListModel;
	private JButton newGroupChatButton;
	private JButton exitButton;
	private JButton btn_get;
	private JButton profile;
	private ImageIcon img;

	public ClientChatUI() throws Exception {
		
		//javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		img=new ImageIcon("chatting.png");
		textArea = new JTextArea();

		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
		textArea.setForeground(Color.blue);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
		textField = new JTextField();
		btn_send = new JButton("Send");
		btn_get = new JButton("History");
		newGroupChatButton = new JButton("Creat a new Group");
		profile=new JButton("User's profile");
		userListModel = new DefaultListModel();
		userList = new JList(userListModel);
		groupListModel = new DefaultListModel();
		groupList = new JList(groupListModel);
		btn_get.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
		btn_get.setForeground(new Color(46,139,87));
		btn_send.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
		btn_send.setForeground(new Color(255,215,0));
		profile.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
		profile.setForeground(new Color(199,21,133));

	    exitButton=new JButton("X");
	    exitButton.setPreferredSize( new Dimension(20,20));
		
		leftScroll = new JScrollPane(textArea);
		leftScroll.setBorder(new TitledBorder("Message"));
		leftScroll.setOpaque(false);
		leftScroll.getViewport().setOpaque(false);
		rightUpScroll = new JScrollPane(userList);
		rightUpScroll.setBorder(new TitledBorder("Online user"));
		rightDownScroll = new JScrollPane(groupList);
		rightDownScroll.setBorder(new TitledBorder("Joined Groups"));

		rightDownPane = new JPanel(new BorderLayout());
		rightDownPane.add(newGroupChatButton,BorderLayout.NORTH);
		rightDownPane.add(rightDownScroll,BorderLayout.CENTER);

		southPanel = new JPanel(new BorderLayout());
		southPanel.add(btn_get, BorderLayout.WEST);
		southPanel.add(profile,BorderLayout.SOUTH);
		southPanel.add(textField, BorderLayout.CENTER);
		southPanel.add(btn_send, BorderLayout.EAST);
		southPanel.setBorder(new TitledBorder("Send message"));

		tabs = new JTabbedPane();
		JLabel tips = new JLabel("Public chat");  
		tips.setOpaque(true);
		tips.setBackground(new Color(0,250,154));
		tabs.addTab("Public", tips);
		leftPanel = new JPanel(new BorderLayout()){

            {
                setOpaque(false);
            } // instance initializer

            public void paintComponent(Graphics g) {
                g.drawImage(img.getImage(), 65, 175, this);
                super.paintComponent(g);
            }
        };
		leftPanel.add(tabs, BorderLayout.NORTH);
		leftPanel.add(leftScroll, BorderLayout.CENTER);

		rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rightUpScroll,rightDownPane);
		rightSplit.setDividerLocation(300);

		centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightSplit);
		centerSplit.setDividerLocation(400);

		frame = new JFrame("Client");
		frame.setLayout(new BorderLayout());
		frame.add(centerSplit, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.setSize(650, 750);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public JList getUserList() {
		return userList;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JButton getBtn_send() {
		return btn_send;
	}

	public JPanel getSouthPanel() {
		return southPanel;
	}

	public JScrollPane getRightUpScroll() {
		return rightUpScroll;
	}

	public JScrollPane getLeftScroll() {
		return leftScroll;
	}

	public JSplitPane getCenterSplit() {
		return centerSplit;
	}

	public JTabbedPane getTabs() {
		return tabs;
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public DefaultListModel getUserListModel() {
		return userListModel;
	}
	
	public JButton getExitButton() {
		return exitButton;
	}

	public JButton getBtn_get() {
		return btn_get;
	}

	public JButton getProfile() {
		return profile;
	}

	public DefaultListModel getGroupListModel() {
		return groupListModel;
	}

	public JList getGroupList() {
		return groupList;
	}

	public JButton getNewGroupChatButton() {
		return newGroupChatButton;
	}
}
