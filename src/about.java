import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class about {

    @FXML
    private Label profile;

    @FXML
    private Button homeButton;

    
    private Stage stage;
	private Scene scene;
	private Parent root;
    
    @FXML
    void homeClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        root = loader.load();
        
        home ab = (home)loader.getController();
        ab.getInfo(profile.getText());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getInfo(String username){
        profile.setText(username);
    }
}
