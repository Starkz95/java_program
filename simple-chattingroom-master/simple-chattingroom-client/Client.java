
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
import java.util.ArrayList;

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
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Client {

	private JFrame frame;
	private JList userList;
	private JTextArea textArea;
	private JTextField textField;
	private JButton btn_send;
	private JPanel southPanel;
	private JScrollPane rightScroll;
	private JScrollPane leftScroll;
	private JSplitPane centerSplit;
	private JTabbedPane tabs;
	private JPanel leftPanel;

	private DefaultListModel listModel;

	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private MessageThread messageThread;// 负责接收消息的线程
	private String name = "Null";

	private ArrayList<StringBuffer> contentList = new ArrayList<StringBuffer>();
	private String currentTabName = "Public";

	// 主方法,程序入口
	public static void main(String[] args) {
//		if (args.length == 3) {
//			String ip = args[0];
//			int port = Integer.parseInt(args[1]);
//			String nickName = args[2];
//			new Client(ip, port, nickName);
//		} else {
//			System.err.println("启动方式：java -jar client.jar server_ip server_port nickname");
//		}
		new Client("localhost", 8888, "jsi");

	}

	// 执行发送
	public void send() {
		String message = textField.getText().trim();
		if (message == null || message.equals("")) {
			JOptionPane.showMessageDialog(frame, "消息不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// sendMessage(frame.getTitle() + "@" + "ALL" + "@" + message);
		// 发送群聊消息
		if (currentTabName.equals("Public")) {
			sendMessage("Public@" + this.name + "@ALL@" + message);
		} else {
			sendMessage("Private@" + name + "@" + currentTabName + "@" + message);
		}
		textField.setText(null);
	}

	/**
	 * 构造方法
	 * 
	 * @param ip
	 *            服务器ip
	 * @param port
	 *            端口
	 * @param nickName
	 *            昵称
0	 */
	public Client(String ip, int port, String nickName) {
		// 创建组件
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setForeground(Color.blue);
		textField = new JTextField();
		btn_send = new JButton("发送");
		listModel = new DefaultListModel();
		userList = new JList(listModel);

		leftScroll = new JScrollPane(textArea);
		leftScroll.setBorder(new TitledBorder("消息显示区"));
		rightScroll = new JScrollPane(userList);
		rightScroll.setBorder(new TitledBorder("在线用户"));
		southPanel = new JPanel(new BorderLayout());
		southPanel.add(textField, BorderLayout.CENTER);
		southPanel.add(btn_send, BorderLayout.EAST);
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

		contentList.add(new StringBuffer());
		// 连接服务器
		if (connectServer(ip, port, nickName)) {
			frame.setTitle("Client - " + nickName);
			this.name = nickName;
		} else {
			System.err.println("服务器连接失败！");
			System.exit(0);
		}

		// 写消息的文本框中按回车键时事件
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send();
			}
		});

		// 单击发送按钮时事件
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		// 关闭窗口时事件
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeConnection();// 关闭连接
				System.exit(0);
			}
		});

		// JList 监听器
		userList.addListSelectionListener(new ListSelectionListener() {

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

		// tabs 监听器
		tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				JTabbedPane p = (JTabbedPane) ce.getSource();
				int idx = p.getSelectedIndex();
				currentTabName = p.getTitleAt(idx);
				textArea.setText(contentList.get(idx).toString());
			}
		});
	}

	/**
	 * 连接服务器
	 * 
	 * @param port
	 * @param hostIp
	 * @param name
	 */
	public boolean connectServer(String hostIp, int port, String name) {
		// 连接服务器
		try {
			socket = new Socket(hostIp, port);
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 用户上线
			// sendMessage(name + "@" + socket.getLocalAddress().toString());
			sendMessage("Login@" + name);
			// 开启接收消息的线程
			messageThread = new MessageThread(reader, textArea);
			messageThread.start();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		writer.println(message);
		writer.flush();
	}

	/**
	 * 客户端主动关闭连接
	 */
	public synchronized boolean closeConnection() {
		try {
			sendMessage("Logout@");// 发送断开连接命令给服务器
			messageThread.stop();// 停止接受消息线程
			// 释放资源
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

	// 不断接收消息的线程
	class MessageThread extends Thread {
		private BufferedReader reader;
		private JTextArea textArea;

		// 接收消息线程的构造方法
		public MessageThread(BufferedReader reader, JTextArea textArea) {
			this.reader = reader;
			this.textArea = textArea;
		}

		// 被动的关闭连接
		public synchronized void closeCon() throws Exception {
			// 清空用户列表
			listModel.removeAllElements();
			// 被动的关闭连接释放资源
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
			if (socket != null) {
				socket.close();
			}
		}

		public void run() {
			String message = "";
			while (true) {
				try {
					message = reader.readLine();
					System.out.println("[Client] " + message);
					String[] sp = message.split("@");

					if (sp[0].equals("Logout@")) {// 服务器要求断开链接
						textArea.append("服务器已关闭!\r\n");
						closeCon();// 被关闭连接

					} else if (sp[0].equals("Add")) { // 新增用户
						String username = sp[1];
						listModel.addElement(username);
					} else if (sp[0].equals("UserList")) { // 用户列表
						String[] usernames = sp[1].split("#");
						for (String username : usernames) {
							if (username.length() == 0) {
								continue;
							}
							listModel.addElement(username);
						}
					} else if (sp[0].equals("Delete")) { // 删除用户
						String username = sp[1];
						listModel.removeElement(username);

					} else if (sp[0].equals("Public")) {// 群聊消息
						String s = sp[1];// 发送者
						// String r = sp[2];// 接收者, 固定为ALL
						String c = sp[3];// 消息内容
						// textArea.append("[群聊]" + s + ":\r\n" + c + "\r\n\r\n");
						addMessage("Public", s + ":\r\n" + c + "\r\n\r\n");
						System.out.println("[群聊]" + s + ": " + c + "\r\n");
					} else if (sp[0].equals("Private")) {
						String s = sp[1];// 发送者
						String r = sp[2];// 接收者, 固定为自己
						String c = sp[3];// 消息内容
						// textArea.append("[私聊]" + s + ":\r\n" + c + "\r\n\r\n");// 暂时放在群聊窗口中
						for (String str : sp) {
							System.out.println(str);
						}
						System.out.println("----");
						String s2 = s.equals(name) ? r : s;
						addMessage(s2, s + ":\r\n" + c + "\r\n");
						System.out.println("[私聊]" + s + ": " + c + "\r\n");
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void addMessage(String r, String msg) {
		int i = 0;
		for (; i < tabs.getTabCount(); i++) {
			if (tabs.getTitleAt(i).equals(r)) {
				contentList.get(i).append(msg);
				break;
			}
		}

		if (i == tabs.getTabCount()) {
			tabs.addTab(r, new JLabel("与" + r + "聊天中"));
			StringBuffer sb = new StringBuffer();
			sb.append(msg);
			contentList.add(sb);
			tabs.setSelectedIndex(tabs.getTabCount() - 1);
		}

		currentTabName = r;
		showMessage();
	}

	private void showMessage() {
		for (int i = 0; i < tabs.getTabCount(); i++) {
			if (tabs.getTitleAt(i).equals(currentTabName)) {
				textArea.setText(contentList.get(i).toString());
			}
		}
	}
}
