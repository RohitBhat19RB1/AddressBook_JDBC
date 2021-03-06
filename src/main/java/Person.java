import java.time.LocalDate;
import java.util.Objects;

public class Person {

    public int  PersonId;
    public String FirstName;
    public String LastName;
    public String Address;
    public String City;
    public String State;
    public int Zip;
    public int MobileNumber;
    public String EmailId;
    public LocalDate startDate;
    public int id;
    public String person_type;

    public Person(int personID, String firstName, String lastName, String address, String city, String state, int zip, int mobileNumber, String emailId, LocalDate startDate) {
        this.PersonId = personID;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Address = address;
        this.City = city;
        this.State = state;
        this.Zip = zip;
        this.MobileNumber = mobileNumber;
        this.EmailId = emailId;
        this.startDate = startDate;

    }

    public Person(int id, String person_type) {
        this.id = id;
        this.person_type = person_type;
    }

    public Person(int personID, String firstName, String lastName, String address, String city, String state, int zip, int mobileNumber, String emailId, LocalDate startDate, String person_type) {
        this.PersonId = personID;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Address = address;
        this.City = city;
        this.State = state;
        this.Zip = zip;
        this.MobileNumber = mobileNumber;
        this.EmailId = emailId;
        this.startDate = startDate;
        this.person_type = person_type;

    }

    @Override
    public int hashCode() {
        return Objects.hash(FirstName, LastName, Address, City, State, Zip, MobileNumber, EmailId, startDate, person_type);
    }

    @Override
    public String toString() { return "PersonId=" + PersonId + ", FirstName=" +
            FirstName + ", LastName=" + LastName + ", Address=" + Address + ", City=" + City
            + ", State=" + State + ", Zip=" + Zip + ", MobileNumber=" + MobileNumber + ", EmailId=" + EmailId + ", startDate=" + startDate + ", person_type=" + person_type;}

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        return PersonId == that.PersonId &&
                Double.compare(that.MobileNumber, MobileNumber) == 0 &&
                FirstName.equals(that.FirstName);
    }

}
