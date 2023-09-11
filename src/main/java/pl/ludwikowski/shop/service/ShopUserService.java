package pl.ludwikowski.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ludwikowski.shop.model.Role;
import pl.ludwikowski.shop.model.ShopUser;
import pl.ludwikowski.shop.repository.ShopUserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShopUserService
        implements UserDetailsService {

    ShopUserRepository shopUserRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ShopUser> optionalShopUser = shopUserRepository.findByEmail(username);
        return optionalShopUser.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public ShopUser registerShopUser(ShopUser shopUser) {
        shopUser.setPassword(passwordEncoder.encode(shopUser.getPassword()));
        shopUser.setRole(Role.USER);
        return shopUserRepository.save(shopUser);
    }
}
