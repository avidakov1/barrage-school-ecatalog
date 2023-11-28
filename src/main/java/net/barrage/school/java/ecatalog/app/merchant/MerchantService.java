package net.barrage.school.java.ecatalog.app.merchant;

import net.barrage.school.java.ecatalog.model.Merchant;
import net.barrage.school.java.ecatalog.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MerchantService {
    /**
     * Get merchants
     */
    List<Merchant> getMerchants();

    /**
     * Get merchant by id
     */
    Optional<Merchant> getMerchantById(UUID merchantId);

    /**
     * Get merchants products
     */
    List<Product> getMerchantsProducts(UUID merchantId);
}
