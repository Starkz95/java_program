import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HistoryUI {
	 private JFrame frame = new JFrame("History");
     private DBHelper history = new DBHelper();
	 private JTextField text = new JTextField();
	 private Client client = new Client();
	 private JPanel panel = new JPanel();    
     
	 public HistoryUI(){
		    frame.setSize(600, 400);
		    panel.setLayout(null);
		    //text.setText(client.getName());
		    text.setText(history.getHistory(client.getName(),client.getCurrentTabName()));
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    frame.add(panel);
		    
		    text.setBounds(0, 0, 600, 400);
	        
	        panel.add(text);
	        
	        frame.setVisible(true);
	 }
	 
	 
	 


	public JTextField getText() {
		return text;
	}



	
}
