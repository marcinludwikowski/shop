package pl.ludwikowski.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ludwikowski.shop.model.Cart;
import pl.ludwikowski.shop.model.CartItem;
import pl.ludwikowski.shop.model.Product;
import pl.ludwikowski.shop.repository.CartItemRepository;
import pl.ludwikowski.shop.repository.CartRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemService {

    CartItemRepository cartItemRepository;

    CartRepository cartRepository;

    public CartItem createCartItem(Product product, Double quantity) {
        return cartItemRepository.save(new CartItem(product, quantity, product.getPrice()));
    }

    public void deleteCartItem(Long id) {
        Cart cart = cartRepository.findAll().stream().findFirst().get();
        CartItem cartItemToRemove = cart.getCartItems().stream().filter(cartItem -> cartItem.getId().equals(id)).findFirst().get();
        cart.getCartItems().remove(cartItemToRemove);
        cartRepository.save(cart);
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
        optionalCartItem.ifPresent(cartItem -> cartItemRepository.delete(cartItem));
    }

}
