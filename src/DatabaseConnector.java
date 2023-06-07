import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
<<<<<<< HEAD
        String url = "jdbc:sqlite:C:/Users/YES/Documents/ITC_Year_3/Semester 02/IPE G11/LibraryApp/DbLibrary_app.db";

=======
        String url = "jdbc:sqlite:/D:/I3 Document/Semester 2/LibraryApp/DbLibrary_app.db";
>>>>>>> d36baed1a5c6fede6802525ffa3a79f43985e731
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}
