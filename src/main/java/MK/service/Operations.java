package MK.service;

import MK.exceptions.MyException;
import MK.model.Customer;
import MK.model.CustomerOrder;
import MK.model.Producer;
import MK.model.Product;
import MK.repository.CustomerOrderRepository;
import MK.repository.CustomerRepository;
import MK.repository.ProducerRepository;
import MK.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Operations {

    private CustomerRepository customerRepository = new CustomerRepository();
    private ProductRepository productRepository = new ProductRepository();
    private ProducerRepository producerRepository = new ProducerRepository();
    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepository();

    public Customer getCustomerInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give name");
        String name = scanner.nextLine();
        System.out.println("Give surname");
        String surname = scanner.nextLine();
        System.out.println("Give age");
        int age = scanner.nextInt();
        return Customer.builder()
                .name(name)
                .surname(surname)
                .age(age)
                .build();
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

        createNumericListProduct(products);

        Scanner sc = new Scanner(System.in);
        int productId;
        System.out.println("Podaj numer produktu ktory chcesz kupic:");
        productId = Integer.parseInt(sc.nextLine());

        return products.get(productId - 1);
    }

    private void createNumericListProduct(List<Product> products) {
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

    public List<Customer> getCustomerList(){
        return this.customerRepository.findAll();
    }

    public void menuAddCustomer(){
        Customer c = this.getCustomerInformation();
        if (addCustomer(c)) {
            System.out.println("Klient utworzony");
        } else {
            System.out.println("Błąd");
        }
    }

    public boolean addCustomer(Customer c) {
        if(c != null){
            this.customerRepository.saveOrUpdate(c);
            return true;
        } else throw new NullPointerException();
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

    public Customer findCustomer(Long id){
        Optional c = this.customerRepository.findOne(id);
        return (Customer) c.get();
    }

    public Customer findCustomerByNameSurname(String name, String surname) {
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

    public boolean updateCustomer(Customer c){
        return this.customerRepository.update(c);
    }

    public void menuRemoveCustomer(Scanner scanner){
        System.out.println("Podaj id użytkownika, którego chcesz usunąć");
        Long id = (long) scanner.nextInt();
        this.removeCustomer(id);
    }

    public void removeCustomer(Long id) {
        this.customerRepository.delete(id);
    }

    // Produkty

    public List<Product> getProductList(){
        return this.productRepository.findAll();
    }

    public void menuAddProduct(Scanner scanner1){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę");
        String name = scanner.nextLine();
        System.out.println("Podaj cenę");
        BigDecimal price = new BigDecimal(scanner.nextDouble());
        System.out.println("Podaj id producenta");
        int id = scanner.nextInt();
        Product p = Product.builder()
                .name(name)
                .price(price)
                .producerId(id)
                .build();
        System.out.println(addProduct(p));
        System.out.println("Dodano produkt");
    }

    public Product addProduct(Product p){
        if(p == null)
            throw new NullPointerException();
        else
            return this.productRepository.saveOrUpdate(p);
    }

    public void menuFindProduct(Scanner scanner){
        System.out.println("Podaj id produktu");
        Long id = (long) scanner.nextInt();
        Product p = findProduct(id);
        System.out.println(p);
    }

    public Product findProduct(Long id) {
        Optional p = this.productRepository.findOne(id);
        return (Product) p.get();
    }

    public void menuUpdateProduct(Scanner scanner){
        System.out.println("Podaj id produktu");
        Long id = (long) scanner.nextInt();
        Product p = findProduct(id);
        if(p != null) {
            System.out.println(p);
            Scanner newScanner = new Scanner(System.in);
            System.out.println("Podaj nową nazwę");
            String name = newScanner.nextLine();
            System.out.println("Podaj nową cenę");
            BigDecimal price = new BigDecimal(newScanner.nextDouble());
            System.out.println("Podaj inne id producenta");
            int producerId = newScanner.nextInt();
            p.setName(name);
            p.setPrice(price);
            p.setProducerId(producerId);
            updateProduct(p);
            System.out.println("Zaktualizowano");
        } else {
            System.out.println("Błąd");
        }
    }

    public void updateProduct(Product p){
        this.productRepository.update(p);
    }

    public void menuRemoveProduct(Scanner scanner){
        System.out.println("Podaj id produktu");
        Long id = (long) scanner.nextInt();
        removeProduct(id);
    }

    public void removeProduct(Long id){
        this.productRepository.delete(id);
    }
    // Producent

    public List<Producer> getProducerList(){
        return this.producerRepository.findAll();
    }

    public void menuAddProducer(Scanner scanner1){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę");
        String name = scanner.nextLine();
        Producer p = Producer.builder()
                .name(name)
                .build();
        System.out.println(addProducer(p));
        System.out.println("Dodano producenta");
    }

    public Producer addProducer(Producer p){
        if(p == null)
            throw new NullPointerException();
        return this.producerRepository.saveOrUpdate(p);
    }

    public void menuFindProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        Producer p = findProducer(id);
        System.out.println(p);
    }

    public Producer findProducer(Long id) {
        Optional p = this.producerRepository.findOne(id);
        return (Producer) p.get();
    }

    public void menuUpdateProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        Producer p = findProducer(id);
        if(p != null) {
            System.out.println(p);
            Scanner newScanner = new Scanner(System.in);
            System.out.println("Podaj nową nazwę");
            String name = newScanner.nextLine();
            p.setName(name);
            updateProducer(p);
            System.out.println("Zaktualizowano");
        } else {
            System.out.println("Błąd");
        }
    }

    public void updateProducer(Producer p){
        this.producerRepository.update(p);
    }

    public void menuRemoveProducer(Scanner scanner){
        System.out.println("Podaj id producenta");
        Long id = (long) scanner.nextInt();
        removeProducer(id);
    }

    public void removeProducer(Long id){
        this.producerRepository.delete(id);
    }

    // Customer Order

    public List<CustomerOrder> getCustomerOrderList(){
        return this.customerOrderRepository.findAll();
    }

    public void menuAddCustomerOrder(Scanner scanner){
        System.out.println("Podaj id użytkownika kupującego");
        Long id = (long) scanner.nextInt();
        if(findCustomer(id) == null)
            throw new NullPointerException("Brak klienta o takim id");
        System.out.println("Podaj id produktu");
        Long productId = (long) scanner.nextInt();
        Product p = findProduct(productId);
        if(p == null)
            throw new NullPointerException("Brak produktu o takim id");
        System.out.println("Podaj ilość kupowanych przedmiotów");
        int amount = scanner.nextInt();
        CustomerOrder co = CustomerOrder.builder()
                .customerId(id)
                .date(LocalDate.now())
                .numberOfItems(amount).
                payment(new BigDecimal(p.getPrice().doubleValue()*amount))
                .productId(productId)
                .build();
        addCustomerOrder(co);
    }

    public CustomerOrder addCustomerOrder(CustomerOrder co){
        return this.customerOrderRepository.save(co);
    }

    public void menuFindCustomerOrder(Scanner scanner){
        System.out.println("Podaj id zamówienia");
        Long id = (long) scanner.nextInt();
        CustomerOrder co = findCustomerOrder(id);
        if(co == null){
            System.out.println("Nie znaleziono takiego zamówienia");
        } else {
            System.out.println(co);
        }
    }

    public CustomerOrder findCustomerOrder(Long id){
        Optional co = this.customerOrderRepository.findOne(id);
        return (CustomerOrder) co.get();
    }

    public void menuUpdateCustomerOrder(Scanner scanner){
        System.out.println("Podaj id zamówienia");
        Long id = (long) scanner.nextInt();
        CustomerOrder co = findCustomerOrder(id);
        System.out.println(co);
        System.out.println("Podaj nowe id produktu");
        Long productId = (long) scanner.nextInt();
        Product p = findProduct(productId);
        if(p == null)
            throw new NullPointerException("Brak produktu o takim id");
        System.out.println("Podaj nową ilość kupowanych przedmiotów");
        int amount = scanner.nextInt();
        co.setNumberOfItems(amount);
        co.setProductId(productId);
        co.setPayment(new BigDecimal(p.getPrice().doubleValue()*amount));
        updateCustomerOrder(co);
    }

    public void updateCustomerOrder(CustomerOrder co){
        this.customerOrderRepository.update(co);
    }

    public void menuRemoveCustomerOrder(Scanner scanner){
        System.out.println("Podaj id zamówienia które chcesz usunąć");
        Long id = (long) scanner.nextInt();
        removeCustomerOrder(id);
        System.out.println("Usunięto");
    }

    public void removeCustomerOrder(Long id){
        this.customerOrderRepository.delete(id);
    }
}
