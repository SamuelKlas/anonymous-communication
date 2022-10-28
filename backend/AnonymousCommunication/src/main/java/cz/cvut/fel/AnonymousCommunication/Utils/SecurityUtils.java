package cz.cvut.fel.AnonymousCommunication.Utils;

import cz.cvut.fel.AnonymousCommunication.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getName() {
        return getAuthentication().getName();
    }

    public UserDetails getUserDetails() {
        return (User) getAuthentication().getPrincipal();
    }
}
