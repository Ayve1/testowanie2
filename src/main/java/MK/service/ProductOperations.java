package MK.service;

import MK.model.Product;
import MK.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProductOperations {

    private ProductRepository productRepository = new ProductRepository();

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
        Product p = Product.builder().name(name).price(price).producerId(id).build();
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
}
