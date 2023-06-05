package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import configs.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReturnBookController implements Initializable {

    @FXML
    private TextField fsfe;

    @FXML
    private Button swapborrow;

    @FXML
    private SplitMenuButton dfd;
    
    @FXML
    private TableView<Borrow> tableView;

    @FXML
    private TableColumn<Borrow, String> idCol;

    @FXML
    private TableColumn<Borrow, String>  nameCol;

    @FXML
    private TableColumn<Borrow, String> sidCol;

    @FXML
    private TableColumn<Borrow, String> bdCol;

    @FXML
    private TableColumn<Borrow, String> rdCol;

    @FXML
    private TableColumn<Borrow, String> bookCol;

    @FXML
    private TableColumn<Borrow, String> bookCol1;

    @FXML
    private TableColumn<Borrow, String> telephoneCol;

    @FXML
    private TableColumn<Borrow, String> ReturnCol;
    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    void doReturn(ActionEvent event) throws SQLException{
       showBorrows();
    }

    @FXML
    void getItem(MouseEvent event) {

    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

    @FXML
    void extReturn(MouseDragEvent event) throws SQLException{
        showBorrows() ;
    }

    
    @FXML
    void swtBorrow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/Borrowpage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }
    

    public ObservableList<Borrow> getBooksList() throws SQLException {
        ObservableList<Borrow> borrowList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM borrow";
            // //Search Function
            // String search = searchField.getText();
            // if (search != "") {
            // sql += " WHERE name LIKE '%" + search + "%'";
            // }

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Borrow borrows;
            while (resultSet.next()) {
                borrows = new Borrow(resultSet.getString("borrowId"), resultSet.getString("name"),
                        resultSet.getString("schoolId"), resultSet.getString("borrowDate"),
                        resultSet.getString("returnDate"), resultSet.getString("book"),resultSet.getString("isReturn"));
                borrowList.add(borrows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowList;

    }

    public void showBorrows() throws SQLException {
        ObservableList<Borrow> list = getBooksList();
        idCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("borrowId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("name"));
        sidCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("schoolId"));
        bdCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("borrowDate"));
        rdCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("returnDate"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("book"));
        ReturnCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("isReturn"));

        tableView.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       try {
        showBorrows();
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }

    
}
