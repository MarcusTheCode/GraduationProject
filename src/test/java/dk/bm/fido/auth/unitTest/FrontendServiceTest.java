package dk.bm.fido.auth.unitTest;

import dk.bm.fido.auth.BaseTestSetup;
import dk.bm.fido.auth.services.FrontEndHelper;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Collection;

public class FrontendServiceTest extends BaseTestSetup {

    @Test
    public void setAuthenticationFalseTest() {
        Model model = new ExtendedModelMap();
        Authentication authentication = getMockAuthentication();
        authentication.setAuthenticated(false);
        FrontEndHelper.setAuthenticated(authentication, model);

        assertThat(model.getAttribute("authenticated")).isEqualTo(false);
    }

    @Test
    public void setAuthenticationTrueTest() {
        Model model = new ExtendedModelMap();
        Authentication authentication = getMockAuthentication();
        authentication.setAuthenticated(true);
        FrontEndHelper.setAuthenticated(authentication, model);

        assertThat(model.getAttribute("authenticated")).isEqualTo(true);
        assertThat(model.getAttribute("username")).isNotNull();
    }

    @Test
    public void setAuthenticationNotNullTest() {
        Model model = new ExtendedModelMap();
        FrontEndHelper.setAuthenticated(null, model);

        assertThat(model.getAttribute("authenticated")).isEqualTo(false);
    }

    private Authentication getMockAuthentication() {
        return new Authentication() {

            private boolean authenticated;

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return authenticated;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                this.authenticated = isAuthenticated;
            }

            @Override
            public String getName() {
                return "test";
            }
        };
    }

}
