package iuh.fit.on_thck.services;

import iuh.fit.on_thck.models.Product;
import iuh.fit.on_thck.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    public List<Product> findAll(){
        return  repository.findAll();
    }
    public Product findById(int id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
    public Product save(Product product){
        return  repository.save(product);
    }
    public  void delete(int id){
        repository.deleteById(id);
    }


}
