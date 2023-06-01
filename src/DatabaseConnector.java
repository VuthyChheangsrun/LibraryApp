package configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:/C:/Users/YES/Documents/ITC_Year_3/Semester 02/IPE G11/today/javafx-sdk-20.0.1/DbLibrary_app.db";
        
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}
