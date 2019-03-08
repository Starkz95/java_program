
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class ServerLogin {

    int port;
    public ServerSocket ss;
    
//   final int TIMOUT = 3600; Set a timer to close the server when there is no connection

    public ServerLogin(int port) throws IOException {
        this.port = port;
        ss = new ServerSocket(port);
        System.out.println("Server established");
    }

    public void start() throws IOException{
        getConnections();
    }

    public void getConnections() throws IOException {
        while(true){
            Socket socket = ss.accept();
            //new ServerLoginThread(socket);
        }
    }



//    public static void main(String[] args) throws IOException {
//        ServerLogin server = new ServerLogin(8000);
//        server.start();
//    }
}
