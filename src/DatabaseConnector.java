import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
<<<<<<< HEAD
        String url = "jdbc:sqlite:C:/Users/YES/Documents/ITC_Year_3/Semester 02/IPE G11/LibraryApp - Copy/DbLibrary_app.db";

=======
        String url = "jdbc:sqlite:/D:/I3 Document/Semester 2/LibraryApp/DbLibrary_app.db";
>>>>>>> fe2a6ebd7c5cea6b7bc647687a18ddcacdddb057
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}
