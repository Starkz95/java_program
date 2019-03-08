
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerLoginThread extends Thread {
   
    private Socket socket;
    boolean login;
    boolean register;
    private ArrayList<ClientThread> clients;
	private ServerUI SUI;
	
    public ServerLoginThread(Socket socket,ArrayList<ClientThread> clients,ServerUI SUI) {
        
        this.socket = socket;
        this.login = false;
        this.register = false;
        this.clients=clients;
		this.SUI=SUI;
        start();
    }

    @Override
    public void run() {
        try {
            System.out.println("connection with "+ socket.getInetAddress()+" established");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            
            while(true) {
            	String message = in.readLine();
            
            	String[] mess = message.split("@");
            	if(mess[0].equals("login")) {
            		login = new DBHelper().checkUser(mess[1], mess[2]);

                	if (login) {
                    //System.out.println(loginInfo[0] + "is online");
                		out.println("1");
        				ClientThread client = new ClientThread(socket,clients,SUI);
        				client.start();// 开启对此客户端服务的线程
        				clients.add(client);
        				SUI.getListModel().addElement(client.getUserName());// 更新在线列表
        				SUI.getContentArea().append(client.getUserName() + " 上线!\r\n");
        				break;
                	
                	}else {
                		out.println("0");
                	}
            	}else if(mess[0].equals("register")){
            		register = new DBHelper().checknewUser(mess[1]);

                	if (!register) {
                    //System.out.println(loginInfo[0] + "is online");
                		if(mess[2].equals(mess[3]) && !mess[2].equals(null) && !mess[3].equals(null)) {
                			out.println("1");
                    		new DBHelper().insertUser(mess[1], mess[2]);
                		}
                		else {
                			out.println("2");
                		}

                	}else {
                		out.println("3");
                	}
            	}
            	
            //TODO connect to the database
            	
            
            //System.out.println("connection lost");
            }
            
        } catch (IOException e) {

        }
    }
    
    
}
