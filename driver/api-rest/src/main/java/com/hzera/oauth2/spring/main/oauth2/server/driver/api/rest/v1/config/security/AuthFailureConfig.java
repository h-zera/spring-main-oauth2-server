package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.config.security;

import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.error.client.ClientAuthenticationHandlerAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

import java.util.LinkedHashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
@RequiredArgsConstructor
public class AuthFailureConfig {
    private final ClientAuthenticationHandlerAdvice clientAuthHandler;

    @Bean
    public AuthenticationEntryPoint delegatingEntryPoint(
            AuthenticationEntryPoint loginEntryPoint,
            AuthenticationEntryPoint jsonEntryPoint
    ) {
        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint > entryPoints = new LinkedHashMap<>();

        entryPoints.put(
                new MediaTypeRequestMatcher(APPLICATION_JSON),
                jsonEntryPoint
        );

        var delegate = new DelegatingAuthenticationEntryPoint(entryPoints);
        delegate.setDefaultEntryPoint(loginEntryPoint);
        return delegate;
    }

    @Bean
    public AuthenticationEntryPoint loginEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/login");
    }

    @Bean
    public AuthenticationEntryPoint jsonEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("application/json");
            response.setStatus(401);
            response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
        };
    }

    @Bean
    public AuthenticationFailureHandler delegatingFailureHandler(
            AuthenticationFailureHandler loginFailureHandler,
            AuthenticationFailureHandler jsonFailureHandler
    ) {
        return (request, response, exception) -> {
            String accept = request.getHeader("Accept");
            var acceptEnum = MediaType.parseMediaType(accept);
            if (acceptEnum.equals(APPLICATION_JSON)) {
                jsonFailureHandler.onAuthenticationFailure(request, response, exception);
            } else {
                loginFailureHandler.onAuthenticationFailure(request, response, exception);
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/login?error");
        };
    }

    @Bean
    public AuthenticationFailureHandler jsonFailureHandler() {
        return (request, response, exception) -> {
            if (exception instanceof OAuth2AuthenticationException oauthException) {
                switch (oauthException.getError().getErrorCode()) {
                    case OAuth2ErrorCodes.INVALID_CLIENT -> clientAuthHandler.invalidClient(response, oauthException);
                    case OAuth2ErrorCodes.UNAUTHORIZED_CLIENT -> clientAuthHandler.unauthorizedClient(response, oauthException);
                }
                return;
            }

            response.setContentType("application/json");
            response.setStatus(401);
            response.getOutputStream().println("{ \"error\": \"" + exception.getAuthenticationRequest().getPrincipal() + "\" }");
        };
    }


}
