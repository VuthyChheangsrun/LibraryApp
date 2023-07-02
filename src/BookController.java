

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BookController implements Initializable {

    @FXML
    private TextField bookField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField titleField;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> idCol;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private Button btnHome;

    @FXML
    private TableColumn<Book, String> yearCol;

    @FXML
    private TableColumn<Book, String> categoryCol;

    @FXML
    private TableColumn<Book, String> statusCol;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    
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
    void autoGenerate(MouseEvent event) throws SQLException {
        int id = 0;
        Connection conn = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM book";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("bookId") + 1;
        }
        String convert = Integer.toString(id);
        bookField.setText(convert);
    }

    @FXML
    void getItem(MouseEvent event) {
        Integer index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        bookField.setText(idCol.getCellData(index).toString());
        titleField.setText(titleCol.getCellData(index).toString());
        yearField.setText(yearCol.getCellData(index).toString());
        categoryField.setText(categoryCol.getCellData(index).toString());
        statusField.setText(statusCol.getCellData(index).toString());
        authorField.setText(authorCol.getCellData(index).toString());
    }

    @FXML
    void handleClear(ActionEvent event) {
        bookField.setText(null);
        titleField.setText(null);
        categoryField.setText(null);
        yearField.setText(null);
        statusField.setText(null);
        authorField.setText(null);
    }

    @FXML
    void handleDelete(ActionEvent event) {
        String bookId = bookField.getText();
        int id = Integer.parseInt(bookId);
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "DELETE FROM book WHERE bookId=?";
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

                handleClear(event);
                showBook();
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
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();
        String category = categoryField.getText();
        String status = statusField.getText();
        if (title == "" || title == null || author == "" || author == null || year == "" || year == null
                || category == "" || category == null || status == "" || status == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlInsert = "INSERT INTO `book`(`title`, `author`, `year`, `category`, `status`) VALUES (?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sqlInsert);
                statement.setString(1, title);
                statement.setString(2, author);
                statement.setString(3, year);
                statement.setString(4, category);
                statement.setString(5, status);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Insert success.");
                    alert.showAndWait();
                    bookField.setText(null);
                    titleField.setText(null);
                    categoryField.setText(null);
                    yearField.setText(null);
                    statusField.setText(null);
                    authorField.setText(null);

                    showBook();

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
    void handleSearch(ActionEvent event) throws SQLException {
        showBook();
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        String bookId = bookField.getText();
        int id;
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();
        String category = categoryField.getText();
        String status = statusField.getText();

        if (title == "" || title == null || author == "" || author == null || year == "" || year == null
                || category == "" || category == null || status == "" || status == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlSelect = "SELECT * FROM book";
                PreparedStatement statement = conn.prepareStatement(sqlSelect);
                ResultSet rs = statement.executeQuery();
                id = Integer.parseInt(bookId);
                while (rs.next()) {
                    if (rs.getInt("bookId") == id) {
                        String sqlUpdate = "UPDATE book SET title= ? ,author= ? ,year= ?, category = ?, status = ? WHERE bookId = ? ";
                        PreparedStatement statement2 = conn.prepareStatement(sqlUpdate);
                        statement2.setString(1, title);
                        statement2.setString(2, author);
                        statement2.setString(3, year);
                        statement2.setString(4, category);
                        statement2.setString(5, status);
                        statement2.setInt(6, id);

                        statement2.executeUpdate();

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Update success.");
                        alert.showAndWait();

                        bookField.setText(null);
                        titleField.setText(null);
                        categoryField.setText(null);
                        yearField.setText(null);
                        statusField.setText(null);
                        authorField.setText(null);

                        showBook();

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

    public ObservableList<Book> getBooksList() throws SQLException {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM book";
            // Search Function
            String search = searchField.getText();
            if (search != "") {
            sql += " WHERE title LIKE '%" + search + "%'";
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Book book;
            while (resultSet.next()) {
                book = new Book(resultSet.getString("bookId"), resultSet.getString("title"),
                        resultSet.getString("author"), resultSet.getString("year"),
                        resultSet.getString("category"), resultSet.getString("status"));
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;

    }

    public void showBook() throws SQLException {
        ObservableList<Book> list = getBooksList();
        idCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Book, String>("year"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
        tableView.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showBook();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRefresh(MouseEvent event) throws SQLException {
        String refresh = searchField.getText();
        if(refresh == null || refresh == ""){
            showBook();
        }
    }

}
