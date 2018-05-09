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
            return con = DriverManager.getConnection(DATABASE_URL, "root", "rootpw");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

/*    public ArrayList<Job> selectAllJobs() {
        createConnection();
        Statement s = null;
        ArrayList<Job> allJobs = new ArrayList<>();
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM jobs");
            while (rs.next()) {
                try {
                    Job job = new Job();
                    job.setId(rs.getInt("job_id"));
                    job.setTitle(rs.getString("job_title"));
                    allJobs.add(job);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allJobs;
    }*/

//    public void insertJob(Job job) {
//        createConnection();
//        PreparedStatement ps = null;
//        try {
//            ps = con.prepareStatement("INSERT INTO jobs (job_title) VALUES(?)");
//            ps.setString(1, job.getTitle());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void updateJob(Job job) {
//        createConnection();
//        PreparedStatement ps = null;
//        try {
//            ps = con.prepareStatement("UPDATE jobs SET job_title = ? WHERE job_id = ?");
//            ps.setString(1, job.getTitle());
//            ps.setInt(2, job.getId());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteJob(Job job) {
//        createConnection();
//        PreparedStatement ps = null;
//        try {
//            ps = con.prepareStatement("DELETE FROM jobs WHERE job_id = ?");
//            ps.setInt(1, job.getId());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static dbConn getInstance() {
        return instance;
    }


}