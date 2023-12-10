package net.barrage.school.java.ecatalog.app.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.barrage.school.java.ecatalog.app.merchant.MerchantRepostiory;
import net.barrage.school.java.ecatalog.app.product.source.ProductSource;
import net.barrage.school.java.ecatalog.model.Merchant;
import net.barrage.school.java.ecatalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final MerchantRepostiory merchantRepostiory;

    @Autowired
    private final List<ProductSource> productSources;

    @SneakyThrows
    @Override
    @Cacheable(
            value = "list"
    )
    public List<Product> listProducts() {
        var products = new ArrayList<Product>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @SneakyThrows
    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findByName(query);
    }

    @SneakyThrows
    @Override
    public Optional<Product> getProductById(UUID productId) {
        return productRepository.findById(productId);
    }

    @SneakyThrows
    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

    @SneakyThrows
    @Override
    public void updateProduct(UUID productId, Product updatedProduct) {
        var product = productRepository.findById(productId).orElseThrow();
        product.setImage(updatedProduct.getImage())
                .setName(updatedProduct.getName())
                .setDescription(updatedProduct.getDescription())
                .setPrice(product.getPrice());
        productRepository.save(product);
    }

    @SneakyThrows
    @Override
    public void createProduct(Product product, UUID merchantId) {
        Merchant merchant = merchantRepostiory.findById(merchantId).orElseThrow();
        productRepository.save(product.setMerchant(merchant));
    }



    @Override
    @Transactional
    public void loadSourceMerchant(String name) {
        log.info(name);
        for (var ps : productSources) {
            var propertyData = ps.getProperty();
            log.info(propertyData.getName());
            if (!propertyData.getName().equals(name)) continue;

            Merchant merchant;
            var dbMerchant = merchantRepostiory.findByName(name);
            if (dbMerchant.isPresent()) {
                merchant = dbMerchant.get();
                productRepository.deleteAll(merchant.getProducts());
            } else {
                merchant = new Merchant().setName(name);
                merchantRepostiory.save(merchant);
            }

            log.info("{}", merchant);

            var products = ps.getProducts(merchant);
            productRepository.saveAll(products);

            log.info("{}", products);
        }
    }
}
