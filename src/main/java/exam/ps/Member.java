package exam.ps;

public class Member {
    private String firstName;
    private String lastName;
    private int age;
    private String CPR;
    private int id;

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
        return age;
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

    public Member(){

    }

    public Member(String firstName, String lastName, int age, String CPR, int id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.CPR = CPR;
        this.id = id;


    }
}
