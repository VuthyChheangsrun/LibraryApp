import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import samples.db.ConnectDB;

public class home {

    @FXML
    private Label profile;

    public void getInfo(String email, String password){

        try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM user WHERE email = ? AND password = ?", email, password)){
            while(rs.next()){
                String username = rs.getString(1);
                profile.setText(username);

            }
        }catch(Exception ex){ex.printStackTrace();}
        
    }
}

