package pl.ludwikowski.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ludwikowski.shop.model.Cart;
import pl.ludwikowski.shop.model.CartItem;
import pl.ludwikowski.shop.model.CartStatus;
import pl.ludwikowski.shop.model.Product;
import pl.ludwikowski.shop.model.Role;
import pl.ludwikowski.shop.model.ShopUser;
import pl.ludwikowski.shop.repository.CartRepository;
import pl.ludwikowski.shop.repository.ShopUserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    ProductService productService;

    CartItemService cartItemService;

    CartRepository cartRepository;

    ShopUserRepository shopUserRepository;

    public Cart createOrGetCart(ShopUser shopUser) {
        Optional<Cart> optionalUserCart = cartRepository.findCartByShopUserAndCartStatus(shopUser, CartStatus.CART);
        if (optionalUserCart.isPresent()) {
            return optionalUserCart.get();
        } else {
            return cartRepository.save(new Cart(shopUserRepository.save(shopUser)));
        }
    }

    public Cart addProductToCart(Long cartId, Long productId, Double quantity) {
        Optional<Cart> optionalCart = findById(cartId);
        Optional<Product> optionalProduct = productService.findById(productId);
        if (optionalCart.isPresent() && optionalProduct.isPresent()) {
            Cart cart = optionalCart.get();
            Product product = optionalProduct.get();
            List<CartItem> cartItems = cart.getCartItems();
            Optional<CartItem> optionalCartItem = cartItems.stream().filter(cartItem -> cartItem.getProduct().equals(product)).findFirst();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                if (product.getQuantity() >= quantity) {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                } else {
                    cartItem.setQuantity(cartItem.getQuantity() + product.getQuantity());
                }
            } else {
                if (product.getQuantity() >= quantity) {
                    CartItem cartItem = cartItemService.createCartItem(product, quantity);
                    productService.subtractProductQuantity(product, quantity);
                    cartItems.add(cartItem);
                } else {
                    CartItem cartItem = cartItemService.createCartItem(product, product.getQuantity());
                    productService.subtractProductQuantity(product, product.getQuantity());
                    cartItems.add(cartItem);
                }
            }
            return cartRepository.save(cart);
        }
        return new Cart();
    }

    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public List<Cart> findAllByStatus(CartStatus status) {
        return cartRepository.findAllByCartStatus(status);
    }

    public Optional<Cart> findCartByShopUserAndCartStatus(ShopUser shopUser, CartStatus status) {
        return cartRepository.findCartByShopUserAndCartStatus(shopUser, status);
    }

    public List<Cart> findAllByShopUserAndCartStatus(ShopUser shopUser, CartStatus status) {
        return cartRepository.findAllByShopUserAndCartStatus(shopUser, status);
    }

    public Cart changeCartStatus(ShopUser shopUser, Long id, CartStatus status) {
        Role userRole = shopUser.getRole();
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent() && userRole.equals(Role.ADMIN)) {
            Cart cart = optionalCart.get();
            cart.setCartStatus(status);
            return cartRepository.save(cart);
        } else if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            if (cart.getShopUser().equals(shopUser)) {
                cart.setCartStatus(status);
                return cartRepository.save(cart);
            }
        }
        return new Cart();
    }
}
