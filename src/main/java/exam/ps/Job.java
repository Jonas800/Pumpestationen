package exam.ps;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Job {

    private int id;
    private String title;

    public Job() {
    }

    public Job(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static ArrayList<Job> selectAllJobs() {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
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
    }
}
