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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ListBookController implements Initializable {

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private TableColumn<Book, String> categoryCol;

    @FXML
    private Button homebtn;

    @FXML
    private TableColumn<Book, String> idCol;

    @FXML
    private Button searchbtn;

    @FXML
    private TextField searchfield;

    @FXML
    private TableColumn<Book, String> statusCol;

    @FXML
    private TableView<Book> tableVeiw;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, String> yearCol;

    @FXML
    void getItem(MouseEvent event) {

    }

    @FXML
    void handlehome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void handlesearch(ActionEvent event) throws SQLException {
        showBook();
    }

    @FXML
    void tableView(KeyEvent event) {

    }

    public ObservableList<Book> getBooksList() throws SQLException {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM book";
            // Search Function
            String search = searchfield.getText();
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
        tableVeiw.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showBook();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
