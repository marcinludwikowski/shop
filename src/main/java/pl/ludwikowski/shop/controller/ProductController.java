package pl.ludwikowski.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ludwikowski.shop.model.Product;
import pl.ludwikowski.shop.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @Secured("ADMIN")
    @Operation(summary = "Add new product")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    @Operation(summary = "Delete product")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("addQuantity/{id}")
    @Secured("ADMIN")
    @Operation(summary = "Add product quantity")
    public Product addProductQuantity(@PathVariable Long id, @RequestParam("quantityToAdd") Double quantityToAdd) {
        return productService.addProductQuantity(id, quantityToAdd);
    }

    @PutMapping("changePrice/{id}")
    @Secured("ADMIN")
    @Operation(summary = "Change product price")
    public Product changeProductPrice(@PathVariable Long id, @RequestParam("newPrice") BigDecimal newPrice) {
        return productService.changeProductPrice(id, newPrice);
    }

}
