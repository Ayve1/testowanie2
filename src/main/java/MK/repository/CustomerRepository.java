package MK.repository;
import MK.exceptions.MyException;
import MK.model.Customer;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

//import static com.sun.tools.javac.code.Lint.LintCategory.PATH;


public class CustomerRepository  extends AbstractGenericRepository<Customer> implements CustomerInterface {

//    private static final String PATH = "C:/Users/Karol/IdeaProjects/testowanie_projekt2/src/main/java/resources/";


    public static Customer downloadCustomerFromFile(String path) {
        File resources = new File("src/main/java/resources");
        try (FileReader reader = new FileReader(resources.getAbsolutePath()+'/'+path);
            Scanner sc = new Scanner(reader)) {
            String data[] = sc.nextLine().split(";");
            System.out.println(Arrays.toString(data));
            /*System.out.println(data[0]);
            System.out.println(data[1]);*/
            return Customer.builder()
                    .name(data[0])
                    .surname(data[1])
                    .age(Integer.parseInt(data[2]))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("FILE EXCEPTION 3", LocalDateTime.now());
        }
    }

    public void initialiseData() {
        String paths[] = {"customer1.txt", "customer2.txt"};
        for (int i = 0; i < paths.length; i++) {
            saveOrUpdate(downloadCustomerFromFile(paths[i]));
        }
    }

}
