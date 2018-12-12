package MK;

import MK.exceptions.MyException;
import MK.repository.CustomerRepository;
import MK.repository.ProducerRepository;
import MK.repository.ProductRepository;
import MK.service.Operations;

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
                    "4. Menu zamówień\n" +
                    "<=0. Wyjście z programu";
            CustomerRepository customerRepository = new CustomerRepository();
            ProductRepository productRepository = new ProductRepository();
            ProducerRepository producerRepository = new ProducerRepository();

            producerRepository.enterDataProducerToDataBase();
            productRepository.enterDataProductToDataBase();
            customerRepository.initialiseData();
            Operations operations = new Operations();

            int option =1, option2 = 1;
            while(option > 0) {
                System.out.println(menu);
                System.out.println("Wybierz interesującą Cię opcje:");
                option  = scanner.nextInt();
                switch(option) {
                    case 1:
                        System.out.println("[KLIENT]Wybierz opcje:\n1.Pokaż listę\n2.Dodaj\n3.Wyszukaj\n4.Zaktualizuj\n5.Usuń");
                        option2  = scanner.nextInt();
                        switch(option2) {
                            case 1:
                                System.out.println(operations.getCustomerList());
                                break;
                            case 2:
                                operations.menuAddCustomer();
                                break;
                            case 3:
                                operations.menuFindCustomerByNameSurname(scanner);
                                break;
                            case 4:
                                operations.menuUpdateCustomer(scanner);
                                break;
                            case 5:
                                operations.menuRemoveCustomer(scanner);
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
                                System.out.println(operations.getProductList());
                                break;
                            case 2:
                                operations.menuAddProduct(scanner);
                                break;
                            case 3:
                                operations.menuFindProduct(scanner);
                                break;
                            case 4:
                                operations.menuUpdateProduct(scanner);
                                break;
                            case 5:
                                operations.menuRemoveProduct(scanner);
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
                                System.out.println(operations.getProducerList());
                                break;
                            case 2:
                                operations.menuAddProducer(scanner);
                                break;
                            case 3:
                                operations.menuFindProducer(scanner);
                                break;
                            case 4:
                                operations.menuUpdateProducer(scanner);
                                break;
                            case 5:
                                operations.menuRemoveProducer(scanner);
                                break;
                            default:
                                System.out.println("Błędna opcja");
                        }
                        break;
                    case 4:
                        System.out.println("[Zamówienia]Wybierz opcje:\n1.Pokaż listę\n2.Dodaj\n3.Wyszukaj\n4.Zaktualizuj\n5.Usuń");
                        option2  = scanner.nextInt();
                        switch(option2) {
                            case 1:
                                System.out.println(operations.getCustomerOrderList());
                                break;
                            case 2:
                                operations.menuAddCustomerOrder(scanner);
                                break;
                            case 3:
                                operations.menuFindCustomerOrder(scanner);
                                break;
                            case 4:
                                operations.menuUpdateCustomerOrder(scanner);
                                break;
                            case 5:
                                operations.menuRemoveCustomerOrder(scanner);
                                break;
                            default:
                                System.out.println("Błędna opcja");
                        }
                        break;
                    default:
                        if(option > 0)
                            System.out.println("Wybrano niepoprawną opcję");
                }
            }
            System.out.println("Zakończenie działania programu");
        } catch (MyException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}
