package dk.bm.fido.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class webSecurityConfigurerAdapter {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                auth.requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/home/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
                )
                .oauth2Login(oauth -> oauth.loginPage("/login"))
                .build();
    }

}
