import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIOService {
    public static String ADDRESSBOOK_FILE_NAME = "addressBook-file.txt";

    public void writeData(List<Person> addressBookList) {
        StringBuffer empBuffer = new StringBuffer();
        addressBookList.forEach(employee -> {
            String employeeDataString = employee.toString().concat("\n");
            empBuffer.append(employeeDataString);
        });

        try {
            Files.write(Paths.get(ADDRESSBOOK_FILE_NAME), empBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printData() {
        try {
            Files.lines(new File(ADDRESSBOOK_FILE_NAME).toPath())
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public long countEntries() {
        long entries = 0;
        try{
            entries = Files.lines(new File(ADDRESSBOOK_FILE_NAME).toPath())
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public List<Person> readData() {
        List<Person> addressBookList = new ArrayList<>();
        try {
            Files.lines(new File("addressBook-file.txt").toPath())
                    .map(line -> line.trim())
                    .forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }
}
