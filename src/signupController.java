import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import samples.userDb.InsertUser;
import samples.userDb.SelectUser;

public class signupController {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup;

    @FXML
    private TextField username;

    @FXML
    private Label existUser;

    @FXML
    private Hyperlink blogIn;

    @FXML
    void signUp(ActionEvent event) throws IOException {
        String email_ = email.getText();
        String pw_ = password.getText();
        String name_ = username.getText();
        boolean exist = SelectUser.searchSignup(name_, email_);
        if (exist==true){
            existUser.setText("User already existed");
        }
        else if (email_.isEmpty() && pw_.isEmpty()){
            existUser.setText("Please enter your info");
        }
        else{
            InsertUser.insert(name_, email_, pw_);
            App m = new App();
            m.changeScene("login.fxml");
        }

    }

    @FXML
    void backToLogin(ActionEvent event) throws IOException{
        App m = new App();
        m.changeScene("login.fxml");
    }


}
