package exam.ps;

public class Employee extends Login {

    private int ID;
    private String firstName;
    private String lastName;
    private String cpr;
    private String address;
    private String phoneNumber;
    private String jobPosition;
    private String city;
    private int zipcode;

    public Employee() {

    }


    public Employee(int id, String firstName, String lastName){
        this.ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(int ID, String firstName, String lastName, String cpr, String address, String phoneNumber, String jobPosition, String city) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpr = cpr;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.jobPosition = jobPosition;

        }

  

    public Employee(String userName, String passWord, int ID) {
        super(userName, passWord);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Employee{" + "ID=" + ID +
                ", firstName" + "='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cpr='" + cpr + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", jobPosition='" + jobPosition + '\'' +
                '}';
    }
}


