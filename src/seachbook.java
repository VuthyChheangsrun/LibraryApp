

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.Node;

public class seachbook implements Initializable {
    @FXML
    private TextField authortxt;
    @FXML
    private Button backbtn;
    @FXML
    private TextField searchbookid;
    @FXML
    private TextField titletxt;
    @FXML
    private TextField yeartxt;
    @FXML
    private TextField categorytxt;
    @FXML
    private TableView<Book> TableView;
    @FXML
    private TableColumn<Book, Integer> idColumn;  
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, Integer> yearColumn;
    @FXML
    private TableColumn<Book, String> pagesColumn;

	Stage stage;
	Scene scene;
    Connection con;

	@FXML
    void goback(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Library.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    
    public void executeQuery(String query) {
    	Connection conn = getConnection();
    	Statement st;
    	try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	showBooks();
    }
    
    public Connection getConnection() {
    	Connection con;
    	try {
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup","root","");
    		return con;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    public void ConnectToBook(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:/signup","root","");
            System.out.println("sucessfully connected yayyyy");
        } catch (ClassNotFoundException ex) {
          System.out.println("hikhik");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void search(ActionEvent event) throws IOException{
        ResultSet rs = null;
        PreparedStatement pst = null;
        ConnectToBook();
        String id = searchbookid.getText();
        String sql = "select *from books where id= '" + id + "'";
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                titletxt.setText(rs.getString(2));
                authortxt.setText(rs.getString(3));
                yeartxt.setText(rs.getString(4));
                categorytxt.setText(rs.getString(5));
                rs.close();
                pst.close();
            }else {
                JOptionPane.showMessageDialog(null, "The book is not in the database");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }

    }
    
    public ObservableList<Book> getBooksList(){
    	ObservableList<Book> booksList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM books ";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Book books;
			while(rs.next()) {
				books = new Book(rs.getInt("Id"),rs.getString("Title"),rs.getString("Author"),rs.getString("Year"),rs.getString("Category"));
				booksList.add(books);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return booksList;
    }
    
    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showBooks() {
    	ObservableList<Book> list = getBooksList();
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<Book,Integer>("id"));
    	titleColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
    	authorColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
    	yearColumn.setCellValueFactory(new PropertyValueFactory<Book,Integer>("year"));
    	pagesColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("category"));
    	
    	TableView.setItems(list);
    }
}