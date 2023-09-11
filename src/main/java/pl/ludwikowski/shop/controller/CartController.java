package pl.ludwikowski.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ludwikowski.shop.model.Cart;
import pl.ludwikowski.shop.model.CartStatus;
import pl.ludwikowski.shop.model.Role;
import pl.ludwikowski.shop.model.ShopUser;
import pl.ludwikowski.shop.repository.ShopUserRepository;
import pl.ludwikowski.shop.service.CartItemService;
import pl.ludwikowski.shop.service.CartService;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    CartService cartService;
    CartItemService cartItemService;

    ShopUserRepository shopUserRepository;

    @PostMapping
    @Secured("USER")
    @Operation(summary = "Create or get cart")
    public Cart createOrGetCart() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userEmail = securityContext.getAuthentication().getName();
        ShopUser shopUser = shopUserRepository.findByEmail(userEmail).get();
        return cartService.createOrGetCart(shopUser);
    }

    @PutMapping("/{id}")
    @Secured("USER")
    @Operation(summary = "Add product to cart")
    public Cart addProductToCart(@PathVariable Long id, @RequestParam("productId") Long productId, @RequestParam("quantity") Double quantity) {
        return cartService.addProductToCart(id, productId, quantity);
    }

    @PutMapping("/{id}/{status}")
    @Secured({"USER", "ADMIN"})
    @Operation(summary = "Change cart status")
    public Cart changeCartStatus(@PathVariable("id") Long id, @PathVariable("status") CartStatus status) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userEmail = securityContext.getAuthentication().getName();
        ShopUser shopUser = shopUserRepository.findByEmail(userEmail).get();
        return cartService.changeCartStatus(shopUser, id, status);

    }

    @DeleteMapping("deleteCartItem/{cartItemId}")
    @Secured("USER")
    @Operation(summary = "Delete product from cart")
    public void removeProductFromCart(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

    @GetMapping("/{status}")
    @Secured({"USER", "ADMIN"})
    @Operation(summary = "Get all orders by status")
    public List<Cart> getAllOrderByStatus(@PathVariable CartStatus status) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userEmail = securityContext.getAuthentication().getName();
        ShopUser shopUser = shopUserRepository.findByEmail(userEmail).get();
        Role shopUserRole = shopUser.getRole();
        if (shopUserRole.equals(Role.ADMIN)) {
            return cartService.findAllByStatus(status);
        } else {
            return cartService.findAllByShopUserAndCartStatus(shopUser, status);
        }
    }
}
