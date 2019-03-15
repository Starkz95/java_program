package client;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import server.DBHelper;

public class HistoryUI {
	 private JFrame frame = new JFrame("History");
     private DBHelper history;
	 private JTextArea textArea;
	 private JScrollPane scrollPane;
	 private JPanel panel = new JPanel();    
     
	 public HistoryUI(String name,String currentTabName){
		 	textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setFont(new Font("FontAttrib.BOLD",Font.BOLD,15));
			scrollPane = new JScrollPane(textArea);
			scrollPane.setBorder(new TitledBorder("History"));
			
			history = new DBHelper();
		    
		 	frame.setSize(600, 400);
		    panel.setLayout(null);
		    //text.setText(client.getName());
		    if(currentTabName.equals("Public")) {
		    	textArea.setText(history.getPublicHistory());
		    }
		    else {
		    	textArea.setText(history.getPrivateHistory(name, currentTabName));
		    }
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    frame.setLayout(new BorderLayout());
			frame.add(scrollPane, BorderLayout.CENTER);
			frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	 }
	 
	
}