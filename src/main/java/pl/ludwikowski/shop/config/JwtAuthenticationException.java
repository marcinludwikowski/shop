package pl.ludwikowski.shop.config;


import org.springframework.security.core.AuthenticationException;

class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
