package exam.ps;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeAndJob {

    private String name;
    private int employeeID;
    private int jobID;
    private String jobName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date = new Date();


    public EmployeeAndJob() {
    }

    public EmployeeAndJob(int employeeID, String name, int jobID, String jobName, String dateString) {
        this.name = name;
        this.employeeID = employeeID;
        this.jobID = jobID;
        this.jobName = jobName;
        setDateWithString(dateString);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateWithString(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = df.parse(dateString);
        }
        catch(ParseException pe){

        }
        setDate(date);

    }

}
