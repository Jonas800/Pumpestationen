package exam.ps;

public class Member {
    private String firstName;
    private String lastName;
    private int age;
    private String CPR;
    private int id;
    private int kontingent;

    public Member(){

    }

    public Member(String firstName, String lastName, int age, String CPR, int id, int kontingent){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.CPR = CPR;
        this.id = id;
        this.kontingent = kontingent;

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