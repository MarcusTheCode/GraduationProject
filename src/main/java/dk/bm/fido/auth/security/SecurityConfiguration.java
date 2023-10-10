package dk.bm.fido.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/logoutUser")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/home/**")).authenticated()
                                .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                )
                .oauth2Login(oauth -> oauth.loginPage("/login"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(
                                (request, response, authentication) -> response.sendRedirect("/login")
                        ))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}
