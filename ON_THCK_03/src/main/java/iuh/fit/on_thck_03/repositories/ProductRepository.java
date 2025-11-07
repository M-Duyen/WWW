package iuh.fit.on_thck_03.repositories;

import iuh.fit.on_thck_03.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
