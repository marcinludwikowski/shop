package pl.ludwikowski.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    ShopUser shopUser;

    @OneToMany
    List<CartItem> cartItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    CartStatus cartStatus = CartStatus.CART;


    public Cart(ShopUser shopUser) {
        this.shopUser = shopUser;
    }
}
