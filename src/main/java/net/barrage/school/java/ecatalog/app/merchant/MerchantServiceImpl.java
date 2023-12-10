package net.barrage.school.java.ecatalog.app.merchant;

import lombok.SneakyThrows;
import net.barrage.school.java.ecatalog.model.Merchant;
import net.barrage.school.java.ecatalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantRepostiory merchantRepostiory;

    @SneakyThrows
    @Override
    public List<Merchant> getMerchants() {
        var merchants = new ArrayList<Merchant>();
        merchantRepostiory.findAll().forEach(merchants::add);
        return merchants;

    }

    @SneakyThrows
    @Override
    public Optional<Merchant> getMerchantById(UUID merchantId) {
        return merchantRepostiory.findById(merchantId);
    }

    @SneakyThrows
    @Override
    public List<Product> getMerchantsProducts(UUID merchantId) {
        var merchant = merchantRepostiory.findById(merchantId).orElseThrow();
        return merchant.getProducts().stream().toList();
    }
}
