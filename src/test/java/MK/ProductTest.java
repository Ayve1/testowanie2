package MK;

import static org.junit.Assert.assertEquals;
import MK.model.Product;
import MK.service.ProductOperations;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class ProductTest {
    @Test
    public void addGetProductTest() {
        ProductOperations o = new ProductOperations();

        Product testProduct = Product.builder().name("name").price(new BigDecimal(1)).producerId(1).build();
        Product dbProduct = o.addProduct(testProduct);

        Long id = dbProduct.getId();
        Product productFromDB = o.findProduct(id);

        assertEquals(productFromDB.getName(), testProduct.getName());
        assertEquals(productFromDB.getPrice().stripTrailingZeros(), testProduct.getPrice().stripTrailingZeros());
        assertEquals(productFromDB.getProducer(), testProduct.getProducer());
        assertEquals(productFromDB.getProducerId(), testProduct.getProducerId());
    }

    @Test(expected = NullPointerException.class)
    public void addNullProductTest() {
        ProductOperations o = new ProductOperations();

        Product testProduct = null;
        o.addProduct(testProduct);
    }

    @Test(expected = NoSuchElementException.class)
    public void findWrongIdProductTest() {
        ProductOperations o = new ProductOperations();

        o.findProduct(0L);
    }

    @Test
    public void updateProductTest() {
        ProductOperations o = new ProductOperations();

        Product testProduct = Product.builder().name("name").price(new BigDecimal(1)).producerId(1).build();
        Product dbProduct = o.addProduct(testProduct);

        dbProduct.setName("newName");
        dbProduct.setPrice(new BigDecimal(2));
        dbProduct.setProducerId(2);

        o.updateProduct(dbProduct);

        Long id = dbProduct.getId();
        Product updatedProduct = o.findProduct(id);

        assertEquals(updatedProduct.getName(), "newName");
        assertEquals(updatedProduct.getPrice().stripTrailingZeros(), new BigDecimal(2).stripTrailingZeros());
        assertEquals(updatedProduct.getProducerId(), 2);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeProductTest() {
        ProductOperations o = new ProductOperations();

        Product testProduct = Product.builder().name("testName").price(new BigDecimal(1)).producerId(1).build();
        Product dbProduct = o.addProduct(testProduct);

        Long id = dbProduct.getId();

        o.removeProduct(id);

        o.findProduct(id);
    }
}
