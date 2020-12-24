package engine.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class AccountSecurity {

    public boolean hasUserId(Authentication authentication, int userId) {
        return authentication.getPrincipal().equals(userId);
    }
}