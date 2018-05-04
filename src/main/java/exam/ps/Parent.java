package exam.ps;

public class Parent {
    String firstName;
    String lastName;
    String address;
    String telefonNumber;
    int id;

    public Parent(int id, String firstName, String lastName, String address, String telefonNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telefonNumber = telefonNumber;

    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }

    public void setAddress(String address) { address = address; }

    public String getTelefoneNumber() { return telefonNumber; }

    public void setTelefoneNumber(String telefoneNumber) { this.telefonNumber = telefoneNumber; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
