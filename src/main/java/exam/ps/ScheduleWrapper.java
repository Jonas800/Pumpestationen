package exam.ps;

import java.util.ArrayList;
import java.util.List;

public class ScheduleWrapper {
    private List<Employee> employees = new ArrayList<Employee>(){};
    private List<Job> jobs;


    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
