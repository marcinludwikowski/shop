package pl.ludwikowski.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ludwikowski.shop.model.Cart;
import pl.ludwikowski.shop.model.CartStatus;
import pl.ludwikowski.shop.model.ShopUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByShopUserAndCartStatus(ShopUser shopUser, CartStatus cartStatus);

    List<Cart> findAllByShopUserAndCartStatus(ShopUser shopUser, CartStatus cartStatus);

    List<Cart> findAllByCartStatus(CartStatus cartStatus);

}
