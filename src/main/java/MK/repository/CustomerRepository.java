package MK.repository;
import MK.exceptions.MyException;
import MK.model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

//import static com.sun.tools.javac.code.Lint.LintCategory.PATH;


public class CustomerRepository  extends AbstractGenericRepository<Customer> implements CustomerInterface {

//    private static final String PATH = "C:/Users/Karol/IdeaProjects/testowanie_projekt2/src/main/java/resources/";


    public void initialiseData() {
        File resources = new File("src/main/java/resources");
        try (FileReader reader = new FileReader(resources.getAbsolutePath()+"/customers.txt");
            Scanner sc = new Scanner(reader)) {
            while(sc.hasNextLine()) {
                String data[] = sc.nextLine().split(";");
                System.out.println(Arrays.toString(data));
                Customer c = Customer.builder()
                        .name(data[0])
                        .surname(data[1])
                        .age(Integer.parseInt(data[2]))
                        .build();
                saveOrUpdate(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("FILE EXCEPTION 3", LocalDateTime.now());
        }
    }

/*    public void initialiseData() {
        String paths[] = {"customers.txt", "customer2.txt"};
        for (int i = 0; i < paths.length; i++) {
            saveOrUpdate(downloadCustomerFromFile(paths[i]));
        }
    }*/

    public Customer findOneByName(String name, String surname) {
        Customer item = null;
        Session session = null;
        Transaction tx;

        try {
            if (name == null || surname == null) {
                throw new NullPointerException("ID IS NULL");
            }

            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            List data = session.createQuery("FROM Customer WHERE name= :name AND surname = :surname")
                    .setParameter("name", name).setParameter("surname", surname)
                    .list();
            tx.commit();
            if(data.size() > 0)
                item = (Customer) data.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("FIND ONE EXCEPTION", LocalDateTime.now());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return item;
    }

/*    public boolean update(Customer c){
        Customer item = null;
        Session session = null;
        Transaction tx;
        try {
            if (c == null) {
                throw new NullPointerException("ID IS NULL");
            }
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            session.update(c);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("FIND ONE EXCEPTION", LocalDateTime.now());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }*/

}
