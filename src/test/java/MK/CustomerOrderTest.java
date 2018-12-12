package MK;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import MK.exceptions.MyException;
import MK.model.CustomerOrder;
import MK.service.CustomerOrderOperations;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class CustomerOrderTest
{

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void addGetCustomerOrderTest() {
        CustomerOrderOperations o = new CustomerOrderOperations();

        CustomerOrder testCustomerOrder = CustomerOrder.builder().customerId(1).date(LocalDate.now()).numberOfItems(3).payment(new BigDecimal(5)).productId(1).build();
        CustomerOrder dbCustomerOrder = o.addCustomerOrder(testCustomerOrder);

        Long id = dbCustomerOrder.getId();
        CustomerOrder customerOrderFromDB = o.findCustomerOrder(id);

        assertEquals(customerOrderFromDB.getCustomerId(), testCustomerOrder.getCustomerId());
        assertEquals(customerOrderFromDB.getDate(), testCustomerOrder.getDate());
        assertEquals(customerOrderFromDB.getPayment().stripTrailingZeros(), testCustomerOrder.getPayment().stripTrailingZeros());
        assertEquals(customerOrderFromDB.getNumberOfItems(), testCustomerOrder.getNumberOfItems());
        assertEquals(customerOrderFromDB.getProductId(), testCustomerOrder.getProductId());
    }

    @Test(expected = MyException.class)
    public void addNullCustomerOrderTest() {
        CustomerOrderOperations o = new CustomerOrderOperations();

        CustomerOrder testCustomerOrder = null;

        o.addCustomerOrder(testCustomerOrder);
    }

    @Test(expected = NoSuchElementException.class)
    public void findWrongIdCustomerOrderTest() {
        CustomerOrderOperations o = new CustomerOrderOperations();

        o.findCustomerOrder(0L);
    }

    @Test
    public void updateCustomerOrderTest(){
        CustomerOrderOperations o = new CustomerOrderOperations();

        CustomerOrder testCustomerOrder = CustomerOrder.builder().customerId(1).date(LocalDate.now()).numberOfItems(1).payment(new BigDecimal(4)).productId(2).build();

        o.addCustomerOrder(testCustomerOrder);

        Long id = testCustomerOrder.getId();

        CustomerOrder beforeUpdateCustomerOrder = o.findCustomerOrder(id);
        assertEquals(beforeUpdateCustomerOrder.getNumberOfItems(), 1);
        assertEquals(beforeUpdateCustomerOrder.getPayment().stripTrailingZeros(), new BigDecimal(4));

        testCustomerOrder.setNumberOfItems(2);
        testCustomerOrder.setPayment(new BigDecimal(8));

        o.updateCustomerOrder(testCustomerOrder);
        CustomerOrder afterUpdateCustomerOrder = o.findCustomerOrder(id);
        assertEquals(afterUpdateCustomerOrder.getNumberOfItems(), 2);
        assertEquals(afterUpdateCustomerOrder.getPayment().stripTrailingZeros(), new BigDecimal(8));
    }

    @Test(expected = NoSuchElementException.class)
    public void removeCustomerOrderTest(){
        CustomerOrderOperations o = new CustomerOrderOperations();

        CustomerOrder testCustomerOrder = CustomerOrder.builder().customerId(1).date(LocalDate.now()).numberOfItems(1).payment(new BigDecimal(5)).productId(1).build();

        o.addCustomerOrder(testCustomerOrder);

        Long id = testCustomerOrder.getId();

        CustomerOrder beforeRemoveCustomerOrder = o.findCustomerOrder(id);
        assertEquals(beforeRemoveCustomerOrder.getNumberOfItems(), 1);
        assertEquals(beforeRemoveCustomerOrder.getPayment().stripTrailingZeros(), new BigDecimal(5));

        o.removeCustomerOrder(id);

        o.findCustomerOrder(id);
    }

}
