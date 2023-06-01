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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class BorrowController implements Initializable {

    @FXML
    private TextField borrowIdField;

    @FXML
    private TextField bookField;

    @FXML
    private TextField returnDateField;

    @FXML
    private TextField borrowDateField;

    @FXML
    private TextField schoolIdField;

    @FXML
    private TextField nameField;

    @FXML
    private Button btnInsert;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

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
    void getItem(MouseEvent event) {
        Integer index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        borrowIdField.setText(idCol.getCellData(index).toString());
        nameField.setText(nameCol.getCellData(index).toString());
        schoolIdField.setText(sidCol.getCellData(index).toString());
        borrowDateField.setText(bdCol.getCellData(index).toString());
        returnDateField.setText(rdCol.getCellData(index).toString());
        bookField.setText(bookCol.getCellData(index).toString());
    }

    @FXML
    void handleClear(ActionEvent event) {
        borrowIdField.setText(null);
        nameField.setText(null);
        schoolIdField.setText(null);
        borrowDateField.setText(null);
        returnDateField.setText(null);
        bookField.setText(null);
    }

    @FXML
    void handleDelete(ActionEvent event) {
        String borrowId = borrowIdField.getText();
        int id = Integer.parseInt(borrowId);
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "DELETE FROM borrow WHERE borrowId=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                // System.out.println("Record deleted successfully.");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Record deleted successfully.");
                alert.showAndWait();

                borrowIdField.setText(null);
                nameField.setText(null);
                schoolIdField.setText(null);
                borrowDateField.setText(null);
                returnDateField.setText(null);
                bookField.setText(null);

                showBorrows();
            } else {
                // System.out.println("Record not found.");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Fail delete!!.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleInsert(ActionEvent event) {
        String name = nameField.getText();
        String schoolId = schoolIdField.getText();
        String borrowDate = borrowDateField.getText();
        String returnDate = returnDateField.getText();
        String book = bookField.getText();
        if (name == "" || name == null || schoolId == "" || schoolId == null || borrowDate == "" || borrowDate == null
                || returnDate == "" || returnDate == null || book == "" || book == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlInsert = "INSERT INTO `borrow`(`name`, `schoolId`, `borrowDate`, `returnDate`, `book`) VALUES (?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sqlInsert);
                statement.setString(1, name);
                statement.setString(2, schoolId);
                statement.setString(3, borrowDate);
                statement.setString(4, returnDate);
                statement.setString(5, book);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Insert success.");
                    alert.showAndWait();
                    borrowIdField.setText(null);
                    nameField.setText(null);
                    schoolIdField.setText(null);
                    borrowDateField.setText(null);
                    returnDateField.setText(null);
                    bookField.setText(null);

                    showBorrows();

                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Fail");
                    alert.setHeaderText(null);
                    alert.setContentText("Fail insert!!");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Fail insert!!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        String borrowId = borrowIdField.getText();
        int id;
        String name = nameField.getText();
        String schoolId = schoolIdField.getText();
        String borrowDate = borrowDateField.getText();
        String returnDate = returnDateField.getText();
        String book = bookField.getText();

        if (name == "" || name == null || schoolId == "" || schoolId == null || borrowDate == "" || borrowDate == null
                || returnDate == "" || returnDate == null || book == "" || book == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlSelect = "SELECT * FROM borrow";
                PreparedStatement statement = conn.prepareStatement(sqlSelect);
                ResultSet rs = statement.executeQuery();
                id = Integer.parseInt(borrowId);
                while (rs.next()) {
                    if (rs.getInt("borrowId") == id) {
                        String sqlInsert = "UPDATE borrow SET name= ? ,schoolId= ? ,borrowDate= ?, returnDate = ?, book = ? WHERE borrowId = ? ";
                        PreparedStatement statement2 = conn.prepareStatement(sqlInsert);
                        statement2.setString(1, name);
                        statement2.setString(2, schoolId);
                        statement2.setString(3, borrowDate);
                        statement2.setString(4, returnDate);
                        statement2.setString(5, book);
                        statement2.setInt(6, id);

                        statement2.executeUpdate();

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Update success.");
                        alert.showAndWait();

                        borrowIdField.setText(null);
                        nameField.setText(null);
                        schoolIdField.setText(null);
                        borrowDateField.setText(null);
                        returnDateField.setText(null);
                        bookField.setText(null);

                        showBorrows();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Fail insert!");
                alert.showAndWait();
            }

        }
    }

    public ObservableList<Borrow> getBooksList() throws SQLException {
        ObservableList<Borrow> borrowList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM borrow";
            // Search Function
            String search = searchField.getText();
            if (search != "") {
                sql += " WHERE name LIKE '%" + search + "%'";
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Borrow borrows;
            while (resultSet.next()) {
                borrows = new Borrow(resultSet.getString("borrowId"), resultSet.getString("name"),
                        resultSet.getString("schoolId"), resultSet.getString("borrowDate"),
                        resultSet.getString("returnDate"), resultSet.getString("book"));
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
        bookCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("book"));
        tableView.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showBorrows();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void autoGenerate(MouseEvent event) throws SQLException {
        int id = 0;
        Connection conn = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM borrow";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("borrowId") + 1;
        }
        String convert = Integer.toString(id);
        borrowIdField.setText(convert);
    }

    @FXML
    void handleSearch(ActionEvent event) throws SQLException {
        showBorrows();
    }

}
