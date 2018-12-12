package MK.service;

import MK.model.CustomerOrder;
import MK.model.Product;
import MK.repository.CustomerOrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CustomerOrderOperations {

    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepository();

    public List<CustomerOrder> getCustomerOrderList(){
        return this.customerOrderRepository.findAll();
    }

    public void menuAddCustomerOrder(Scanner scanner){
        System.out.println("Podaj id użytkownika kupującego");
        Long id = (long) scanner.nextInt();
        if(new CustomerOperations().findCustomer(id) == null)
            throw new NullPointerException("Brak klienta o takim id");
        System.out.println("Podaj id produktu");
        Long productId = (long) scanner.nextInt();
        Product p = new ProductOperations().findProduct(productId);
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
        Product p = new ProductOperations().findProduct(productId);
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
