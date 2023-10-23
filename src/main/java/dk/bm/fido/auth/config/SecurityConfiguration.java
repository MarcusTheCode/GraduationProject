package dk.bm.fido.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${idc.idp.endpoint:https://localhost:9443}") private String idcIdpEndpoint;
    @Value("${idc.api.endpoint:http://localhost:8080}") private String idcApiEndpoint;

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication == null)
            return;

        OAuth2AuthenticationToken oauth2Auth = (OAuth2AuthenticationToken) authentication;
        String idToken = ((DefaultOidcUser) oauth2Auth.getPrincipal()).getIdToken().getTokenValue();

        try {
            // Manual logout
            response.sendRedirect(idcIdpEndpoint + "/oidc/logout?id_token_hint=" + idToken + "&post_logout_redirect_uri=" + idcApiEndpoint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/fidoDevices/**")).authenticated()
                                .requestMatchers(new AntPathRequestMatcher("/CSS/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/JS/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/favicon.ico")).permitAll()
                )
                .oauth2Login(oauth -> oauth.loginPage("/login"))
                //.formLogin(login -> login.loginPage("/login"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .addLogoutHandler(this::logout))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}
