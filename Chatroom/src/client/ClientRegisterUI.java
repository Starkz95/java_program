package client;

import java.util.Locale;

import javax.swing.*;

public class ClientRegisterUI {
    private JFrame frame = new JFrame("Register Interface");
    private JPanel panel = new JPanel();
    private JLabel userLabel = new JLabel("username:");
    private JTextField userText = new JTextField();
    private JLabel passwordLabel = new JLabel("password:");
    private JPasswordField passwordText = new JPasswordField();
    private JLabel makesurepasswordLabel = new JLabel("confirm password:");
    private JPasswordField makesurepasswordText = new JPasswordField();
    private JButton cancelButton = new JButton("cancel");
    private JButton registerButton = new JButton("register");

    public ClientRegisterUI() {
        frame.setSize(350, 200);
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
        cancelButton.setBounds(60, 120, 80, 25);
        panel.add(cancelButton);
        registerButton.setBounds(200, 120, 100, 25);
        panel.add(registerButton);
        
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

    
   
}
