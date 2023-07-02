//package controllers;

import java.beans.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//import configs.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import util.IsNullAndEmpty;

public class ReturnBookController implements Initializable {

    @FXML
    private Button btnreturn;

    @FXML
    private Button swapborrow;

    @FXML
    private TableView<Borrow> tableView;

    @FXML
    private TableColumn<Borrow, String> idCol;

    @FXML
    private TableColumn<Borrow, String> nameCol;

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
    private ComboBox selectCombo;

    @FXML
    private TextField idField;

    @FXML
    void doReturn(ActionEvent event) {
        
        String id = idField.getText();
        String update = selectCombo.getSelectionModel().getSelectedItem().toString();
        if(id!=""||update!=""){
            try(Connection conn = DatabaseConnector.getConnection()) {
                String sql = "UPDATE borrow SET isReturn= ? WHERE borrowId= ?";
                
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, update);
                statement.setString(2, id);
                statement.executeUpdate();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("updated!");
                alert.showAndWait();
                showBorrows();
    
    
            } catch (SQLException e) {
                System.out.println(e);
            }
        }else{
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Fail update!");
                alert.showAndWait();
        }
        
    }

    @FXML
    void getItem(MouseEvent event) {
        Integer index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        idField.setText(idCol.getCellData(index).toString());
    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

    @FXML
    void extReturn(MouseDragEvent event) throws SQLException {

    }

    @FXML
    private Button btnHome;

    @FXML
    void swtHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }


    @FXML
    void swtBorrow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Borrowpage.fxml"));
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
            String sql = "SELECT * FROM borrow WHERE isReturn = 'No Returned' ";
            // //Search Function
            // String search = searchField.getText();
            // if (search != "") {
            // sql += " WHERE name LIKE '%" + search + "%'";
            // }
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Borrow borrows;
            // while (resultSet.next()) {
            //     borrows = new Borrow(resultSet.getString("borrowId"), resultSet.getString("name"),
            //             resultSet.getString("schoolId"), resultSet.getString("borrowDate"),
            //             resultSet.getString("returnDate"), resultSet.getString("book"),
            //             resultSet.getString("isReturn"));
            //     borrowList.add(borrows);
            // }
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
        ObservableList<String> list = FXCollections.observableArrayList("Returned", "Not Return");
        selectCombo.setItems(list);
        try {
            showBorrows();
            getBooksList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
