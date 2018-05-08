package exam.ps;

import org.springframework.format.annotation.DateTimeFormat;


public class Activity {
    private int id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "EEE MMM dd kk:mm:ss z yyyy")
    private DateTimeFormat startDateTime;
    private DateTimeFormat endDateTime;

    public Activity() {
    }

    public Activity(int id, String name, String description, DateTimeFormat startDateTime, DateTimeFormat endDateTime) {
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

    public DateTimeFormat getStartDateTime() { return startDateTime; }

    public void setStartDateTime(DateTimeFormat startDateTime) { this.startDateTime = startDateTime; }

    public DateTimeFormat getEndDateTime() { return endDateTime; }

    public void setEndDateTime(DateTimeFormat endDateTime) { this.endDateTime = endDateTime; }


    public String toString() {
        return id + "#" + name + "#" + description + "#" + startDateTime + "#" + endDateTime;
    }
}
