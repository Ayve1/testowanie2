package MK.service;

import MK.model.Customer;
import MK.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CustomerOperations {

    private CustomerRepository customerRepository = new CustomerRepository();

    public List<Customer> getCustomerList(){
        return this.customerRepository.findAll();
    }

    public Customer getCustomerInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give name");
        String name = scanner.nextLine();
        System.out.println("Give surname");
        String surname = scanner.nextLine();
        System.out.println("Give age");
        int age = scanner.nextInt();
        return Customer.builder().name(name).surname(surname).age(age).build();
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

    public Customer findCustomerByNameSurname(String name, String surname) {
        return this.customerRepository.findOneByName(name, surname);
    }

    public Customer findCustomer(Long id){
        Optional c = this.customerRepository.findOne(id);
        return (Customer) c.get();
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


}
