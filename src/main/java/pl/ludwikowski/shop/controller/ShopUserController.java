package pl.ludwikowski.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ludwikowski.shop.model.ShopUser;
import pl.ludwikowski.shop.service.ShopUserService;

import java.util.Collection;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequestMapping("/user")
public class ShopUserController {

    ShopUserService shopUserService;

    @PostMapping
    @Operation(summary = "Register new user")
    public ShopUser registerUser(@RequestBody ShopUser shopUser) {
        return shopUserService.registerShopUser(shopUser);
    }

    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Ger own user email from Security Context")
    public String getUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        return securityContext.getAuthentication().getName() + " ---> " + authorities.stream().findFirst();
    }

}
