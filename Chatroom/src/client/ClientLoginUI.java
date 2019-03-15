package client;

import java.util.Locale;

import javax.swing.*;

public class ClientLoginUI {
    private JFrame frame = new JFrame("Login Interface");
    private JPanel panel = new JPanel();
    private JLabel userLabel = new JLabel("username:");
    private JTextField userText = new JTextField();
    private JLabel passwordLabel = new JLabel("password:");
    private JPasswordField passwordText = new JPasswordField();
    private JButton loginButton = new JButton("login");
    private JButton registerButton = new JButton("register");

    public ClientLoginUI() throws Exception {
    	//javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	//org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        frame.setSize(400, 300);
        panel.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        init();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() throws Exception {
    	
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);
        passwordLabel.setBounds(10, 70, 80, 25);
        panel.add(passwordLabel);
        passwordText.setBounds(100, 70, 165, 25);
        panel.add(passwordText);
        loginButton.setBounds(60, 120, 80, 25);
        panel.add(loginButton);
        registerButton.setBounds(200, 120, 100, 25);
        panel.add(registerButton);
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
