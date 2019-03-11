
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

	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			int port = Integer.parseInt(args[0]);
			new Server(port);
		} else {
			new Server(8000);
		}
	}

	/**
	 * constructor
	 * @param port
	 */
	public Server(int port) {

		this.SUI=new ServerUI();

		try {
			serverStart(port);
			SUI.getContentArea().append("The server is running, port: " + port + "\r\n");
		} catch (BindException e1) {
			System.err.println("The server failed to start, port occupied!");
			System.exit(-1);
		}

		// close the window
		SUI.getFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (isStart) {
					closeServer();
				}
				System.exit(0);
			}
		});

	}

	/**
	 * start the connection to the clients
	 * @param port
	 * @throws java.net.BindException
	 */
	public void serverStart(int port) throws java.net.BindException {
		try {
			clients = new ArrayList<ClientThread>();
			serverSocket = new ServerSocket(port);
			serverThread = new ServerThread(serverSocket,clients,SUI);
			serverThread.start();
			isStart = true;
		} catch (BindException e) {
			isStart = false;
			throw new BindException("The port is already occupied, please change the other one!");
		} catch (Exception e1) {
			e1.printStackTrace();
			isStart = false;
			throw new BindException("Exception for starting the server!");
		}
	}

	/**
	 * close connection with the clients
	 */
	public void closeServer() {
		try {
			if (serverThread != null)
				serverThread.stop();// stop the thread for server

			for (int i = clients.size() - 1; i >= 0; i--) {
				//send "CLOSE" message to all clients
				clients.get(i).getWriter().println("CLOSE");
				clients.get(i).getWriter().flush();
				
				clients.get(i).stop();// stop the clientthread
				clients.get(i).getReader().close();
				clients.get(i).getWriter().close();
				clients.get(i).getSocket().close();
				clients.remove(i);
			}
			if (serverSocket != null) {
				serverSocket.close();
			}
			SUI.getListModel().removeAllElements();
			isStart = false;
		} catch (IOException e) {
			e.printStackTrace();
			isStart = true;
		}
	}


//	public void sendServerMessage(String message) {
//		for (int i = clients.size() - 1; i >= 0; i--) {
//			clients.get(i).getWriter().println("服务器：" + message + "(多人发送)");
//			clients.get(i).getWriter().flush();
//		}
//	}


}
