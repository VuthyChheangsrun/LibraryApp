import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import samples.db.ConnectDB;
import samples.userDb.InsertUser;
import samples.userDb.SelectUser;


public class signupController implements Initializable{

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
    private Button home;

    @FXML
    private TableView<User> UserTable;
    
    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, String> usernameCol;

    @FXML
    private Button update;

    @FXML
    private Button delete;

    @FXML
    void signUp(ActionEvent event) throws SQLException {
        String email_ = email.getText();
        String pw_ = password.getText();
        String name_ = username.getText();
        if (name_=="" || email_=="" || pw_=="" || name_==null || email_==null || pw_==null){
            existUser.setText("Please enter your info");
        }
        else if (SelectUser.searchSignup(name_, email_)==true){
            existUser.setText("User already existed");
            clearField();
        }
        else{
            InsertUser.insert(name_, email_, pw_);
            existUser.setText("");
            showUser();
            clearField();
        }

    }

    
    @FXML
    void updateClick(ActionEvent event) throws SQLException {
        String email_ = email.getText();
        String pw_ = password.getText();
        String name_ = username.getText();
        if (name_=="" || email_=="" || pw_=="" || name_==null || email_==null || pw_==null){
            existUser.setText("Please enter your info");
        }
        else{
            
            try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM user")){
                boolean rightE=false;
                while(rs.next()){
                    if (rs.getString("email").compareTo(email_)==0){
                        rightE=true;
                        ConnectDB.getConnection().executeUpdate("UPDATE user SET username=?, password=? where email=?"
                        ,name_, pw_, email_);
                        existUser.setText("");
                        showUser();
                        clearField();
                    }
                }
                if (rightE==false){
                        existUser.setText("Wrong email!!");
                        clearField();
                    }
            }catch(Exception ex){ex.printStackTrace();}
        }

    }

    @FXML
    void deleteClick(ActionEvent event) throws SQLException{
        String email_ = email.getText();
        String pw_ = password.getText();
        String name_ = username.getText();
        if (name_=="" || name_==null || email_=="" || email_==null || pw_=="" || pw_==null){
            existUser.setText("Please enter your info");
        }
        else{
            try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM user")){
                boolean rightE=false;
                while(rs.next()){
                    if (rs.getString("email").compareTo(email_)==0 && rs.getString("password").compareTo(pw_)==0){
                        rightE=true;
                        ConnectDB.getConnection().executeUpdate("delete from user where email=? and password=?"
                        , email_, pw_);
                        existUser.setText("");
                        showUser();
                        clearField();
                    }
                }
                if (rightE==false){
                        existUser.setText("Wrong Info!!");
                        clearField();
                    }
            }catch(Exception ex){ex.printStackTrace();}
        }

    }

    public void clearField(){
        email.setText(null);
        password.setText(null);
        username.setText(null);
    }
    
    private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    void backToHome(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public ObservableList<User> getuserList() throws SQLException {
        ObservableList<User> bookList = FXCollections.observableArrayList();
        
        try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM user")){
            User user;
            while(rs.next()){
                user = new  User(rs.getString("username"), rs.getString("email"));
                bookList.add(user);
            }
        }catch(Exception ex){ex.printStackTrace();}

        return bookList;

    }

    
    public void showUser() throws SQLException {
        ObservableList<User> list = getuserList();
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        UserTable.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
