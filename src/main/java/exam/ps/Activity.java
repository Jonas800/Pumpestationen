package exam.ps;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class Activity {
    private int id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDateTime;

    public Activity() {
    }

    public Activity(int id, String name, String description, Date startDateTime, Date endDateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }

    public Date getStartDateTime() { return startDateTime; }

    public void setStartDateTime(Date startDateTime) { this.startDateTime = startDateTime; }

    public Date getEndDateTime() { return endDateTime; }

    public void setEndDateTime(Date endDateTime) { this.endDateTime = endDateTime; }

    public String toString() {
        return id + "#" + name + "#" + description + "#" + startDateTime+ "#" + endDateTime;
    }
}
