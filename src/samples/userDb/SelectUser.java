package samples.userDb;
import java.sql.ResultSet;

import samples.db.ConnectDB;

public class SelectUser {

    public static void main(String[] args) {
        try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM user")){
            while(rs.next()){
                System.out.printf("%-10s%-25s%10s\n", 
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3));
            }
        }catch(Exception ex){ex.printStackTrace();}   
    }

    public static boolean search(String email, String password) {

        try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM user WHERE email = ? AND password = ?", email, password)){
            while(rs.next()){
                System.out.printf("%-10s%-25s%-10s\n", 
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3));
                return true;
            }
        }catch(Exception ex){ex.printStackTrace();}  
        return false; 
    }
    public static boolean searchSignup(String username, String email) {

        String email_ = email;
        String name = username;

        try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM user WHERE email = ? OR username = ?", email_, name)){
            while(rs.next()){
                System.out.printf("%-10s%-25s%-10s\n", 
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3));
                return true;
            }
        }catch(Exception ex){ex.printStackTrace();}  
        return false; 
    }
}
