package server;
import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class ServerUI {

	private JFrame frame;
	private JTextArea contentArea;
	private JScrollPane rightPanel;
	private JScrollPane leftPanel;
	private JSplitPane centerSplit;
	private JList userList;
	private DefaultListModel listModel;
	
	public ServerUI() {
		frame = new JFrame("Server");
		contentArea = new JTextArea();
		contentArea.setEditable(false);
		listModel = new DefaultListModel();
		userList = new JList(listModel);

		rightPanel = new JScrollPane(userList);
		rightPanel.setBorder(new TitledBorder("Online users"));

		leftPanel = new JScrollPane(contentArea);
		leftPanel.setBorder(new TitledBorder("Message"));

		centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		centerSplit.setDividerLocation(400);

		frame.setLayout(new BorderLayout());
		frame.add(centerSplit, "Center");
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextArea getContentArea() {
		return contentArea;
	}

	public JScrollPane getRightPanel() {
		return rightPanel;
	}

	public JScrollPane getLeftPanel() {
		return leftPanel;
	}

	public JSplitPane getCenterSplit() {
		return centerSplit;
	}

	public JList getUserList() {
		return userList;
	}

	public DefaultListModel getListModel() {
		return listModel;
	}
	
	
}
