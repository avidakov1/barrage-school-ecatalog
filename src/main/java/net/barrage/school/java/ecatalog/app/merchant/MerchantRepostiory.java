package net.barrage.school.java.ecatalog.app.merchant;

import net.barrage.school.java.ecatalog.model.Merchant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepostiory extends CrudRepository<Merchant, UUID> {
    Optional<Merchant> findByName(String name);
}
