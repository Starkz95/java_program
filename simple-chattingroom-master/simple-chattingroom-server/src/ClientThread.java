import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;



public class ClientThread extends Thread{

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private String userName;
	private ArrayList<ClientThread> clients;
	private ServerUI SUI; 

	/**
	 * constructor
	 * @param socket
	 * @param clients
	 * @param SUI
	 */
	public ClientThread(Socket socket,ArrayList<ClientThread> clients,ServerUI SUI) {
		this.socket = socket;
		this.clients=clients;
		this.SUI=SUI;
		try {
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
			
			String inf = reader.readLine();
			
			String[] sp = inf.split("@");
			if (sp.length != 2 || !sp[0].equals("Logon")) {
				return;
			}
			userName = sp[1];
			// send message to clients 
			writer.println("Public@Server@ALL@" + userName + "successfully connecting to the server!");
			writer.flush();

			// send message to client how many users are online
			if (clients.size() > 0) {
				String allUsers = "";
				for (int i = clients.size() - 1; i >= 0; i--) {
					allUsers += (clients.get(i).getUserName() + "#");
				}
				writer.println("UserList@" + allUsers);
				writer.flush();
			}

			// Send the message that who is online to all online users
			for (int i = clients.size() - 1; i >= 0; i--) {
				// clients.get(i).getWriter().println("Add@" + clients.get(i).getUserName());
				clients.get(i).getWriter().println("Add@" + userName);
				clients.get(i).getWriter().flush();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * receive different types of messages from client
	 */
	public void run() {
		String message = null;
		while (true) {
			try {
				message = reader.readLine();
				if (message.equals("Logout"))// when the user is logout
				{
					SUI.getContentArea().append(this.getUserName() + " 下线!\r\n");
					reader.close();
					writer.close();
					socket.close();

					// Send the message that who is offline to all online users
					for (int i = clients.size() - 1; i >= 0; i--) {
						clients.get(i).getWriter().println("Delete@" + userName);
						clients.get(i).getWriter().flush();
					}

					SUI.getListModel().removeElement(userName);// refresh the userlist

					// stop the thread for this user
					for (int i = clients.size() - 1; i >= 0; i--) {
						if (clients.get(i).getUserName().equals(userName)) {
							ClientThread temp = clients.get(i);
							clients.remove(i);
							temp.stop();
							return;
						}
					}
				} else {
					dispatcherMessage(message);
				}
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * send the public messages to all online users, and send the private messages to corresponding users.
	 * @param message
	 */
	public void dispatcherMessage(String message) {
		String[] sp = message.split("@");
		if (sp.length != 4) {
			return;
		}
		if (sp[0].equals("Public")) {// send the public messages
			
			String s = sp[1];// sender
			// String r = sp[2];// receiver
			String c = sp[3];// message
			SUI.getContentArea().append("[Public]" + s + ": " + c + "\r\n");
			for (int i = clients.size() - 1; i >= 0; i--) {
				clients.get(i).getWriter().println(message);
				clients.get(i).getWriter().flush();
			}
		} else if (sp[0].equals("Private")) { // send the private messages
			String s = sp[1];// sender
			String r = sp[2];// receiver
			String c = sp[3];// message
			SUI.getContentArea().append("[Private]" + s + " => " + r + ": " + c + "\r\n");
			for (int i = 0; i < clients.size(); i++) {
				if (clients.get(i).getUserName().equals(r) || clients.get(i).getUserName().equals(s)) {
					clients.get(i).getWriter().println(message);
					clients.get(i).getWriter().flush();
				}
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}
	
	public BufferedReader getReader() {
		return reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public String getUserName() {
		return userName;
	}
	
	
}
