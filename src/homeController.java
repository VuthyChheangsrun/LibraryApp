import java.io.IOException;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import samples.db.ConnectDB;

public class homeController {

    @FXML
    private Label profile;

    @FXML
    private Button addBook;

    @FXML
    private Button list;

    @FXML
    private Button search;

    @FXML
    private Button borrowBook;

    @FXML
    private Button aboutButton;

    @FXML
    private Button logoutB;

    @FXML
    private VBox authorCon;

    @FXML
    private VBox ratingCon;

    @FXML
    private VBox realeaseCon;

    @FXML
    private VBox titleCon;


    private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    void mEnter(MouseEvent event) { //when mouse is hovering over any of these buttons, it turn gray
        if(search.isHover()) search.setStyle("-fx-background-radius: 0; -fx-background-color: gray; -fx-text-Fill: white");
        else if (addBook.isHover()) addBook.setStyle("-fx-background-radius: 0; -fx-background-color: gray; -fx-text-Fill: white");
        else if (list.isHover()) list.setStyle("-fx-background-radius: 0; -fx-background-color: gray; -fx-text-Fill: white");
        else if (aboutButton.isHover()) aboutButton.setStyle("-fx-background-radius: 0; -fx-background-color: gray; -fx-text-Fill: white");
        else if (borrowBook.isHover()) borrowBook.setStyle("-fx-background-radius: 0; -fx-background-color: gray; -fx-text-Fill: white");
        else if (logoutB.isHover()) logoutB.setStyle("-fx-background-radius: 0; -fx-background-color: white; -fx-text-Fill: black; -fx-border-color: white");
    }
    @FXML
    void mExit(MouseEvent event) { //when mouse is out of the buttons
        search.setStyle("-fx-background-radius: 0; -fx-background-color: transparent");
        addBook.setStyle("-fx-background-radius: 0; -fx-background-color: transparent");
        list.setStyle("-fx-background-radius: 0; -fx-background-color: transparent");
        aboutButton.setStyle("-fx-background-radius: 0; -fx-background-color: transparent");
        borrowBook.setStyle("-fx-background-radius: 0; -fx-background-color: transparent");
        logoutB.setStyle("-fx-border-color: white; -fx-background-color: gray");
    }

    @FXML
    void addClick(ActionEvent event) throws IOException {
        App m = new App();
        m.changeScene("add_newbook.fxml");
        
    }

    @FXML
    void aboutClick(ActionEvent event) throws IOException {     //when clicked about button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void listClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListBook.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void searchClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchbook.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    @FXML
    void borrowClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowPage.fxml"));
        root = loader.load();
        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void LogoutClick(ActionEvent event) throws IOException {
        App m = new App();
        m.changeScene("login.fxml");
    }

    public void getInfo(String email, String password){     //getting user info from login page

        try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM user WHERE email = ? AND password = ?", email, password)){
            while(rs.next()){
                String username = rs.getString(1);
                profile.setText(username);

            }
        }catch(Exception ex){ex.printStackTrace();}
        
    }
    public void getInfo(String username){ //getting user info from other page
        //profile.setText(username);
    }

    public void getUsername(){
        
    }
}

