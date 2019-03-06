import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


//服务器线程
public class ServerThread extends Thread{
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients;
	private ServerUI SUI;

	// 服务器线程的构造方法
	public ServerThread(ServerSocket serverSocket,ArrayList<ClientThread> clients,ServerUI SUI) {
		this.serverSocket = serverSocket;
		this.clients=clients;
		this.SUI=SUI;
	}

	public void run() {
		while (true) {// 不停的等待客户端的链接
			try {
				Socket socket = serverSocket.accept();
				ClientThread client = new ClientThread(socket,clients,SUI);
				client.start();// 开启对此客户端服务的线程
				clients.add(client);
				SUI.getListModel().addElement(client.getUserName());// 更新在线列表
				SUI.getContentArea().append(client.getUserName() + " 上线!\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
