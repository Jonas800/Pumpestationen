package exam.ps;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
public class Member extends Person{
    private int age;
    private int kontingent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth = new Date();

    public Member(){

    }

    public int getAge() {
        Date date = new Date();
        age = date.getYear() - dateOfBirth.getYear();

        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        getAge();
    }

}

