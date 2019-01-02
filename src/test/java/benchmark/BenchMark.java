package benchmark;

import MK.model.Customer;
import MK.service.CustomerOperations;
import org.openjdk.jmh.annotations.*;

public class BenchMark {

    @Benchmark
    @Fork(warmups = 1, value = 2)
    @Warmup(iterations = 2)
    @BenchmarkMode(Mode.AverageTime)
    public void addCustomerBenchmark() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = Customer.builder().name("name").surname("surname").age(1).build();
        o.addCustomer(testCustomer);

        o.findCustomerByNameSurname("name", "surname");
    }

    @Benchmark
    @Fork(warmups = 1, value = 2)
    @Warmup(iterations = 2)
    @BenchmarkMode(Mode.AverageTime)
    public void editCustomerBenchmark() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = Customer.builder().name("name").surname("surname").age(1).build();
        o.addCustomer(testCustomer);

        Customer customer = o.findCustomerByNameSurname("name", "surname");

        customer.setName("newName");
        customer.setSurname("newSurname");
        customer.setAge(2);

        o.updateCustomer(customer);

        o.findCustomerByNameSurname("newName", "newSurname");
    }

    @Benchmark
    @Fork(warmups = 1, value = 2)
    @Warmup(iterations = 2)
    @BenchmarkMode(Mode.AverageTime)
    public void removeCustomerBenchmark() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = Customer.builder().name("testName").surname("testSurname").age(1).build();
        o.addCustomer(testCustomer);

        Customer dbCustomer = o.findCustomerByNameSurname("testName", "testSurname");

        o.removeCustomer(dbCustomer.getId());

        o.findCustomerByNameSurname("testName", "testSurname");
    }

    @Benchmark
    @Fork(warmups = 1, value = 2)
    @Warmup(iterations = 2)
    @BenchmarkMode(Mode.AverageTime)
    public void macroCustomerBenchmark() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = Customer.builder().name("name").surname("surname").age(1).build();
        o.addCustomer(testCustomer);

        o.findCustomerByNameSurname("name", "surname");

        CustomerOperations o1 = new CustomerOperations();

        Customer testCustomer1 = Customer.builder().name("name").surname("surname").age(1).build();
        o.addCustomer(testCustomer1);

        Customer customer = o1.findCustomerByNameSurname("name", "surname");

        customer.setName("newName");
        customer.setSurname("newSurname");
        customer.setAge(2);

        o1.updateCustomer(customer);

        o1.findCustomerByNameSurname("newName", "newSurname");

        CustomerOperations o2 = new CustomerOperations();

        Customer testCustomer2 = Customer.builder().name("testName").surname("testSurname").age(1).build();
        o2.addCustomer(testCustomer2);

        Customer dbCustomer = o2.findCustomerByNameSurname("testName", "testSurname");

        o2.removeCustomer(dbCustomer.getId());

        o2.findCustomerByNameSurname("testName", "testSurname");
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
