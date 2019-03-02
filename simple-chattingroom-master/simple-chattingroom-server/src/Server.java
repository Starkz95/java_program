
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class Server {

	private JFrame frame;
	private JTextArea contentArea;
	private JScrollPane rightPanel;
	private JScrollPane leftPanel;
	private JSplitPane centerSplit;
	private JList userList;
	private DefaultListModel listModel;

	private ServerSocket serverSocket;
	private ServerThread serverThread;
	private ArrayList<ClientThread> clients;

	private boolean isStart = false;

	// 主方法,程序执行入口
	public static void main(String[] args) {
		if (args.length == 1) {
			// 命令行参数 java -jar server.jar port
			int port = Integer.parseInt(args[0]);
			new Server(port);
		} else {
			new Server(8888);
		}
	}

	// 构造放法
	public Server(int port) {
		// 创建组件
		frame = new JFrame("Server");
		contentArea = new JTextArea();
		contentArea.setEditable(false);
		listModel = new DefaultListModel();
		userList = new JList(listModel);

		rightPanel = new JScrollPane(userList);
		rightPanel.setBorder(new TitledBorder("在线用户"));

		leftPanel = new JScrollPane(contentArea);
		leftPanel.setBorder(new TitledBorder("消息显示区"));

		centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		centerSplit.setDividerLocation(400);

		frame.setLayout(new BorderLayout());
		frame.add(centerSplit, "Center");
		frame.setSize(600, 400);
		frame.setVisible(true);

		// 启动服务器
		try {
			serverStart(port);
			contentArea.append("服务器已成功启动，端口：" + port + "\r\n");
		} catch (BindException e1) {
			System.err.println("服务器启动失败，端口占用！");
			System.exit(-1);
		}

		// 关闭窗口时事件
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (isStart) {
					closeServer();
				}
				System.exit(0);
			}
		});

	}

	// 启动服务器
	public void serverStart(int port) throws java.net.BindException {
		try {
			clients = new ArrayList<ClientThread>();
			serverSocket = new ServerSocket(port);
			serverThread = new ServerThread(serverSocket);
			serverThread.start();
			isStart = true;
		} catch (BindException e) {
			isStart = false;
			throw new BindException("端口号已被占用，请换一个！");
		} catch (Exception e1) {
			e1.printStackTrace();
			isStart = false;
			throw new BindException("启动服务器异常！");
		}
	}

	/**
	 * 关闭服务器socket，断开与客户端的连接
	 */
	public void closeServer() {
		try {
			if (serverThread != null)
				serverThread.stop();// 停止服务器线程

			for (int i = clients.size() - 1; i >= 0; i--) {
				// 给所有在线用户发送关闭命令
				clients.get(i).getWriter().println("CLOSE");
				clients.get(i).getWriter().flush();
				// 释放资源
				clients.get(i).stop();// 停止此条为客户端服务的线程
				clients.get(i).reader.close();
				clients.get(i).writer.close();
				clients.get(i).socket.close();
				clients.remove(i);
			}
			if (serverSocket != null) {
				serverSocket.close();// 关闭服务器端连接
			}
			listModel.removeAllElements();// 清空用户列表
			isStart = false;
		} catch (IOException e) {
			e.printStackTrace();
			isStart = true;
		}
	}

	// 群发服务器消息
	public void sendServerMessage(String message) {
		for (int i = clients.size() - 1; i >= 0; i--) {
			clients.get(i).getWriter().println("服务器：" + message + "(多人发送)");
			clients.get(i).getWriter().flush();
		}
	}

	// 服务器线程
	class ServerThread extends Thread {
		private ServerSocket serverSocket;

		// 服务器线程的构造方法
		public ServerThread(ServerSocket serverSocket) {
			this.serverSocket = serverSocket;
		}

		public void run() {
			while (true) {// 不停的等待客户端的链接
				try {
					Socket socket = serverSocket.accept();
					ClientThread client = new ClientThread(socket);
					client.start();// 开启对此客户端服务的线程
					clients.add(client);
					listModel.addElement(client.getUserName());// 更新在线列表
					contentArea.append(client.getUserName() + " 上线!\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 为一个客户端服务的线程
	class ClientThread extends Thread {
		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;
		private String userName;

		public BufferedReader getReader() {
			return reader;
		}

		public PrintWriter getWriter() {
			return writer;
		}

		public String getUserName() {
			return userName;
		}

		// 客户端线程的构造方法
		public ClientThread(Socket socket) {
			try {
				this.socket = socket;
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream());
				// 接收客户端的基本用户信息
				String inf = reader.readLine();
				// StringTokenizer st = new StringTokenizer(inf, "@");
				// user = new User(st.nextToken(), st.nextToken());
				String[] sp = inf.split("@");
				if (sp.length != 2 || !sp[0].equals("Login")) {
					return;
				}
				userName = sp[1];
				// 反馈连接成功信息
				writer.println("Public@Server@ALL@" + userName + " 与服务器连接成功!");
				writer.flush();

				// 反馈当前在线用户信息
				if (clients.size() > 0) {
					String allUsers = "";
					for (int i = clients.size() - 1; i >= 0; i--) {
						allUsers += (clients.get(i).getUserName() + "#");
					}
					writer.println("UserList@" + allUsers);
					writer.flush();
				}

				// 向所有在线用户发送该用户上线命令
				for (int i = clients.size() - 1; i >= 0; i--) {
					// clients.get(i).getWriter().println("Add@" + clients.get(i).getUserName());
					clients.get(i).getWriter().println("Add@" + userName);
					clients.get(i).getWriter().flush();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			String message = null;
			while (true) {
				try {
					message = reader.readLine();// 接收客户端消息
					if (message.equals("Logout@"))// 下线
					{
						contentArea.append(this.getUserName() + " 下线!\r\n");
						// 断开连接释放资源
						reader.close();
						writer.close();
						socket.close();

						// 向所有在线用户发送该用户的下线命令
						for (int i = clients.size() - 1; i >= 0; i--) {
							clients.get(i).getWriter().println("Delete@" + clients.get(i).getUserName());
							clients.get(i).getWriter().flush();
						}

						listModel.removeElement(userName);// 更新在线列表

						// 删除此条客户端服务线程
						for (int i = clients.size() - 1; i >= 0; i--) {
							if (clients.get(i).getUserName().equals(userName)) {
								ClientThread temp = clients.get(i);
								clients.remove(i);// 删除此用户的服务线程
								temp.stop();// 停止这条服务线程
								return;
							}
						}
					} else {
						dispatcherMessage(message);// 转发消息
					}
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}

		// 转发消息
		public void dispatcherMessage(String message) {
			String[] sp = message.split("@");
			if (sp.length != 4) {
				return;
			}
			if (sp[0].equals("Public")) {// 群聊消息
				// 群发
				String s = sp[1];// 发送者
				// String r = sp[2];// 接收者, 固定为ALL
				String c = sp[3];// 消息内容
				contentArea.append("[群聊]" + s + ": " + c + "\r\n");
				for (int i = clients.size() - 1; i >= 0; i--) {
					clients.get(i).getWriter().println(message);
					clients.get(i).getWriter().flush();
				}
			} else if (sp[0].equals("Private")) { // 私聊消息
				String s = sp[1];// 发送者
				String r = sp[2];// 接收者
				String c = sp[3];// 消息内容
				contentArea.append("[私聊]" + s + " => " + r + ": " + c + "\r\n");
				for (int i = 0; i < clients.size(); i++) {
					if (clients.get(i).getUserName().equals(r) || clients.get(i).getUserName().equals(s)) {
						clients.get(i).getWriter().println(message);
						clients.get(i).getWriter().flush();
					}
				}
			}
		}
	}
}
