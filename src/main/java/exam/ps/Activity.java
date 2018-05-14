package exam.ps;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;


public class Activity {
    private int id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @DateTimeFormat(pattern = "HH:mm")
    private Time startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private Time endTime;

    public Activity() {
    }

    public Activity(int id, String name, String description, Date startDate, Date endDate, Time startTime, Time endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public Time getStartTime() { return startTime; }

    public void setStartTime(Time startTime) { this.startTime = startTime; }

    public Time getEndTime() { return endTime; }

    public void setEndTime(Time endTime) { this.endTime = endTime; }

    public String toString() {
        return id + "#" + name + "#" + description + "#" + startDate+ "#" + endDate +"#" + startTime+ "#" + endTime;
    }
}
