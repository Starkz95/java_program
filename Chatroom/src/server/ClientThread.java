package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



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
			writer.println("Public@Server@ALL@" + userName + " successfully connecting to the server!");
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
				String[] s=message.split("@");
				if (message.equals("Logout"))// when the user is logout
				{
					SUI.getContentArea().append(this.getUserName() + " offline!\r\n");
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
				}
				else if(s[0].equals("Profile")) {
					ResultSet rs=new DBHelper().userProfile(s[1]);
					if(rs.next()) {
						String	user=rs.getString("username");
						String	sex=rs.getString("sex");
						String	age=rs.getString("age");
						String	email=rs.getString("email");
						String	address=rs.getString("address");
						String profile="Profile#"+user+"#"+sex+"#"+age+"#"+email+"#"+address;
					
						for (int i = clients.size() - 1; i >= 0; i--) {
							if (clients.get(i).getUserName().equals(userName)) {
								clients.get(i).getWriter().println(profile);
								clients.get(i).getWriter().flush();
							}
						}
					}
					
				}
//				else if(s[0].equals("History")) {
//					if(s.length==2) {
//						String history=new DBHelper().getPublicHistory();
//						for (int i = clients.size() - 1; i >= 0; i--) {
//							if (clients.get(i).getUserName().equals(userName)) {
//								clients.get(i).getWriter().println("History@"+history);
//								clients.get(i).getWriter().flush();
//							}
//						}
//					}
//					else {
//						String history=new DBHelper().getPrivateHistory(s[1], s[2]);
//						for (int i = clients.size() - 1; i >= 0; i--) {
//							if (clients.get(i).getUserName().equals(userName)) {
//								clients.get(i).getWriter().println("History@"+history);
//								clients.get(i).getWriter().flush();
//							}
//						}
//					}
//				}
				else {
					dispatcherMessage(message);
				}
			} catch (IOException e) {
				// e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * send the public messages to all online users, and send the private messages to corresponding users.
	 * @param message
	 */
	public void dispatcherMessage(String message) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] sp = message.split("@");
		if (sp.length != 4) {
			return;
		}
		if (sp[0].equals("Public")) {// send the public messages
			
			String s = sp[1];// sender
			// String r = sp[2];// receiver
			String c = sp[3];// message
			new DBHelper().insertHistory(s,"Public",df.format(new Date()) +"\n"+ s + ":   " + c + "\r\n",df.format(new Date()).toString());
			SUI.getContentArea().append("[Public]" + s + ": " + c + "\r\n");
			for (int i = clients.size() - 1; i >= 0; i--) {
				clients.get(i).getWriter().println(message);
				clients.get(i).getWriter().flush();
			}
		} else if (sp[0].equals("Private")) { // send the private messages
			String s = sp[1];// sender
			String r = sp[2];// receiver
			String c = sp[3];// message
			new DBHelper().insertHistory(s,r,df.format(new Date()) +"\n"+ s + ":   " + c + "\r\n",df.format(new Date()).toString());
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
