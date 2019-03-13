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

    public ClientLoginUI() {
        frame.setSize(350, 200);
        panel.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        init();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() {
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);
        loginButton.setBounds(60, 80, 80, 25);
        panel.add(loginButton);
        registerButton.setBounds(200, 80, 100, 25);
        panel.add(registerButton);
    }

    public void successfulLogin() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "login successfully!", "",JOptionPane.PLAIN_MESSAGE);
    }

    public void loginFailed() {
    	JOptionPane.setDefaultLocale(Locale.ENGLISH);
    	JOptionPane.showMessageDialog(null, "account or password wrong!", "",JOptionPane.ERROR_MESSAGE);
        
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
