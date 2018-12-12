package MK;

import MK.exceptions.MyException;
import MK.model.Customer;
import MK.model.Producer;
import MK.model.Product;
import MK.repository.CustomerRepository;
import MK.repository.ProducerRepository;
import MK.repository.ProductRepository;
import MK.service.CustomerOperations;
import MK.service.Operations;
import MK.service.ProducerOperations;
import MK.service.ProductOperations;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String menu = "MENU \n" +
                    "1. Menu klientów\n" +
                    "2. Menu produktów\n" +
                    "3. Menu producentów\n" +
                    "<=0. Wyjście z programu";
            CustomerRepository customerRepository = new CustomerRepository();
            ProductRepository productRepository = new ProductRepository();
            ProducerRepository producerRepository = new ProducerRepository();

            producerRepository.enterDataProducerToDataBase();
            productRepository.enterDataProductToDataBase();
            customerRepository.initialiseData();

            Operations operations = new Operations();
            CustomerOperations customerOperations = new CustomerOperations();
            ProductOperations productOperations = new ProductOperations();
            ProducerOperations producerOperations = new ProducerOperations();

            int option = 1, option2;
            while(option > 0) {
                System.out.println(menu);
                System.out.println("Wybierz interesującą Cię opcje:");
                option  = scanner.nextInt();
                switch(option) {
                    case 1:
                        System.out.println("[KLIENT]Wybierz opcje:\n1.Pokaż listę\n2.Dodaj\n3.Wyszukaj\n4.Zaktualizuj\n5.Usuń");
                        option2 = scanner.nextInt();
                        switch(option2) {
                            case 1:
                                List<Customer> customerList = customerRepository.findAll();

                                for(Customer customer : customerList)
                                    System.out.println(customer);
                                break;
                            case 2:
                                customerOperations.menuAddCustomer();
                                break;
                            case 3:
                                customerOperations.menuFindCustomerByNameSurname(scanner);
                                break;
                            case 4:
                                customerOperations.menuUpdateCustomer(scanner);
                                break;
                            case 5:
                                customerOperations.menuRemoveCustomer(scanner);
                                break;
                            default:
                                System.out.println("Błędna opcja");
                        }
                        break;
                    case 2:
                        System.out.println("[PRODUKTY]Wybierz opcje:\n1.Pokaż listę\n2.Dodaj\n3.Wyszukaj\n4.Zaktualizuj\n5.Usuń");
                        option2  = scanner.nextInt();
                        switch(option2) {
                            case 1:
                                List<Product> productList = productRepository.findAll();

                                for(Product product : productList)
                                    System.out.println(product);
                                break;
                            case 2:
                                productOperations.menuAddProduct(scanner);
                                break;
                            case 3:
                                productOperations.menuFindProduct(scanner);
                                break;
                            case 4:
                                productOperations.menuUpdateProduct(scanner);
                                break;
                            case 5:
                                productOperations.menuRemoveProduct(scanner);
                                break;
                            default:
                                System.out.println("Błędna opcja");
                        }
                        break;
                    case 3:
                        System.out.println("[PRODUCENCI]Wybierz opcje:\n1.Pokaż listę\n2.Dodaj\n3.Wyszukaj\n4.Zaktualizuj\n5.Usuń");
                        option2  = scanner.nextInt();
                        switch(option2) {
                            case 1:
                                List<Producer> producerList = producerRepository.findAll();

                                for(Producer producer : producerList)
                                    System.out.println(producer);

                                break;
                            case 2:
                                producerOperations.menuAddProducer(scanner);
                                break;
                            case 3:
                                producerOperations.menuFindProducer(scanner);
                                break;
                            case 4:
                                producerOperations.menuUpdateProducer(scanner);
                                break;
                            case 5:
                                producerOperations.menuRemoveProducer(scanner);
                                break;
                            default:
                                System.out.println("Błędna opcja");
                        }
                        break;
                    default:
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
        scanner.close();
    }
}
