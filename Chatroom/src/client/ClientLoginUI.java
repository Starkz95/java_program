package client;

import java.awt.Color;
import java.awt.Font;
import java.util.Locale;

import javax.swing.*;

public class ClientLoginUI {
    private JFrame frame = new JFrame("Login Interface");
    private JPanel panel = new JPanel();
    private JLabel userLabel = new JLabel("Username:");
    private JTextField userText = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordText = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JLabel background;
    private ImageIcon img = new ImageIcon("chatroom.png");

    public ClientLoginUI() throws Exception {
    	//javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	//org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        frame.setSize(400, 400);
        panel.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        init();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() throws Exception {
    	
    	background=new JLabel(img);
    	background.setBounds(140, 10, img.getIconWidth(), img.getIconHeight());
    	panel.add(background);
        userLabel.setBounds(10, 100, 80, 25);
        panel.add(userLabel);
        userText.setBounds(100, 100, 165, 25);
        panel.add(userText);
        passwordLabel.setBounds(10, 150, 80, 25);
        panel.add(passwordLabel);
        passwordText.setBounds(100, 150, 165, 25);
        panel.add(passwordText);
        loginButton.setBounds(60, 200, 80, 25);
        panel.add(loginButton);
        registerButton.setBounds(200, 200, 100, 25);
        panel.add(registerButton);
        loginButton.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
        loginButton.setForeground(new Color(123,104,238));
        registerButton.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
        registerButton.setForeground(new Color(255,140,0));
    }

    public void successfulLogin() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "login successfully!", "",JOptionPane.PLAIN_MESSAGE);
    }

    public void loginFailed() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "account or password is wrong!", "",JOptionPane.ERROR_MESSAGE);
        
    }
    
    public void notnull() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "account or password can't be null!", "",JOptionPane.ERROR_MESSAGE);
        
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
    
    public JButton getLoginButton() {
    	return loginButton;
    }

	public JFrame getFrame() {
		return frame;
	}
    
}
