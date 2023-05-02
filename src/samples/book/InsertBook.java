package samples.book;

import samples.db.ConnectDB;

public class InsertBook {
    public static void main(String[] args) {
        CreateBookTable.main(args);
        try (ConnectDB conn = ConnectDB.getConnection();) {
            int ret = conn.executeUpdate("INSERT INTO books(name, rating, price, author, publishDate, genre, amount) VALUES(?,?,?,?,?,?,?)", "");
            System.out.println(ret);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBook(String name, Double rating, Double price, String author, String publishDate, String genre, Integer amount){
        CreateBookTable.main(null);
        try (ConnectDB conn = ConnectDB.getConnection();) {
            int ret = conn.executeUpdate("INSERT INTO books(name, rating, price, author, publishDate, genre, amount) VALUES(?,?,?,?,?,?,?)", name, rating, price, author, publishDate, genre, amount);
            System.out.println(ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
