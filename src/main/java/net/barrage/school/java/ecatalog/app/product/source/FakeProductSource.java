package net.barrage.school.java.ecatalog.app.product.source;

import net.barrage.school.java.ecatalog.config.ProductSourceProperties;
import net.barrage.school.java.ecatalog.model.Merchant;
import net.barrage.school.java.ecatalog.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FakeProductSource implements ProductSource {

    @Component
    public static class Factory implements ProductSource.Factory {
        @Override
        public Set<String> getSupportedFormats() {
            return Set.of("fake");
        }

        @Override
        public ProductSource create(ProductSourceProperties.SourceProperty psp) {
            return new FakeProductSource();
        }
    }

    @Override
    public List<Product> getProducts(Merchant merchant) {
        return List.of(new Product()
                .setId(UUID.randomUUID())
                .setName("fake-name")
                .setMerchant(merchant));
    }

    @Override
    public ProductSourceProperties.SourceProperty getProperty() {
        var psp = new ProductSourceProperties.SourceProperty();
        psp.setName("fake");
        psp.setFormat("fake");
        psp.setUrl("");
        return psp;
    }
}
