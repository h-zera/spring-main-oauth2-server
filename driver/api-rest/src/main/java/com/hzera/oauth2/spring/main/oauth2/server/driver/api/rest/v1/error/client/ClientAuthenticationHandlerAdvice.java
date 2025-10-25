package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.error.client;

import com.hzera.oauth2.spring.main.oauth2.server.application.exceptions.client.RegisteredClientNotFound;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientAuthenticationHandlerAdvice {
    private final ClientErrorResponseService clientErrorResponseService;

    public void invalidClient(HttpServletResponse response, OAuth2AuthenticationException exception) throws IOException {
            var errorException = new RegisteredClientNotFound(
                    "Client authentication failed for: " + exception.getAuthenticationRequest().getPrincipal()
            );

            clientErrorResponseService.writeClientNotFoundResponse(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    response,
                    errorException,
                    List.of(
                            "OAuth2 Error Code: " + exception.getError().getErrorCode(),
                            "Description: " + exception.getError().getDescription()
                    )
            );
    }

    public  void unauthorizedClient(HttpServletResponse response, OAuth2AuthenticationException exception) {}
}
