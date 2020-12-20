import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {

    public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}

    private List<Person> addressBookList;
    private AddressBookDBService addressBookDBService;

    public AddressBookService()
    {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public AddressBookService(List<Person> addressBookList) {
        this();
        this.addressBookList = addressBookList;
    }

    public List<Person> readAddressBookForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
        if(ioService.equals(IOService.DB_IO))
            return addressBookDBService.getAddressBookForDateRange(startDate, endDate);
        return null;
    }

    public List<Person> countPeopleFromGivenState(IOService dbIo, String State) {
        return addressBookDBService.countPeopleFromGivenState(State);

    }

    public void addEmployeeToAddressBook(String firstName, String lastName, String address, String city, String state, int zip, int mobile, String email, LocalDate date) {
        addressBookList.add(addressBookDBService.addEntryToPayroll(firstName, lastName, address, city, state, zip, mobile, email, date));

    }

    public void addEmployeesToAddressBook(List<Person> personList) {
        personList.forEach(addressBookData -> {
            System.out.println("Employee being added: "+addressBookData.FirstName);
            this.addEmployeeToAddressBook(addressBookData.FirstName, addressBookData.LastName, addressBookData.Address,
                    addressBookData.City, addressBookData.State, addressBookData.Zip, addressBookData.MobileNumber,
                    addressBookData.EmailId, addressBookData.startDate);
            System.out.println("Employee added: "+addressBookData.FirstName);
        });
        System.out.println(this.addressBookList);
    }

    public long countEntries(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            return new AddressBookFileIOService().countEntries();
        return addressBookList.size();
    }

    public void printData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
             new AddressBookFileIOService().printData();
        else System.out.println(addressBookList);
    }

    public void updateMobileNumber(String FirstName, int MobileNumber) {
        int result = addressBookDBService.updateMobileNumber(FirstName, MobileNumber);
        if (result == 0) return;
        Person person = this.getAddressBookData(FirstName);
        if (person != null) person.MobileNumber = MobileNumber;
    }

    public boolean checkAddressBookInSyncWithDB(String FirstName) {
        List<Person> personList = addressBookDBService.getAddressBookData(FirstName);
        return personList.get(0).equals(getAddressBookData(FirstName));
    }

    private Person getAddressBookData(String firstName) {
        return this.addressBookList.stream()
                .filter(addressBookDataItem -> addressBookDataItem.FirstName.equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public List<Person> readAddressBookData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.addressBookList = addressBookDBService.readData();
        return this.addressBookList;
    }

}
