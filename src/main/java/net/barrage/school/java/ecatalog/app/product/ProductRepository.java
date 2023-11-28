package net.barrage.school.java.ecatalog.app.product;

import net.barrage.school.java.ecatalog.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
    // query fails
    // @Query("SELECT p FROM product p WHERE p.name ILIKE CONCAT(%,:name,%) OR p.description ILIKE CONCAT(%,:name,%)")
    List<Product> findByName(@Param("name") String name);
}