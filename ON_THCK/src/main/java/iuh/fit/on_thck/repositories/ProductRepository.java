package iuh.fit.on_thck.repositories;


import iuh.fit.on_thck.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
