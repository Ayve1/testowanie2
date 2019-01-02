package MK.repository;

import MK.model.Producer;
import MK.model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductRepository extends AbstractGenericRepository<Product> implements ProductInterface {

    private List<Product> importProductFromFile(String filename) {

        InputStream file = null;
        file = getClass().getResourceAsStream(filename);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        List<Product> products = new ArrayList<>();
        while (sc.hasNextLine()) {
            String data[] = sc.nextLine().split(",");
            Product product = Product.builder()
                    .name(data[0])
                    .price(new BigDecimal(data[1]))
                    .producerId(Integer.parseInt(data[2]))
                    .build();
            products.add(product);
        }
        return products;
    }

    public void enterDataProductToDataBase() {
        List<Product> products = importProductFromFile("/products.txt");
        for (int i = 0; i < products.size(); i++) {
            saveOrUpdate(products.get(i));
        }
    }

}
