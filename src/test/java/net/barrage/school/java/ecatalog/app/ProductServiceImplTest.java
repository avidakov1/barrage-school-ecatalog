package net.barrage.school.java.ecatalog.app;

import net.barrage.school.java.ecatalog.app.product.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("json")
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl impl;

    @Test
    void products_are_not_empty() {
        assertFalse(impl.listProducts().isEmpty(), "Expect listProducts() to return something.");
    }

    @Test
    void search_products_are_not_empty() {
        assertFalse(impl.searchProducts("fake").isEmpty(), "Expect searchProducts() to return something.");
    }

    @Test
    void search_products_should_be_empty() {
        assertTrue(impl.searchProducts("real").isEmpty(), "Expect searchProducts() to be empty");
    }
}