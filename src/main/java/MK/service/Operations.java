package MK.service;

import MK.exceptions.MyException;
import MK.model.Customer;
import MK.model.CustomerOrder;
import MK.model.Producer;
import MK.model.Product;
import MK.repository.CustomerRepository;
import MK.repository.ProducerRepository;
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
    private ProducerRepository producerRepository = new ProducerRepository();
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

    // Klienci

    public void menuAddCustomer(){
        if (addCustomer()) {
            System.out.println("Klient utworzony");
        } else {
            System.out.println("Błąd");
        }
    }

    private boolean addCustomer() {
        Customer c = this.getCustomerInformation();
        if(c != null){
            this.customerRepository.saveOrUpdate(c);
            return true;
        }
        return false;
    }

    public Customer menuFindCustomerByNameSurname(Scanner scanner){
        System.out.println("Podaj imie");
        String name = scanner.next();
        System.out.println("Podaj nazwisko");
        String surname = scanner.next();
        Customer c = findCustomerByNameSurname(name, surname);
        if(c == null)
            System.out.println("Brak takiego użytkownika");
        else
            System.out.println(c);
        return c;
    }

    private Customer findCustomerByNameSurname(String name, String surname) {
        return this.customerRepository.findOneByName(name, surname);
    }

    public void menuUpdateCustomer(Scanner scanner){
        Customer c = menuFindCustomerByNameSurname(scanner);
        if(c != null){
            System.out.println("Podaj nowe imie");
            String name = scanner.next();
            System.out.println("Podaj nowe nazwisko");
            String surname = scanner.next();
            c.setName(name);
            c.setSurname(surname);
            if(updateCustomer(c)){
                System.out.println("Użytkownik zaktualizowany");
            }
        }
    }

    private boolean updateCustomer(Customer c){
        return this.customerRepository.update(c);
    }

    public void menuRemoveCustomer(Scanner scanner){
        System.out.println("Podaj id użytkownika, którego chcesz usunąć");
        Long id = (long) scanner.nextInt();
        this.removeCustomer(id);
    }

    private void removeCustomer(Long id) {
        this.customerRepository.delete(id);
    }

    // Produkty

    public void menuAddProduct(Scanner scanner){
        System.out.println("Podaj nazwę");
        String name = scanner.nextLine();
        System.out.println("Podaj cenę");
        BigDecimal price = new BigDecimal(scanner.nextDouble());
        System.out.println("Podaj id producenta");
        int id = scanner.nextInt();
        Product p = Product.builder().name(name).price(price).producerId(id).build();
        System.out.println(addProduct(p));
        System.out.println("Dodano produkt");
    }

    private Product addProduct(Product p){
        return this.productRepository.saveOrUpdate(p);
    }

    public void menuFindProduct(Scanner scanner){
        System.out.println("Podaj id produktu");
        Long id = (long) scanner.nextInt();
        Product p = findProduct(id);
        System.out.println(p);
    }

    private Product findProduct(Long id) {
        Optional p = this.productRepository.findOne(id);
        return (Product) p.get();
    }

    public void menuUpdateProduct(Scanner scanner){
        System.out.println("Podaj id produktu");
        Long id = (long) scanner.nextInt();
        Product p = findProduct(id);
        if(p != null) {
            System.out.println(p);
            System.out.println("Podaj nową nazwę");
            String name = scanner.nextLine();
            System.out.println("Podaj nową cenę");
            BigDecimal price = new BigDecimal(scanner.nextDouble());
            System.out.println("Podaj inne id producenta");
            int producerId = scanner.nextInt();
            p.setName(name);
            p.setPrice(price);
            p.setProducerId(producerId);
            updateProduct(p);
            System.out.println("Zaktualizowano");
        } else {
            System.out.println("Błąd");
        }
    }

    private void updateProduct(Product p){
        this.productRepository.update(p);
    }

    public void menuRemoveProduct(Scanner scanner){
        System.out.println("Podaj id produktu");
        Long id = (long) scanner.nextInt();
        removeProduct(id);
    }

    private void removeProduct(Long id){
        this.productRepository.delete(id);
    }
    // Producent

    public void menuAddProducer(Scanner scanner){
        System.out.println("Podaj nazwę");
        String name = scanner.nextLine();
        Producer p = Producer.builder().name(name).build();
        System.out.println(addProducer(p));
        System.out.println("Dodano producenta");
    }

    private Producer addProducer(Producer p){
        return this.producerRepository.saveOrUpdate(p);
    }

    public void menuFindProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        Producer p = findProducer(id);
        System.out.println(p);
    }

    private Producer findProducer(Long id) {
        Optional p = this.producerRepository.findOne(id);
        return (Producer) p.get();
    }

    public void menuUpdateProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        Producer p = findProducer(id);
        if(p != null) {
            System.out.println(p);
            System.out.println("Podaj nową nazwę");
            String name = scanner.nextLine();
            p.setName(name);
            updateProducer(p);
            System.out.println("Zaktualizowano");
        } else {
            System.out.println("Błąd");
        }
    }

    private void updateProducer(Producer p){
        this.producerRepository.update(p);
    }

    public void menuRemoveProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        removeProducer(id);
    }

    private void removeProducer(Long id){
        this.producerRepository.delete(id);
    }

}
