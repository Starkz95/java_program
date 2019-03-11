
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ClientLogin {


    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientLoginUI loginUI;
    private ClientRegisterUI registerUI;

    public ClientLogin(String host, int port) throws IOException {


        loginUI = new ClientLoginUI();

        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        

    }



    public void startLogin() {

    	loginUI.getLoginButton().addActionListener(new ActionListener() {
    		
  	        @Override
  	        public void actionPerformed(ActionEvent e) {
  	        	
  	        	try {   
  	        	
  	        		String username = loginUI.getUserText().getText();
  	  	        	String password = loginUI.getPasswordText().getText();
  	  	        	String loginInfo = "login" + "@" + username + "@" + password;
  	        		login(loginInfo);
  	        		
  	        		
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 	        		        
    		}
    	});

    }
    
   


    public void login(String message) throws IOException {
    	
    	out.println(message);
        String mess=in.readLine();
        if (mess.equals("1")) {
            loginUI.successfulLogin();
            loginUI.getFrame().setVisible(false);
            new Client(socket, loginUI.getUserText().getText());
        } else {
            loginUI.loginFailed();
        }
    	
    	
    	
    }



    public void register(String message) throws IOException {
       
        out.println(message);
        String mess=in.readLine();
        if (mess.equals("1")) {
            registerUI.successfulRegister();
        } else if(mess.equals("2")){
            registerUI.registerpasswordFailed();
        } else {
        	registerUI.registeraccountFailed();
        }
       
    }
    
    public void startRegister() {
    	loginUI.getRegisterButton().addActionListener(new ActionListener() {	
   	        @Override
   	        public void actionPerformed(ActionEvent e) {
   	        	registerUI = new ClientRegisterUI();
   	        	
   	        	registerUI.getRegisterButton().addActionListener(new ActionListener() {
   	     		
   	   	        @Override
   	   	        public void actionPerformed(ActionEvent e) {
   	   	        	try {
   	   	        		String username = registerUI.getUserText().getText();
   	   	        		String password = registerUI.getPasswordText().getText();
   	   	        		String makesurepassword = registerUI.getMakesurepasswordText().getText();
   	   	        		String registerInfo = "register" + "@" + username + "@" + password + "@" + makesurepassword;
   	   	        		register(registerInfo);
   	   	        	} catch (IOException e1) {
   	 			// TODO Auto-generated catch block
   	 			e1.printStackTrace();
   	   	        	}
   	   	        }
   	      	});
     		}
     	});
    }
    
    
    public static void main(String[] args) throws IOException {
        ClientLogin localhost = new ClientLogin("localhost", 8000);
        localhost.startLogin();
        localhost.startRegister();
        
    }
}
