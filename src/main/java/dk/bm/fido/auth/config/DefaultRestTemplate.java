package dk.bm.fido.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DefaultRestTemplate {
    @Bean
    public RestTemplate sslRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }
}
