import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import samples.db.ConnectDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class searchbook {

    @FXML
    private TableView<BookList> BookTableview;

    @FXML
    private ObservableList<BookList> data = FXCollections.observableArrayList(
        new BookList(1, "asd", "qeq"),
        new BookList(2, "as2d", "qe2q"),
        new BookList(3, "as1d", "qe1q")
    );

    @FXML
    private TableColumn<BookList, String> AuthorTableColunm;

    @FXML
    private TableColumn<BookList, Integer> BookIDTableColumn;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private TableColumn<?, ?> PublisherTableColunm;

    @FXML
    private TableColumn<BookList, String> TitleTableColunm;

    @FXML
    private TextField KeywordTextfield;

    @FXML
    private DefaultTableModel tableModel;

    @FXML
    private JTextField searchField;

    @FXML
    private Button searchButton;

    
    @FXML
    void searchClick(ActionEvent event) {
        BookIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
    }

}