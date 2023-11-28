package net.barrage.school.java.ecatalog.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.barrage.school.java.ecatalog.app.merchant.MerchantService;
import net.barrage.school.java.ecatalog.model.Merchant;
import net.barrage.school.java.ecatalog.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/e-catalog/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping
    public List<Merchant> getMerchants() {
        return merchantService.getMerchants();
    }

    @GetMapping("/{merchantId}")
    public Optional<Merchant> getMerchant(@PathVariable("merchantId") UUID merchantId) {
        return merchantService.getMerchantById(merchantId);
    }

    @GetMapping("/{merchantId}/products")
    public List<Product> getMerchantsProducts(@PathVariable("merchantId") UUID merchantId) {
        return merchantService.getMerchantsProducts(merchantId);
    }


}
