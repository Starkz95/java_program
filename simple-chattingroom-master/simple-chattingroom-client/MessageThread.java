import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JTextArea;
//不断接受消息的线程
public class MessageThread extends Thread{
	private BufferedReader reader;
	private JTextArea textArea;
	private ClientChatUI CCUI;
	private Client client;
	// 接收消息线程的构造方法
	public MessageThread(BufferedReader reader, JTextArea textArea,ClientChatUI CCUI,Client client) {
		this.reader = reader;
		this.textArea = textArea;
		this.CCUI=CCUI;
		this.client=client;
		
	}

	// 被动的关闭连接
	public synchronized void closeCon() throws Exception {
		// 清空用户列表
		CCUI.getListModel().removeAllElements();
		// 被动的关闭连接释放资源
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

	public void run() {
		String message = "";
		while (true) {
			try {
				message = reader.readLine();
				System.out.println("[Client] " + message);
				String[] sp = message.split("@");

				if (sp[0].equals("CLOSE")) {// 服务器要求断开链接
					textArea.append("服务器已关闭!\r\n");
					closeCon();// 被关闭连接

		        }
				 if (sp[0].equals("Add")) { // 新增用户
					String username = sp[1];
					CCUI.getListModel().addElement(username);
				} else if (sp[0].equals("UserList")) { // 用户列表
					String[] usernames = sp[1].split("#");
					for (String username : usernames) {
						if (username.length() == 0) {
							continue;
						}
						CCUI.getListModel().addElement(username);
					}
				} else if (sp[0].equals("Delete")) { // 删除用户
					String username = sp[1];
					CCUI.getListModel().removeElement(username);

				} else if (sp[0].equals("Public")) {// 群聊消息
					String s = sp[1];// 发送者
					// String r = sp[2];// 接收者, 固定为ALL
					String c = sp[3];// 消息内容
					// textArea.append("[群聊]" + s + ":\r\n" + c + "\r\n\r\n");
					client.addMessage("Public", s + ":\r\n" + c + "\r\n\r\n");
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
					String s2 = s.equals(client.getName()) ? r : s;
					client.addMessage(s2, s + ":\r\n" + c + "\r\n");
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
