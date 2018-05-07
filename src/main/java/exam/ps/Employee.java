package exam.ps;

public class Employee {

    private int ID;
    private String firstName;
    private String lastName;
    private String cpr;
    private String address;
    private int phoneNumber;
    private String jobPosition;

    public Employee() {

    }
    public Employee(int id, String firstName, String lastName){
        this.ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(int ID, String firstName, String lastName, String cpr, String address, int phoneNumber, String jobPosition) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpr = cpr;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.jobPosition = jobPosition;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }
}


