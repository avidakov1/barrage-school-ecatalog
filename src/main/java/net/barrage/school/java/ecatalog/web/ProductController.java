package net.barrage.school.java.ecatalog.web;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.barrage.school.java.ecatalog.app.product.ProductService;
import net.barrage.school.java.ecatalog.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/e-catalog/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public List<Product> listProducts() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("user = {}", authentication);
        return productService.listProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam("q") String query
    ) {
        var sec = SecurityContextHolder.getContext();
        return productService.searchProducts(query);
    }

    @GetMapping("/{productId}")
    public Optional<Product> getProductById(@PathVariable("productId") UUID productId) {
        return productService.getProductById(productId);
    }

    @SneakyThrows
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") UUID productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (UnsupportedOperationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("productId") UUID productId,
            @RequestBody Product updatedProduct
    ) {
        try {
            productService.updateProduct(productId, updatedProduct);
            return ResponseEntity.ok("Product updated successfully");
        } catch (UnsupportedOperationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestParam("product") Product product, @RequestParam("merchantId") UUID merchantId) {
            productService.createProduct(product, merchantId);
            return ResponseEntity.ok("Product crated.");
    }

    @SneakyThrows
    @PostMapping("/sync/{merchantName}")
    public  ResponseEntity<String> loadSourceMerchant(@PathVariable("merchantName") String merchantName) {
        productService.loadSourceMerchant(merchantName);
        return ResponseEntity.ok("Merchant synced");
    }
}
