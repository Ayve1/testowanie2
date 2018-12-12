package MK;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import MK.model.Customer;
import MK.model.Producer;
import MK.model.Product;
import MK.service.Operations;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class AppTest 
{

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void addGetCustomerTest() {
        Operations o = new Operations();

        Customer testCustomer = Customer.builder().name("name").surname("surname").age(1).build();
        o.addCustomer(testCustomer);

        Customer customer = o.findCustomerByNameSurname("name", "surname");

        assertEquals(customer.getName(), testCustomer.getName());
        assertEquals(customer.getSurname(), testCustomer.getSurname());
        assertEquals(customer.getAge(), testCustomer.getAge());
    }

    @Test(expected = NullPointerException.class)
    public void addNullCustomerTest() {
        Operations o = new Operations();

        Customer testCustomer = null;
        o.addCustomer(testCustomer);
    }

    @Test
    public void updateCustomerTest() {
        Operations o = new Operations();

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
        Operations o = new Operations();

        Customer testCustomer = Customer.builder().name("testName").surname("testSurname").age(1).build();
        o.addCustomer(testCustomer);

        Customer dbCustomer = o.findCustomerByNameSurname("testName", "testSurname");

        o.removeCustomer(dbCustomer.getId());

        Customer dbCustomerAfterRemoval = o.findCustomerByNameSurname("testName", "testSurname");

        assertNull(dbCustomerAfterRemoval);
    }

    @Test
    public void addGetProductTest() {
        Operations o = new Operations();

        Product testProduct = Product.builder().name("name").price(new BigDecimal(1)).producerId(1).build();
        Product dbProduct = o.addProduct(testProduct);

        Long id = dbProduct.getId();
        Product productFromDB = o.findProduct(id);

        assertEquals(productFromDB.getName(), testProduct.getName());
        assertEquals(productFromDB.getPrice().stripTrailingZeros(), testProduct.getPrice().stripTrailingZeros());
        assertEquals(productFromDB.getProducer(), testProduct.getProducer());
        assertEquals(productFromDB.getProducerId(), testProduct.getProducerId());
    }

    @Test(expected = NullPointerException.class)
    public void addNullProductTest() {
        Operations o = new Operations();

        Product testProduct = null;
        o.addProduct(testProduct);
    }

    @Test
    public void updateProductTest() {
        Operations o = new Operations();

        Product testProduct = Product.builder().name("name").price(new BigDecimal(1)).producerId(1).build();
        Product dbProduct = o.addProduct(testProduct);

        dbProduct.setName("newName");
        dbProduct.setPrice(new BigDecimal(2));
        dbProduct.setProducerId(2);

        o.updateProduct(dbProduct);

        Long id = dbProduct.getId();
        Product updatedProduct = o.findProduct(id);

        assertEquals(updatedProduct.getName(), "newName");
        assertEquals(updatedProduct.getPrice().stripTrailingZeros(), new BigDecimal(2).stripTrailingZeros());
        assertEquals(updatedProduct.getProducerId(), 2);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeProductTest() {
        Operations o = new Operations();

        Product testProduct = Product.builder().name("testName").price(new BigDecimal(1)).producerId(1).build();
        Product dbProduct = o.addProduct(testProduct);

        Long id = dbProduct.getId();

        o.removeProduct(id);

        Product productAfterRemoval = o.findProduct(id);
    }

    @Test
    public void addGetProducerTest() {
        Operations o = new Operations();

        Producer testProducer = Producer.builder().name("name").build();

        o.addProducer(testProducer);

        o.findProducer(testProducer.getId());

        assertEquals(testProducer.getName(), "name");
    }

    @Test(expected = NullPointerException.class)
    public void addNullProducerTest() {
        Operations o = new Operations();

        Producer testProducer = null;

        o.addProducer(testProducer);
    }

    @Test
    public void updateProducerTest(){
        Operations o = new Operations();

        Producer testProducer = Producer.builder().name("name").build();

        o.addProducer(testProducer);

        Long id = testProducer.getId();

        Producer beforeUpdateProducer = o.findProducer(id);
        assertEquals(beforeUpdateProducer.getName(), "name");

        testProducer.setName("newName");

        o.updateProducer(testProducer);
        Producer afterUpdateProducer = o.findProducer(id);
        assertEquals(afterUpdateProducer.getName(), "newName");
    }

    @Test(expected = NoSuchElementException.class)
    public void removeProducerTest(){
        Operations o = new Operations();

        Producer testProducer = Producer.builder().name("name").build();

        o.addProducer(testProducer);

        Long id = testProducer.getId();

        Producer beforeRemoveProducer = o.findProducer(id);
        assertEquals(beforeRemoveProducer.getName(), "name");

        o.removeProducer(id);

        o.findProducer(id);
    }
}
