package samples.userDb;

import samples.db.ConnectDB;

public class InsertUser {
    public static void main(String[] args) {
        CreateUserTable.main(args);
        try (ConnectDB conn = ConnectDB.getConnection();) {
            int ret = conn.executeUpdate("INSERT INTO user(username,email,password) VALUES(?,?,?)", "Srun","chheangsrun@gmail.com","123");
            System.out.println(ret);
            ret = conn.executeUpdate("INSERT INTO user(username,email,password) VALUES(?,?,?)", "Chan","channy@gmail.com","123");
            System.out.println(ret);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(String username, String email, String password){
        
        CreateUserTable.main(null);
        try (ConnectDB conn = ConnectDB.getConnection();) {
            int ret = conn.executeUpdate("INSERT INTO user(username,email,password) VALUES(?,?,?)", username,email,password);
            System.out.println(ret);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
