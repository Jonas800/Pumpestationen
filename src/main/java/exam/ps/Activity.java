package exam.ps;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Activity {
    private int id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private double startTime;
    private double endTime;

    public Activity(int id, String name, String description, Date date, double startTime, double endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public void setStartTime(double startTime) { this.startTime = startTime; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public double getEndTime() { return endTime; }

    public void setEndTime(double endTime) { this.endTime = endTime; }

    public String toString() {
        return name + "#" + description + "#" + date + "#" + startTime + "#" + endTime;
    }

}
