import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import java.util.List;

public class AddressBookServiceTest {
    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        AddressBookService addressBookService = new  AddressBookService();
        List<Person> addressBookData = addressBookService
                .readAddressBookData(AddressBookService.IOService.DB_IO);
        Assert.assertEquals(3, addressBookData.size());
    }

    @Test
    public void givenNewMobileNumberForEmployee_WhenUpdated_ShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookData = addressBookService
                .readAddressBookData(AddressBookService.IOService.DB_IO);
        addressBookService.updateMobileNumber("Jugal", 882266442);
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Jugal");
        Assert.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEntryCount() {
        AddressBookService addressBookService = new AddressBookService();
       // addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2017, 3, 7);
        LocalDate endDate = LocalDate.now();
        List<Person> addressBookData =
                addressBookService.readAddressBookForDateRange(AddressBookService
                        .IOService.DB_IO, startDate, endDate);
        Assert.assertEquals(3, addressBookData.size());
    }

    @Test
    public void givenState_WhenRetrieved_ShouldMatchEntryCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookData =
                addressBookService.countPeopleFromGivenState(AddressBookService
                        .IOService.DB_IO, "AndhraPradesh");
        Assert.assertEquals(2, addressBookData.size());
    }

    @Test
    public void givenNewEntry_WhenAdded_ShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        addressBookService.addEmployeeToAddressBook("Mark", "Keats",
                    "Greater-Boston","Boston", "Massachusetts",
                        432415, 98226256,"mark.keats@gmail.com",LocalDate.now());
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Mark");
        Assert.assertTrue(result);
    }


}
