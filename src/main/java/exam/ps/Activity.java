package exam.ps;

public class Activity {
    private String name;
    private String description;
    private String date;
    private Double startTime;
    private double endTime;

    public Activity(String name, String description, String date, Double startTime, double endTime) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public Double getStartTime() { return startTime; }

    public void setStartTime(Double startTime) { this.startTime = startTime; }

    public double getEndTime() { return endTime; }

    public void setEndTime(double endTime) { this.endTime = endTime; }
}
