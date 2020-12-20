
import com.mysql.jdbc.Connection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private int connectionCounter = 0;
    private static AddressBookDBService addressBookDBService;
    private PreparedStatement addressBookDataStatement;

    public static AddressBookDBService getInstance() {
        if (addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    public List<Person> readData() {
        String sql = "SELECT * FROM Person;";
        return this.getAddressBookDataUsingDB(sql);
    }

    private List<Person> getAddressBookDataUsingDB(String sql) {
        List<Person> addressBookList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }

    private List<Person> getAddressBookData(ResultSet resultSet) {
        List<Person> addressBookList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int PersonID = resultSet.getInt("PersonID");
                String FirstName = resultSet.getString("FirstName");
                String LastName = resultSet.getString("LastName");
                String Address = resultSet.getString("Address");
                String City = resultSet.getString("City");
                String State = resultSet.getString("State");
                int Zip = resultSet.getInt("Zip");
                int MobileNumber = resultSet.getInt("MobileNumber");
                String EmailId = resultSet.getString("EmailId");
                LocalDate startDate = resultSet.getDate("date_added").toLocalDate();
                addressBookList.add(new Person(PersonID, FirstName, LastName, Address, City, State, Zip, MobileNumber, EmailId, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }

    private synchronized Connection getConnection() throws SQLException {
        connectionCounter++;
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book?useSSL=false";
        String userName = "root";
        String password = "123456";
        Connection con;
        System.out.println("Processing Thread: " + Thread.currentThread().getName() +
                   "...  Connecting to database with Id:  " + connectionCounter);
        con = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Processing Thread: " + Thread.currentThread().getName() +
                   "...  Id: " + connectionCounter + "...Connection is Successful!!   " + con);
        return con;
    }

    public int updateMobileNumber(String firstName, int mobileNumber) {
        return this.updateAddressBookDataUsingStatement(firstName, mobileNumber);

    }

    private int updateAddressBookDataUsingStatement(String firstName, int mobileNumber) {
        String sql = String.format("update Person set MobileNumber = %s where FirstName = '%s';", mobileNumber, firstName);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Person> getAddressBookData(String firstName) {
        List<Person> personList = null;
        if (this.addressBookDataStatement == null)
            this.prepareStatementForAddressBookData();
        try {
            addressBookDataStatement.setString(1, firstName);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            personList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }


    private void prepareStatementForAddressBookData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM Person WHERE FirstName = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Person> getAddressBookForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM Person WHERE date_added BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getAddressBookDataUsingDB(sql);
    }

    public List<Person> countPeopleFromGivenState(String state) {
        String sql = String.format("SELECT * FROM Person WHERE State =  '%s';",
                state);
        return this.getAddressBookDataUsingDB(sql);
    }

    public Person addEntryToPayroll(String firstName, String lastName, String address, String city, String state, int zip, int mobile, String email, LocalDate date) {
        int entryId = -1;
        Person person = null;
        String sql = String.format("INSERT INTO Person (FirstName,LastName, Address, City, State, Zip, MobileNumber, EmailId, date_added) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", firstName, lastName, address, city, state, zip, mobile, email, Date.valueOf(date));

        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement ();
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if(rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys ();
                if(resultSet.next()) entryId = resultSet.getInt( 1);
            }
            person = new Person(entryId, firstName,lastName, address,city, state, zip, mobile, email,date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }
}
