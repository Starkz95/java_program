package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerThread extends Thread{
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients;
	private ServerUI SUI;

	/**
	 * constructor
	 * @param serverSocket
	 * @param clients
	 * @param SUI
	 */
	public ServerThread(ServerSocket serverSocket,ArrayList<ClientThread> clients,ServerUI SUI) {
		this.serverSocket = serverSocket;
		this.clients=clients;
		this.SUI=SUI;
	}
	
	/**
	 * 
	 */
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				new ServerLoginThread(socket,clients,SUI);
//				ClientThread client = new ClientThread(socket,clients,SUI);
//				client.start();// start the client thread for this user
//				clients.add(client);
//				SUI.getUserListModel().addElement(client.getUserName());// refresh the user list
//				SUI.getContentArea().append(client.getUserName() + " is online!\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
