import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:/D:/ITC_Year_3/LibraryApp/DbLibrary_app.db";
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}
