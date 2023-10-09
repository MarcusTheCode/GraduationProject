package dk.bm.fido.auth.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
public class OauthConfig {
    @Autowired private Environment env;

    @Value("${server.ssl.trust-store}")
    private String trustStoreLocation;
    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;
    @Value("${server.ssl.trust-store-type}")
    private String trustStoreType;

    @Bean
    public OAuth2AuthorizedClientManager authorizeClientManager(
            ClientRegistrationRepository clients,
            OAuth2AuthorizedClientRepository authorizedClients) {

        DefaultOAuth2AuthorizedClientManager manager =
                new DefaultOAuth2AuthorizedClientManager(clients, authorizedClients);

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();
        manager.setAuthorizedClientProvider(authorizedClientProvider);
        return manager;
    }

    @Bean
    public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth2Client.setDefaultClientRegistrationId("wso2");
        return WebClient.builder()
                .filter(oauth2Client)
                .build();
    }

    @PostConstruct
    private void configureSSL() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        File trusdStoreFile = new File(trustStoreLocation);

        KeyStore ks = KeyStore.getInstance(trusdStoreFile, trustStorePassword.toCharArray());

        //System.setProperty("javax.net.debug", "all");
        System.setProperty("javax.net.ssl.trustStore", trusdStoreFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
    }

}