package net.barrage.school.java.ecatalog.app.merchant;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.SneakyThrows;
import net.barrage.school.java.ecatalog.model.Merchant;
import net.barrage.school.java.ecatalog.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {
    MerchantRepostiory merchantRepostiory;

    MeterRegistry meterRegistry;

    Map<UUID, Counter> merchantCounters = new HashMap<>();

    private MerchantServiceImpl(
            MerchantRepostiory merchantRepostiory,
            MeterRegistry meterRegistry
    ) {
        this.merchantRepostiory = merchantRepostiory;
        this.meterRegistry = meterRegistry;
        Gauge.builder("ecatalog.merchant.count", this, service -> service.getMerchants().size())
                .description("Merchant count")
                .register(meterRegistry);
    }

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
        var optionalMerchant = merchantRepostiory.findById(merchantId);
        optionalMerchant.ifPresent(this::createOrIncrementMerchantCounter);
        return optionalMerchant;
    }

    @SneakyThrows
    @Override
    public List<Product> getMerchantsProducts(UUID merchantId) {
        var merchant = merchantRepostiory.findById(merchantId).orElseThrow();
        this.createOrIncrementMerchantCounter(merchant);
        return merchant.getProducts().stream().toList();
    }

    private void createOrIncrementMerchantCounter(Merchant merchant) {
        if (merchantCounters.containsKey(merchant.getId())) {
            var counter = merchantCounters.get(merchant.getId());
            counter.increment();
        } else {
            Tags tags = Tags.of(
                    "id", merchant.getId().toString(),
                    "name", merchant.getName());
            Counter counter = Counter.builder("ecatalog.merchants").tags(tags).register(meterRegistry);
            counter.increment(1);
            merchantCounters.put(merchant.getId(), counter);
        }
    }
}
