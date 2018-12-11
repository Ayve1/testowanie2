package MK.service;

import MK.exceptions.MyException;
import MK.model.Customer;
import MK.model.CustomerOrder;
import MK.model.Product;
import MK.repository.CustomerRepository;
import MK.repository.ProductRepository;
//import sun.security.krb5.SCDynamicStoreConfig;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Operations {

    private CustomerRepository customerRepository = new CustomerRepository();
    private ProductRepository productRepository = new ProductRepository();
    private CustomerOrder customerOrder = new CustomerOrder();

    private Customer getCustomerInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give name");
        String name = scanner.nextLine();
        System.out.println("Give surname");
        String surname = scanner.nextLine();
        System.out.println("Give age");
        int age = scanner.nextInt();
        return Customer.builder().name(name).surname(surname).age(age).build();
    }

    public boolean addNewCustomer() {
        Customer c = this.getCustomerInformation();
        if(c != null){
            this.customerRepository.saveOrUpdate(c);
            return true;
        }
        return false;
    }


    public void znajdzNajstarszegoKlienta() {
        List<Customer> customers = customerRepository.findAll();
        customers.stream()
                .sorted(Comparator.comparing(s -> s.getAge()))
                .limit(1)
                .forEach(s -> System.out.println(s));
    }

    public double calculateAverageAgeCustomers() {
        List<Customer> customers = customerRepository.findAll();
        DoubleSummaryStatistics doubleSummaryStatistics = customers.stream()
                .collect(Collectors.summarizingDouble(s -> s.getAge()));

        return doubleSummaryStatistics.getAverage();
    }

    public void buyProduct() {

        Scanner scanner = new Scanner(System.in);
        //pobierz Dane od klienta
        Customer c = getCustomerInformation();
        if (!czyZapisaćDoBazy(c)) {
            customerRepository.saveOrUpdate(c);
        }

        Product p = chooseProduct();
        System.out.println("how many items you order");
        int numberOfItems = scanner.nextInt();
        CustomerOrder.builder()
                .date(LocalDate.now())
                .payment(p.getPrice().multiply(new BigDecimal(numberOfItems)))
                .productId(p.getId())
                .customerId(c.getId())
                .build();

    }

    private Product chooseProduct() {
        List<Product> products = productRepository.findAll();
        if (products == null) {
            throw new MyException("Data base have not products", LocalDateTime.now());
        }

        createNumericListPoduct(products);

        Scanner sc = new Scanner(System.in);
        int productId;
        System.out.println("Podaj numer produktu ktory chcesz kupic:");
        productId = Integer.parseInt(sc.nextLine());

        return products.get(productId - 1);
    }

    public void createNumericListPoduct(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i + "." + products.get(i));
        }
    }

    private boolean czyZapisaćDoBazy(Customer c) {
        List<Customer> customers = customerRepository.findAll();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getName().equals(c.getName())
                    && customers.get(i).getSurname().equals(c.getSurname())
                    && customers.get(i).getAge() == c.getAge()
            ) {
                break;
            }
            if (i == customers.size()) {
                return true;
            }
        }
        return false;
    }


}
