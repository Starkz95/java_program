
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
	
	private ServerUI SUI;
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

		this.SUI=new ServerUI();

		// 启动服务器
		try {
			serverStart(port);
			SUI.getContentArea().append("服务器已成功启动，端口：" + port + "\r\n");
		} catch (BindException e1) {
			System.err.println("服务器启动失败，端口占用！");
			System.exit(-1);
		}

		// 关闭窗口时事件
		SUI.getFrame().addWindowListener(new WindowAdapter() {
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
			serverThread = new ServerThread(serverSocket,clients,SUI);
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
				clients.get(i).getReader().close();
				clients.get(i).getWriter().close();
				clients.get(i).getSocket().close();
				clients.remove(i);
			}
			if (serverSocket != null) {
				serverSocket.close();// 关闭服务器端连接
			}
			SUI.getListModel().removeAllElements();// 清空用户列表
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


}
