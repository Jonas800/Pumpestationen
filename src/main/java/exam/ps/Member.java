package exam.ps;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
public class Member {
    private String firstName;
    private String lastName;
    private int age;
    private String CPR;
    private int id;
    private int kontingent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth = new Date();
    private String address;
    private int zipcode;
    private String city;
    public Member(){

    }

    public Member(String firstName, String lastName, int age, String CPR, int id, int kontingent, Date dateOfBirth, int zipcode, String city){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.CPR = CPR;
        this.id = id;
        this.kontingent = kontingent;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.zipcode = zipcode;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public int getAge() {
        Date date = new Date();
        age = date.getYear() - dateOfBirth.getYear();

        return age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKontingent(){
        return kontingent;
    }

    public void setKontingent(int kontingent){
        if(age < 14){
            kontingent = 508;
        } else {
            kontingent = 0;
        }
        this.kontingent = kontingent;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Member{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", CPR='" + CPR + '\'' +
                ", id=" + id +
                ", kontingent=" + kontingent +
                '}';
    }

}

