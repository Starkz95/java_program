import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


//为一个客户端服务的线程
public class ClientThread extends Thread{

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private String userName;
	private ArrayList<ClientThread> clients;
	private ServerUI SUI; 

	// 客户端线程的构造方法
	public ClientThread(Socket socket,ArrayList<ClientThread> clients,ServerUI SUI) {
		this.socket = socket;
		this.clients=clients;
		this.SUI=SUI;
		try {
			
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
					SUI.getContentArea().append(this.getUserName() + " 下线!\r\n");
					// 断开连接释放资源
					reader.close();
					writer.close();
					socket.close();

					// 向所有在线用户发送该用户的下线命令
					for (int i = clients.size() - 1; i >= 0; i--) {
						clients.get(i).getWriter().println("Delete@" + clients.get(i).getUserName());
						clients.get(i).getWriter().flush();
					}

					SUI.getListModel().removeElement(userName);// 更新在线列表

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
			SUI.getContentArea().append("[群聊]" + s + ": " + c + "\r\n");
			for (int i = clients.size() - 1; i >= 0; i--) {
				clients.get(i).getWriter().println(message);
				clients.get(i).getWriter().flush();
			}
		} else if (sp[0].equals("Private")) { // 私聊消息
			String s = sp[1];// 发送者
			String r = sp[2];// 接收者
			String c = sp[3];// 消息内容
			SUI.getContentArea().append("[私聊]" + s + " => " + r + ": " + c + "\r\n");
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
