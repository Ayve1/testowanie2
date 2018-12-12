package MK;

import MK.model.Customer;
import MK.service.CustomerOperations;
import MK.service.Operations;
import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class CustomerTest {
    @Test
    public void addGetCustomerTest() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = Customer.builder().name("name").surname("surname").age(1).build();
        o.addCustomer(testCustomer);

        Customer customer = o.findCustomerByNameSurname("name", "surname");

        assertEquals(customer.getName(), testCustomer.getName());
        assertEquals(customer.getSurname(), testCustomer.getSurname());
        assertEquals(customer.getAge(), testCustomer.getAge());
    }

    @Test(expected = NullPointerException.class)
    public void addNullCustomerTest() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = null;
        o.addCustomer(testCustomer);
    }

    @Test(expected = NoSuchElementException.class)
    public void findWrongIdCustomerTest() {
        CustomerOperations o = new CustomerOperations();

        o.findCustomer(0L);
    }

    @Test
    public void updateCustomerTest() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = Customer.builder().name("name").surname("surname").age(1).build();
        o.addCustomer(testCustomer);

        Customer customer = o.findCustomerByNameSurname("name", "surname");

        customer.setName("newName");
        customer.setSurname("newSurname");
        customer.setAge(2);

        o.updateCustomer(customer);

        Customer updatedCustomer = o.findCustomerByNameSurname("newName", "newSurname");

        assertEquals(updatedCustomer.getName(), "newName");
        assertEquals(updatedCustomer.getSurname(), "newSurname");
        assertEquals(updatedCustomer.getAge(), 2);
    }

    @Test
    public void removeCustomerTest() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = Customer.builder().name("testName").surname("testSurname").age(1).build();
        o.addCustomer(testCustomer);

        Customer dbCustomer = o.findCustomerByNameSurname("testName", "testSurname");

        o.removeCustomer(dbCustomer.getId());

        Customer dbCustomerAfterRemoval = o.findCustomerByNameSurname("testName", "testSurname");

        assertNull(dbCustomerAfterRemoval);
    }

}
