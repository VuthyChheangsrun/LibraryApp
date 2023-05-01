import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import samples.userDb.SelectUser;
public class login {

    @FXML
    private TextField email;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink signup;

    @FXML
    private Label wrongInfo;

    @FXML
    void signupPage(ActionEvent event) throws IOException {
        App m = new App();
        m.changeScene("signup.fxml");
    }

    @FXML
    public void userLogin(ActionEvent event) throws IOException{
        String email_ = email.getText();
        String password_ = password.getText();
        boolean exist = SelectUser.search(email_, password_);
        if (exist==true){
            wrongInfo.setText("Correct!");
            App m = new App();
            m.changeScene("home.fxml");
            
        }
        else if (email_.isEmpty() && password_.isEmpty()){
            wrongInfo.setText("Please enter your info");
        }
        else{
            wrongInfo.setText("Incorrect info");
        }

    }

}
