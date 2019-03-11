import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClientChatUI {

	private JFrame frame;
	private JList userList;
	private JTextArea textArea;
	private JTextField textField;
	private JButton btn_send;
	private JButton btn_get;
	private JPanel southPanel;
	private JScrollPane rightScroll;
	private JScrollPane leftScroll;
	private JSplitPane centerSplit;
	private JTabbedPane tabs;
	private JPanel leftPanel;
	private DefaultListModel listModel;
	
	public ClientChatUI() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setForeground(Color.blue);
		textField = new JTextField();
		btn_send = new JButton("发送");
		btn_get = new JButton("历史");
		listModel = new DefaultListModel();
		userList = new JList(listModel);

		leftScroll = new JScrollPane(textArea);
		leftScroll.setBorder(new TitledBorder("消息显示区"));
		rightScroll = new JScrollPane(userList);
		rightScroll.setBorder(new TitledBorder("在线用户"));
		southPanel = new JPanel(new BorderLayout());
		southPanel.add(textField, BorderLayout.CENTER);
		southPanel.add(btn_send, BorderLayout.EAST);
		southPanel.add(btn_get, BorderLayout.NORTH);
		southPanel.setBorder(new TitledBorder("写消息"));

		tabs = new JTabbedPane();
		tabs.addTab("Public", new JLabel("群聊中"));
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(tabs, BorderLayout.NORTH);
		leftPanel.add(leftScroll, BorderLayout.CENTER);

		centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightScroll);
		centerSplit.setDividerLocation(400);

		frame = new JFrame("Client");
		frame.setLayout(new BorderLayout());
		frame.add(centerSplit, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.setSize(600, 400);
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

	public JButton getBtn_get() {
		return btn_get;
	}
	
	public JPanel getSouthPanel() {
		return southPanel;
	}

	public JScrollPane getRightScroll() {
		return rightScroll;
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

	public DefaultListModel getListModel() {
		return listModel;
	}
	
}
