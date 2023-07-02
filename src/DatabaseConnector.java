import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:C:/Users/YES/Documents/ITC_Year_3/Semester 02/IPE G11/LibraryApp - Copy/DbLibrary_app.db";

        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}
