package MBTIDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection DBConnect(){
        Connection con = null;
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "KMJ";
        String password = "0914";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 접속 성공!");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return con;
    }
}
