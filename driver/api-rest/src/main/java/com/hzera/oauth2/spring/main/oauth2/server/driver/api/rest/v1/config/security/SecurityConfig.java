package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.config.security;

import com.hzera.oauth2.spring.main.oauth2.server.application.ports.driver.ClientServicePort;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.mappers.client.RegisteredClientMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            AuthenticationEntryPoint delegatingEntryPoint,
            AuthenticationFailureHandler delegatingFailureHandler
    ) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults())
                        .clientAuthentication(client ->
                                client.errorResponseHandler(delegatingFailureHandler)
                        );
        http
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(delegatingEntryPoint)
                )
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .csrf(CsrfConfigurer::disable)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(
            ClientServicePort clientService,
            RegisteredClientMapper clientMapper
    ) {
        return new RegisteredClientProvider(clientService, clientMapper);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("pepe")
                .password("12345")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .authorizationEndpoint("/v1/oauth2/authorize")
                .tokenEndpoint("/v1/oauth2/token")
                .tokenIntrospectionEndpoint("/v1/oauth2/introspect")
                .tokenRevocationEndpoint("/v1/oauth2/revoke")
                .jwkSetEndpoint("/v1/oauth2/jwks")
                .oidcUserInfoEndpoint("/v1/userinfo")
                .build();
    }
}