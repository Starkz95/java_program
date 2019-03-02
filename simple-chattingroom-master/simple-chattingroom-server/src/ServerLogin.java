
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


 
public class ServerLogin {
	//private DBHelper dbHelper;
	

    
   public static void main(String[] args) {
      try {
         ServerSocket ss = new ServerSocket(8888);
         System.out.println("启动服务器....");
         Socket s = ss.accept();
         System.out.println("客户端:"+s.getInetAddress().getLocalHost()+"已连接到服务器");
         
         BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
         //读取客户端发送来的消息
         String mess = br.readLine();
         String[] sp = mess.split("@");
        
         //System.out.println("客户端："+mess);
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
         DBHelper dbHelper = new DBHelper();
         if (dbHelper.checkUser(sp[0],sp[1])){
        	 bw.write("login successfully!\n");// System.out.println(sp[0] + "is online!");
         }
         else {
        	 bw.write("username or password is wrong!\n");
         }
         //bw.write(mess+"\n");
         bw.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}