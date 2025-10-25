package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.config.jwk;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class JwkSourceConfig {
    @Value("${JKS_PATH}")
    private String jksPath;

    @Value("${JKS_ALIAS}")
    private String jksAlias;

    @Value("${JKS_STOREPASS}")
    private String storePass;

    @Value("${JKS_KEYPASS}")
    private String keyPass;

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            ClassPathResource resource = new ClassPathResource(jksPath);
            keyStore.load(resource.getInputStream(), storePass.toCharArray());

            RSAPrivateKey privateKey = (RSAPrivateKey) keyStore.getKey(jksAlias, keyPass.toCharArray());
            RSAPublicKey publicKey = (RSAPublicKey) keyStore.getCertificate(jksAlias).getPublicKey();

            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();

            JWKSet jwkSet = new JWKSet(rsaKey);

            return ((jwkSelector, securityContext) ->  jwkSelector.select(jwkSet));
        } catch (Exception e) {
            throw new IllegalStateException("Error al cargar el JKS", e);
        }
    }
}
