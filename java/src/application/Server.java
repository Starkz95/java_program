package application;

import java.io.*;
import java.net.*;
import java.util.*;


public class Server {

    int port;
    List<Socket> clients;
    ServerSocket server;

    public static void main(String[] args) {
        new Server();
    
    }

    public Server() {
        try {

            port = 8080;
            clients = new ArrayList<Socket>();
            server = new ServerSocket(port);
            System.out.println("server is ready!");

            while (true) {
              Socket socket = server.accept();
                clients.add(socket);
                Mythread mythread = new Mythread(socket);
                mythread.start();
            }


        } catch (Exception ex) {
        }
    }

    class Mythread extends Thread {
        Socket ssocket;
        private BufferedReader br;
        private PrintWriter pw;
        public String msg;

        public Mythread(Socket s) {
            ssocket = s;
        }

        public void run() {

            try {
                br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));
                msg = "welcome [" + ssocket.getInetAddress() + "] enter the chatroom! now the number of users is["
                        + clients.size() + "]";

                sendMsg();

                while ((msg = br.readLine()) != null) {

                    msg = "[" + ssocket.getInetAddress() + "] say:" + msg;
                    sendMsg();

                }
            } catch (Exception ex) {

            }
        }

        public void sendMsg() {
            try {
                System.out.println(msg);

                for (int i = clients.size() - 1; i >= 0; i--) {
                    pw = new PrintWriter(clients.get(i).getOutputStream(), true);
                    pw.println(msg);
                    pw.flush();
                    
                }
            } catch (Exception ex) {
            }
        }
    }

}

