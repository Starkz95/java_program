package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFrameController implements Initializable {

    @FXML public TextField Username;
    @FXML public PasswordField Password;
    @FXML public Button exit;
    @FXML public Button login;
    @FXML public Button logRegister;

    public LoginFrameController(){

    }

    @FXML
    public void Login(javafx.event.ActionEvent actionEvent) throws IOException {
//        if(Check.checkreturn(Username.getText(), Password.getText())){
        if(Username.getText().equals("1") && Password.getText().equals("2")){

            Parent ChatRoomParent = FXMLLoader.load(getClass().getResource("ChatRoomFrame.fxml"));
            changeScene(actionEvent, ChatRoomParent);

        } else{
            //密码不对 清空数据
            Username.setText(null);
            Password.setText(null);
        }
    }

//    private void changeScene(ActionEvent actionEvent, Parent sceneParent) {
//        changeScene(actionEvent, sceneParent);
//    }

    // change into the other scene
    static void changeScene(ActionEvent actionEvent, Parent sceneParent) {
        Scene sceneParentFrame = new Scene(sceneParent);

        Stage windowChat = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        windowChat.setScene(sceneParentFrame);
        windowChat.show();
    }

    // Press the button to exit the system
    @FXML
    public void Exit(){
        Stage stage = (Stage)exit.getScene().getWindow();
        stage.close();
    }

    // to register as a membership
    @FXML
    public void toRegister(javafx.event.ActionEvent actionEvent) throws IOException {

        Parent registerFrameParent = FXMLLoader.load(getClass().getResource("RegisterFrame.fxml"));
        changeScene(actionEvent, registerFrameParent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
