import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:/D:/I3 Document/Semester 2/LibraryApp/DbLibrary_app.db";
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}
