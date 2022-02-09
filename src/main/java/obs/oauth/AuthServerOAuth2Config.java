package obs.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import obs.messages.MessageConstants;
import obs.service.CustomUserDetailsService;



@Configuration
@EnableAuthorizationServer
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

	private static final String RESOURCE_ID = "restservice";
	
	@Autowired
	ServletContext ctx;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private TokenStore tokenStore = new InMemoryTokenStore();
    
    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	clients
		.inMemory()  
		.withClient(MessageConstants.OAUTHTC)
		.authorizedGrantTypes("password","refresh_token")
		.authorities("USER")
		.scopes("read","write")
		.resourceIds(RESOURCE_ID)			
		.secret("$2a$11$NHh86ZTyFzt9aGG9.um36uXX8aipRBV.gAGNoNztPyOoSwSAS/bCK")
		.accessTokenValiditySeconds(5000000);
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(this.tokenStore).authenticationManager(authenticationManager).userDetailsService(userDetailsService)
		.tokenEnhancer(tokenEnhancer());
    }
    
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
	public class CustomTokenEnhancer implements TokenEnhancer {
		
		@Override
		public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
			User user = (User) authentication.getPrincipal();
			final Map<String, Object> additionalInfo = new HashMap<>();

			List<String> tokenValues = new ArrayList<>();
			
			Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(MessageConstants.OAUTHTC); 
			if (tokens!=null){
				for (OAuth2AccessToken token:tokens){
					tokenValues.add(token.getValue());
				}
			}			
			obs.domain.User us = userDetailsService.viewProfile(user.getUsername());
			additionalInfo.put("User_id", us.getUserId());
			additionalInfo.put("User_type", us.getUserType());
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			us.setAccessToken(accessToken.getValue());
			
			ctx.setAttribute("LOGGED_USER", us);   
			
			return accessToken;
		}
		
	}
}
