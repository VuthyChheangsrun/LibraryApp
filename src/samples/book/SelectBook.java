package samples.book;

import java.sql.ResultSet;

import samples.db.ConnectDB;

public class SelectBook {
    public static void main(String[] args) {
        try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM books")){
            while(rs.next()){
                System.out.printf("%-10d%-10s%-10f%-10f%-25s%-10s%-10s%-10d\n", 
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getDouble(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getInt(8));
            }
        }catch(Exception ex){ex.printStackTrace();}
    }
    
}
