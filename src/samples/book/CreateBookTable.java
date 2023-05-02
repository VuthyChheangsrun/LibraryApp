package samples.book;

import java.sql.Statement;

import samples.db.ConnectDB;

public class CreateBookTable {
    public static void main(String[] args) {
        ConnectDB conn = ConnectDB.getConnection();
        if(conn.isError()){
            System.err.println(conn.getException());
            System.exit(-1);
        }
        try (Statement stmt = conn.getDb().createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS books
                (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    rating DECIMAL,
                    price DECIMAL,
                    author TEXT,
                    publishDate TEXT,
                    genre TEXT,
                    amount INTEGER

                )
            """);
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }
}
