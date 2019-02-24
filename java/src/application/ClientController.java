package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ClientController implements Initializable{

	@FXML
	private Button sendButton;
	@FXML
	private TextField textSend;
	@FXML
	private TextArea textField;

	
	  @Override
	  public void initialize(URL location, ResourceBundle resources) {

	       // TODO (don't really need to do anything here).
		  
	   }
	  
	  public void sendMessage(ActionEvent event) {

		  textField.appendText(textSend.getText()+"\n");
		  textSend.clear();

	   }



	
	  
	  
	  
	  
	  
}
