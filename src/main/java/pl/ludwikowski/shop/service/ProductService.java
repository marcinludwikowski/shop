package pl.ludwikowski.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ludwikowski.shop.model.Product;
import pl.ludwikowski.shop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().filter(product -> !product.isDeleted()).collect(Collectors.toList());
    }


    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = findById(id);

        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            product.setDeleted(true);
            productRepository.save(product);
        }

    }


    public Product addProductQuantity(Long id, Double quantityToAdd) {
        Optional<Product> optionalProduct = findById(id);
        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            product.setQuantity(product.getQuantity() + quantityToAdd);
            return productRepository.save(product);
        }
        return new Product();
    }

    public Product subtractProductQuantity(Product product, Double quantityToSubtract) {
        product.setQuantity(product.getQuantity() - quantityToSubtract);
        return productRepository.save(product);
    }


    public Product changeProductPrice(Long id, BigDecimal newPrice) {
        Optional<Product> optionalProduct = findById(id);
        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            product.setPrice(newPrice);
            return productRepository.save(product);
        }
        return new Product();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
