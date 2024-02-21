package pl.ludwikowski.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ludwikowski.shop.model.ShopUser;

import java.util.Optional;

@Repository
public interface ShopUserRepository extends JpaRepository<ShopUser, Long> {

    Optional<ShopUser> findByEmail(String email);
}
