package net.barrage.school.java.ecatalog.app.product;

import net.barrage.school.java.ecatalog.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    /**
     * List all available products
     */
    List<Product> listProducts();

    /**
     * List all available products
     */
    List<Product> searchProducts(String query);

    /**
     * Get product by id
     */
    Optional<Product> getProductById(UUID productId);

    /**
     * Delete product with id
     */
    void deleteProduct(UUID productId);

    /**
     * Update product by id
     */
    void updateProduct(UUID productId, Product updatedProduct);

    /**
     * Create product
     */
    void createProduct(Product product, UUID merchantId);

    /**
     * Load merchant from source
     */
    void loadSourceMerchant(String name);
}
