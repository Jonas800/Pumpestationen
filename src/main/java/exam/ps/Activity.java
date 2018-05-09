package exam.ps;

import org.springframework.format.annotation.DateTimeFormat;


public class Activity {
    private int id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    public Activity() {
    }

    public Activity(int id, String name, String description, String startDate, String endDate, String startTime, String endTime) {
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

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String toString() {
        return id + "#" + name + "#" + description + "#" + startDate+ "#" + startTime + "#" + endDate+ "#" + endTime;
    }
}
