package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {

    final Product product1 = new Product(0L, "hairdryer", 3000);
    final Product product2 = new Product(1L, "vacuume cleaner", 5000);
    final Product product3 = new Product(2L, "microwave", 4000);
    final Product product4 = new Product(3L, "tv", 15000);
    final Product product5 = new Product(4L, "refrigerator", 30000);

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS =
            new ArrayList<>(Arrays.asList(product1, product2, product3, product4, product5));


    final Product EXPECTED_FIND_BY_ID_PRODUCT = product2;

    final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "refrigerator", 28000);

    final Product NEW_PRODUCT = new Product(5L, "computer", 150000);


    private ProductsRepository productsRepository;

    @BeforeEach
    void setUp() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        productsRepository = new ProductsRepositoryJdbcImpl(db);
    }

    @Test
    void testFindAll() {
        List<Product> actualProducts = productsRepository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS.size(), actualProducts.size());
        assertEquals(actualProducts, EXPECTED_FIND_ALL_PRODUCTS);
    }

    @Test
    void testFindById() {
        Optional<Product> actualProduct = productsRepository.findById(EXPECTED_FIND_BY_ID_PRODUCT.getId());
        assertTrue(actualProduct.isPresent());
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, actualProduct.get());
    }

    @Test
    void testUpdate() {
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);

        Optional<Product> updatedProduct = productsRepository.findById(EXPECTED_UPDATED_PRODUCT.getId());
        assertTrue(updatedProduct.isPresent());
        assertEquals(EXPECTED_UPDATED_PRODUCT, updatedProduct.get());
    }

    @Test
    void testSave() {
        productsRepository.save(NEW_PRODUCT);

        Optional<Product> savedProduct = productsRepository.findById(NEW_PRODUCT.getId());
        assertTrue(savedProduct.isPresent());
        assertEquals(NEW_PRODUCT, savedProduct.get());
    }

    @Test
    void testDelete() {
        productsRepository.delete(EXPECTED_FIND_BY_ID_PRODUCT.getId());

        Optional<Product> deletedProduct = productsRepository.findById(EXPECTED_FIND_BY_ID_PRODUCT.getId());
        assertFalse(deletedProduct.isPresent());
    }
}
