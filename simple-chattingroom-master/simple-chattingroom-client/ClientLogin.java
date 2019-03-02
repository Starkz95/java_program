

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;



 public class ClientLogin {


	public static void main(String args[]) throws IOException {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Username:" );
		String account = input.nextLine();
		System.out.println("Password:");
		String password = input.nextLine();
		
		String message = account + "@" + password;
		//System.out.println(message);
        Socket login = new Socket("localhost",8888);
		
		
		InputStream is = login.getInputStream();
        OutputStream os = login.getOutputStream();
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        
        bw.write(message+ "\n");
        bw.flush();
        
        
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String mess = br.readLine();
        System.out.println(mess);
		
	
	}
	
}
