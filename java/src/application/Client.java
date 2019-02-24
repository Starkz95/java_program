package application;

import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Client {

	public int port = 8080;
    Socket socket = null;

    public static void main(String[] args) {
    	new Client();
    }

    public Client() {

        try {
            socket = new Socket("127.0.0.1", port);
            
            new Cthread().start();

            BufferedReader br = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            String msg1;
            while ((msg1 = br.readLine()) != null) {

                System.out.println(msg1);
               
            }
        } catch (Exception e) {
        }
    }

    class Cthread extends Thread {

        public void run() {
            try {
            	
                BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                String msg2;

              while (true) {

                   msg2 = re.readLine();
                   pw.println(msg2);
                   pw.flush();
                 
               }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    
   

}
