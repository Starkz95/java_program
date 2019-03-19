package client;

import java.awt.Color;
import java.awt.Font;
import java.util.Locale;

import javax.swing.*;

public class ClientRegisterUI {
    private JFrame frame = new JFrame("Register Interface");
    private JPanel panel = new JPanel();
    private JLabel userLabel = new JLabel("Username:");
    private JTextField userText = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordText = new JPasswordField();
    private JLabel makesurepasswordLabel = new JLabel("Confirm password:");
    private JPasswordField makesurepasswordText = new JPasswordField();
    private JButton cancelButton = new JButton("Cancel");
    private JButton registerButton = new JButton("Register");
    private JLabel sex = new JLabel("Sex:");
    private JRadioButton male=new JRadioButton("Male");
	private JRadioButton female=new JRadioButton("Female");
	private JLabel age = new JLabel("Age:");
	private JTextField ageText = new JTextField();
	private JLabel email = new JLabel("Email:");
	private JTextField emailText = new JTextField();
	private JLabel address = new JLabel("Address:");
	private JTextField addressText = new JTextField();
	private String sexString;
	

    public ClientRegisterUI() {
        frame.setSize(420, 380);
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
        userText.setBounds(150, 20, 165, 25);
        panel.add(userText);
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        passwordText.setBounds(150, 50, 165, 25);
        panel.add(passwordText);
        makesurepasswordLabel.setBounds(0, 80, 150, 25);
        panel.add(makesurepasswordLabel);
        makesurepasswordText.setBounds(150, 80, 165, 25);
        panel.add(makesurepasswordText);
        sex.setBounds(10, 110, 80, 25);
        panel.add(sex);
        male.setBounds(150,110,80,25);
        panel.add(male);
        female.setBounds(230,110,100,25);
        panel.add(female);
        age.setBounds(10,140,80,25);
        panel.add(age);
        ageText.setBounds(150,140,165,25);
        panel.add(ageText);
        email.setBounds(10,170,80,25);
        panel.add(email);
        emailText.setBounds(150,170,165,25);
        panel.add(emailText);
        address.setBounds(10,200,80,25);
        panel.add(address);
        addressText.setBounds(150,200,165,25);
        panel.add(addressText);
        cancelButton.setBounds(60, 240, 80, 25);
        panel.add(cancelButton);
        registerButton.setBounds(200, 240, 100, 25);
        panel.add(registerButton);
        sexString="";
        cancelButton.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
        cancelButton.setForeground(new Color(220,20,60));
        registerButton.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
        registerButton.setForeground(new Color(72,61,139));
        
    }



	public void successfulRegister() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "register successfully!", "",JOptionPane.PLAIN_MESSAGE);
    }

    public void registerpasswordFailed() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "two passwords are not the same!", "",JOptionPane.ERROR_MESSAGE);
        
    }
    public void registeraccountFailed() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "account is existed!", "",JOptionPane.ERROR_MESSAGE);
        
    }
    
    public void notnull() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "user's profile can't be null!", "",JOptionPane.ERROR_MESSAGE);
        
    }
    
    

    
    public JButton getRegisterButton() {
		return registerButton;
	}

	public void setRegisterButton(JButton registerButton) {
		this.registerButton = registerButton;
	}

	public JTextField getUserText() {
        return userText;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JPasswordField getMakesurepasswordText() {
		return makesurepasswordText;
	}

	public JTextField getAgeText() {
		return ageText;
	}

	public JTextField getEmailText() {
		return emailText;
	}

	public JTextField getAddressText() {
		return addressText;
	}

	public String getSexString() {
		if(male.isSelected()){
        	sexString="male";
        }
        else if(female.isSelected()){
        	sexString="female";
        }
        else {
        	sexString="";
        }
			return sexString;
	}

	public JFrame getFrame() {
		return frame;
	}
	
    
   
}
