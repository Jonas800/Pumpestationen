package exam.ps;

import java.sql.*;
import java.util.ArrayList;

public class dbConn {


    static dbConn instance = new dbConn();
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/pumpestationen?useSSL=false";
    static Connection con;

    /**
     * we want to use JDBC protocol, mysql DBMS , the local host with
     * the database ap
     */

    public Connection createConnection() {
        con = null;
        try {
            Class.forName(JDBC_DRIVER);
            return con = DriverManager.getConnection(DATABASE_URL, "root", "bispeparken");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static dbConn getInstance() {
        return instance;
    }


}