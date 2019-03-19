package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Client {

	private ClientChatUI CCUI;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private MessageThread messageThread;// thread for receiving messages
	private String name = "Null";

	private ArrayList<StringBuffer> contentList = new ArrayList<StringBuffer>();
	private String currentTabName = "Public";


	// send messages
	public void send() {
		String message = CCUI.getTextField().getText().trim();
		if (message == null || message.equals("")) {
			JOptionPane.showMessageDialog(CCUI.getFrame(), "message can't be null!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// sendMessage(frame.getTitle() + "@" + "ALL" + "@" + message);
		
		// send public messages
		if (currentTabName.equals("Public")) {
			sendMessage("Public@" + this.name + "@ALL@" + message);
		} else {
			sendMessage("Private@" + name + "@" + currentTabName + "@" + message);
		}
		CCUI.getTextField().setText(null);
	}
	
	/**
	 * Constructor
	 * @param ip
	 * @param port      
	 * @param nickName
	 *           
0	 
	 * @throws Exception */
	public Client(Socket socket, String nickName) throws Exception {
		this.CCUI=new ClientChatUI();
		contentList.add(new StringBuffer());
		// connect to server
		if (connectServer(socket, nickName)) {
			CCUI.getFrame().setTitle("Client - " + nickName);
			this.name = nickName;
		} else {
			System.err.println("Fail to connect to the server");
			System.exit(0);
		}

		// press "Enter" to send messages
		CCUI.getTextField().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send();
			}
		});

		// press button to send messages
		CCUI.getBtn_send().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		

		// close the window
		CCUI.getFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeConnection();
				System.exit(0);
			}
		});

		// choose users to chat
		CCUI.getUserList().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String val = (String) (((JList) e.getSource()).getSelectedValue());
				System.out.println(val);
				if (!currentTabName.equals(val)) {
					currentTabName = val;
					addMessage(val, "");
				}
			}
		});

		// change the tabs
		CCUI.getTabs().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				JTabbedPane p = (JTabbedPane) ce.getSource();
				int idx = p.getSelectedIndex();
				currentTabName = p.getTitleAt(idx);
				CCUI.getTextArea().setText(contentList.get(idx).toString());
			}
		});
		
		//get chatting history
		CCUI.getBtn_get().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				 new HistoryUI(name,currentTabName);
			
			}
		});
		
		//get user's profile
		CCUI.getProfile().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				 if(!currentTabName.equals("Public")) {
					//new UserProfileUI(currentTabName);
					writer.println("Profile@"+currentTabName);
					writer.flush();
				 }
				 else {
					 JOptionPane.setDefaultLocale(Locale.ENGLISH);
				    JOptionPane.showMessageDialog(null, "Please select a user!", "",JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

	/**
	 * connecting to the server
	 * 
	 * @param port
	 * @param hostIp
	 * @param name
	 */
	public boolean connectServer(Socket socket, String name) {
		try {
			
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// when the user is online
			// sendMessage(name + "@" + socket.getLocalAddress().toString());
			sendMessage("Logon@" + name);
			// start the messages thread
			messageThread = new MessageThread(reader, CCUI.getTextArea(), CCUI, this);
			messageThread.start();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * send the messages to the server
	 * @param message
	 */
	public void sendMessage(String message) {
		writer.println(message);
		writer.flush();
	}

	/**
	 * close connection to server for client
	 */
	public synchronized boolean closeConnection() {
		try {
			sendMessage("Logout");// send "Logout" to the server
			messageThread.stop();// stop the thread
			
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
			if (socket != null) {
				socket.close();
			}
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/**
	 * chat with a user with a new tab
	 * @param r
	 * @param msg
	 */
	public void addMessage(String r, String msg) {
		int i=0;
		for (; i < CCUI.getTabs().getTabCount(); i++) {
			if (CCUI.getTabs().getTitleAt(i).equals(r)) {
				contentList.get(i).append(msg);
				break;
			}
		}

		if (i == CCUI.getTabs().getTabCount()) {
			JLabel tips = new JLabel("Chatting with " + r);  
			tips.setOpaque(true);
			tips.setBackground(new Color(0,250,154)); 
			CCUI.getTabs().addTab(r, tips);
			StringBuffer sb = new StringBuffer();
			sb.append(msg);
			contentList.add(sb);
			CCUI.getTabs().setSelectedIndex(CCUI.getTabs().getTabCount() - 1);
		}

		currentTabName = r;
		showMessage();
	}
	
	/**
	 * show the messages in the textarea which correspond to the user
	 */
	public void showMessage() {
		for (int i = 0; i < CCUI.getTabs().getTabCount(); i++) {
			if (CCUI.getTabs().getTitleAt(i).equals(currentTabName)) {
				CCUI.getTextArea().setText(contentList.get(i).toString());
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public String getName() {
		return name;
	}
	public ArrayList<StringBuffer> getContentList() {
		return contentList;
	}

	public String getCurrentTabName() {
		return currentTabName;
	}
	
	
}
