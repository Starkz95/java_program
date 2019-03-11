
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

	private ClientChatUI CCUI;
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
		new Client("localhost", 8888, "ziqi");

	}

	// 执行发送
	public void send() {
		String message = CCUI.getTextField().getText().trim();
		if (message == null || message.equals("")) {
			JOptionPane.showMessageDialog(CCUI.getFrame(), "消息不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// sendMessage(frame.getTitle() + "@" + "ALL" + "@" + message);
		// 发送群聊消息
		if (currentTabName.equals("Public")) {
			sendMessage("Public@" + this.name + "@ALL@" + message);
		} else {
			sendMessage("Private@" + name + "@" + currentTabName + "@" + message);
		}
		CCUI.getTextField().setText(null);
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
	
	public Client() {
		
		
	}
	public Client(String ip, int port, String nickName) {
		this.CCUI=new ClientChatUI();
		contentList.add(new StringBuffer());
		// 连接服务器
		if (connectServer(ip, port, nickName)) {
			CCUI.getFrame().setTitle("Client - " + nickName);
			this.name = nickName;
		} else {
			System.err.println("服务器连接失败！");
			System.exit(0);
		}

		// 写消息的文本框中按回车键时事件
		CCUI.getTextField().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send();
			}
		});

		// 单击发送按钮时事件
		CCUI.getBtn_send().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		
		CCUI.getBtn_get().addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				
				HistoryUI hisUI = new HistoryUI();

			}
		});

		// 关闭窗口时事件
		CCUI.getFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeConnection();// 关闭连接
				System.exit(0);
			}
		});

		// JList 监听器
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

		// tabs 监听器
		CCUI.getTabs().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				JTabbedPane p = (JTabbedPane) ce.getSource();
				int idx = p.getSelectedIndex();
				currentTabName = p.getTitleAt(idx);
				CCUI.getTextArea().setText(contentList.get(idx).toString());
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
			messageThread = new MessageThread(reader, CCUI.getTextArea(), CCUI, this);
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
			sendMessage("Logout");// 发送断开连接命令给服务器
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


	public void addMessage(String r, String msg) {
		int i=0;
		for (; i < CCUI.getTabs().getTabCount(); i++) {
			if (CCUI.getTabs().getTitleAt(i).equals(r)) {
				contentList.get(i).append(msg);
				break;
			}
		}

		if (i == CCUI.getTabs().getTabCount()) {
			CCUI.getTabs().addTab(r, new JLabel("与" + r + "聊天中"));
			StringBuffer sb = new StringBuffer();
			sb.append(msg);
			contentList.add(sb);
			CCUI.getTabs().setSelectedIndex(CCUI.getTabs().getTabCount() - 1);
		}

		currentTabName = r;
		showMessage();
	}

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

	public String getCurrentTabName() {
		return currentTabName;
	}

	
}
