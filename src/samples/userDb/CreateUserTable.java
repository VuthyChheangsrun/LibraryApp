package samples.userDb;

import java.sql.Statement;

import samples.db.ConnectDB;


public class CreateUserTable {
    public static void main(String[] args)
        
    {
        ConnectDB conn = ConnectDB.getConnection();
        if(conn.isError()){
            System.err.println(conn.getException());
            System.exit(-1);
        }
        try (Statement stmt = conn.getDb().createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS user
                (
                    username TEXT,
                    email TEXT,
                    password TEXT
                )
            """);
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }
}
