import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JTextArea;

public class MessageThread extends Thread{
	private BufferedReader reader;
	private JTextArea textArea;
	private ClientChatUI CCUI;
	private Client client;
	
	/**
	 * constructor
	 * @param reader
	 * @param textArea
	 * @param CCUI
	 * @param client
	 */
	public MessageThread(BufferedReader reader, JTextArea textArea,ClientChatUI CCUI,Client client) {
		this.reader = reader;
		this.textArea = textArea;
		this.CCUI=CCUI;
		this.client=client;
		
	}

	/**
	 * when the server is closed
	 * @throws Exception
	 */
	public synchronized void closeCon() throws Exception {
		// remove the users
		CCUI.getListModel().removeAllElements();
		
		if (reader != null) {
			reader.close();
		}
		if (client.getWriter() != null) {
			client.getWriter().close();
		}
		if (client.getSocket() != null) {
			client.getSocket().close();
		}
	}
	
	/**
	 * receive different types of messages
	 */
	public void run() {
		String message = "";
		while (true) {
			try {
				message = reader.readLine();
				System.out.println("[Client] " + message);
				String[] sp = message.split("@");

				if (sp[0].equals("CLOSE")) { //when the server is closed
					textArea.append("The server is closed!\r\n");
					closeCon(); //close the connection

		        }
				else if (sp[0].equals("Add")) { // add a user when he is online
					String username = sp[1];
					CCUI.getListModel().addElement(username);
				} 
				else if (sp[0].equals("UserList")) { // the list of users
					String[] usernames = sp[1].split("#");
					for (String username : usernames) {
						if (username.length() == 0) {
							continue;
						}
						CCUI.getListModel().addElement(username);
					}
				} 
				else if (sp[0].equals("Delete")) { // delete a user from the list when he is logout
					String username = sp[1];
					CCUI.getListModel().removeElement(username);
					CCUI.getTabs().removeTabAt(CCUI.getTabs().indexOfTab(username));
					//client.addMessage("Public", "");
					CCUI.getTabs().setSelectedIndex(CCUI.getTabs().indexOfTab("Public"));

				} 
				else if (sp[0].equals("Public")) {// messages for public
					String s = sp[1];// sender
					// String r = sp[2];// receiver
					String c = sp[3];// message
					// textArea.append("[public]" + s + ":\r\n" + c + "\r\n\r\n");
					client.addMessage("Public", s + ":    " + c + "\r\n\r\n");
					CCUI.getTabs().setSelectedIndex(CCUI.getTabs().indexOfTab("Public"));
					System.out.println("[public]" + s + ": " + c + "\r\n");
				} 
				else if (sp[0].equals("Private")) {// messages for private
					String s = sp[1];// sender
					String r = sp[2];// receiver
					String c = sp[3];// message
					// textArea.append("[private]" + s + ":\r\n" + c + "\r\n\r\n");
					for (String str : sp) {
						System.out.println(str);
					}
					System.out.println("----");
					String s2 = s.equals(client.getName()) ? r : s;
					client.addMessage(s2, s + ":    " + c + "\r\n\r\n");
					CCUI.getTabs().setSelectedIndex(CCUI.getTabs().indexOfTab(s2));
					System.out.println("[Private]" + s + ": " + c + "\r\n");
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
