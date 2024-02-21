package pl.ludwikowski.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
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
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("ADMIN")
    @Operation(summary = "Add new product")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("ADMIN")
    @Operation(summary = "Delete product")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("addQuantity/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("ADMIN")
    @Operation(summary = "Add product quantity")
    public Product addProductQuantity(@PathVariable Long id, @RequestParam("quantityToAdd") Double quantityToAdd) {
        return productService.addProductQuantity(id, quantityToAdd);
    }

    @PutMapping("changePrice/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Secured("ADMIN")
    @Operation(summary = "Change product price")
    public Product changeProductPrice(@PathVariable Long id, @RequestParam("newPrice") BigDecimal newPrice) {
        return productService.changeProductPrice(id, newPrice);
    }

}
