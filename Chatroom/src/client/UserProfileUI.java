package client;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import server.DBHelper;

public class UserProfileUI {
	 private JFrame frame = new JFrame("User's profile");
     private DBHelper userProfile;
     private JPanel panel = new JPanel();
     private JLabel userLabel = new JLabel("Username:");
     private JLabel user = new JLabel();
     private JLabel sexLabel = new JLabel("Sex:");
     private JLabel sex = new JLabel();
 	private JLabel ageLabel = new JLabel("Age:");
 	private JLabel age = new JLabel();
 	private JLabel emailLabel = new JLabel("Email:");
 	private JLabel email = new JLabel();
 	private JLabel addressLebel = new JLabel("Address:");
 	private JLabel address = new JLabel();  
     
	 public UserProfileUI(String currentTabName) throws SQLException{
			
		 	userProfile = new DBHelper();
		    ResultSet rs=userProfile.userProfile(currentTabName);
		    if(rs.next()) {
		    	user.setText(rs.getString("username"));
		    	sex.setText(rs.getString("sex"));
		    	age.setText(rs.getString("age"));
		    	email.setText(rs.getString("email"));
		    	address.setText(rs.getString("address"));
		    }
			 frame.setSize(350, 380);
		     panel.setLayout(null);
		     frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		     frame.add(panel);
		     init();
		     frame.setLocationRelativeTo(null);
		     frame.setVisible(true);
	 }
	 
	  public void init() {
	        userLabel.setBounds(10, 20, 80, 25);
	        panel.add(userLabel);
	        user.setBounds(150, 20, 165, 25);
	        panel.add(user);
	        sexLabel.setBounds(10, 50, 80, 25);
	        panel.add(sexLabel);
	        sex.setBounds(150,50,80,25);
	        panel.add(sex);
	        ageLabel.setBounds(10,80,80,25);
	        panel.add(ageLabel);
	        age.setBounds(150,80,165,25);
	        panel.add(age);
	        emailLabel.setBounds(10,110,80,25);
	        panel.add(emailLabel);
	        email.setBounds(150,110,165,25);
	        panel.add(email);
	        addressLebel.setBounds(10,140,80,25);
	        panel.add(addressLebel);
	        address.setBounds(150,140,165,25);
	        panel.add(address);
	        
	    }
}
