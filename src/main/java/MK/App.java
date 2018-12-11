package MK;

import MK.exceptions.MyException;
import MK.model.Customer;
import MK.model.Product;
import MK.repository.CustomerRepository;
import MK.repository.ProducerRepository;
import MK.repository.ProductRepository;
import MK.service.Operations;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Hello world!
 */

public class App {
    public static void main(String[] args) {
        try {
            String menu = "MENU \n" +
                    "0. Pokaż menu\n" +
                    "1. Pokaż listę klientów\n" +
                    "2. Pokaż listę produktów\n" +
                    "3. Pokaż listę producentów\n" +
                    "4. Dodaj klienta\n" +
                    "5. Znajdz najstarszego klienta\n" +
                    "6. Średni wiek klientów\n" +
                    "-X. Wyjście z programu";
            CustomerRepository customerRepository = new CustomerRepository();
            ProductRepository productRepository = new ProductRepository();
            ProducerRepository producerRepository = new ProducerRepository();

            producerRepository.enterDataProducerToDataBase();
            productRepository.enterDataProductToDataBase();
            customerRepository.initialiseData();
            Operations operations = new Operations();
            Scanner scanner = new Scanner(System.in);
            System.out.println(menu);
            int option =1;
            while(option >= 0) {
                System.out.println("Wybierz interesującą Cię opcje:");
                option  = scanner.nextInt();
                switch(option) {
                    case 0:
                        System.out.println(menu);
                        break;
                    case 1:
                        System.out.println(customerRepository.findAll());
                        break;
                    case 2:
                        System.out.println(productRepository.findAll());
                        break;
                    case 3:
                        System.out.println(producerRepository.findAll());
                        break;
                    case 4:
                        if (operations.addNewCustomer()) {
                            System.out.println("Klient utworzony");
                        } else {
                            System.out.println("Błąd");
                        }
                        break;
                    case 5:
                        operations.znajdzNajstarszegoKlienta();
                        break;
                    case 6:
                        System.out.println("Średni wiek: "+operations.calculateAverageAgeCustomers());
                        break;
                    case 7:
                        System.out.println("Kup produkt");
                        break;
                    default:
                        if(option >= 0)
                        System.out.println("Wybrano niepoprawną opcję");

                }
                /*operations.znajdzNajstarszegoKlienta();
                System.out.println("Average age=" + operations.calculateAverageAgeCustomers());
                operations.buyProduct();*/
            }
            System.out.println("Zakończenie działania programu");
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
