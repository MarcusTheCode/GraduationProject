package dk.bm.fido.auth;

import org.apache.hc.client5.http.auth.BasicUserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.security.Principal;

public class SecurityContextFactory implements WithSecurityContextFactory<WithTestUser> {
    @Value("${test.user.name:}")
    private String username;

    @Value("${test.user.password:}")
    private String password;

    @Override
    public SecurityContext createSecurityContext(WithTestUser user) {
        System.out.println(username);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Principal principal = new BasicUserPrincipal(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, null);
        context.setAuthentication(auth);
        return context;
    }
}