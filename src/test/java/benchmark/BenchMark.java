package benchmark;

import MK.model.Customer;
import MK.service.CustomerOperations;
import org.openjdk.jmh.annotations.*;

public class BenchMark {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void productTest() {
        CustomerOperations o = new CustomerOperations();

        Customer testCustomer = Customer.builder().name("name").surname("surname").age(1).build();
        o.addCustomer(testCustomer);

        o.findCustomerByNameSurname("name", "surname");

    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
