package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.mappers.client;

import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.ClientAuthMethodsEnum;
import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.GrantTypeEnum;
import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.RegisteredClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {UUID.class, OffsetDateTime.class, ZoneId.class})
public interface RegisteredClientMapper {

    default RegisteredClient toSecurity(RegisteredClientEntity entity) {
        return RegisteredClient.withId(entity.getId().toString())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .clientName(entity.getName())
                .clientIdIssuedAt(entity.getCreatedAt().toInstant())
                .clientAuthenticationMethods(mapAuthMethods(entity.getAuthMethods()))
                .authorizationGrantTypes(mapGrantTypes(entity.getGrantTypes()))
                .redirectUris(mapToConsumer(entity.getRedirectUris()))
                .postLogoutRedirectUris(mapToConsumer(entity.getPostLogoutUris()))
                .scopes(mapToConsumer(entity.getScopes()))
                .clientSettings(buildClientSettings(entity))
                .tokenSettings(buildTokenSettings(entity))
                .build();
    }

    default Consumer<Set<ClientAuthenticationMethod>> mapAuthMethods(Set<ClientAuthMethodsEnum> authMethods) {
        return methods -> authMethods.stream()
                .map(this::toClientAuthMethod)
                .forEach(methods::add);
    }

    default Consumer<Set<AuthorizationGrantType>> mapGrantTypes(Set<GrantTypeEnum> grantTypes) {
        return types -> grantTypes.stream()
                .map(this::toGrantType)
                .forEach(types::add);
    }

    default Consumer<Set<String>> mapToConsumer(Set<String> values) {
        return set -> {
            if (values != null) {
                set.addAll(values);
            }
        };
    }

    ClientAuthenticationMethod toClientAuthMethod(ClientAuthMethodsEnum method);

    AuthorizationGrantType toGrantType(GrantTypeEnum grantType);

    default ClientSettings buildClientSettings(RegisteredClientEntity entity) {
        return ClientSettings.builder()
                .requireAuthorizationConsent(!entity.isFirstParty())
                .requireProofKey(true)
                .build();
    }

    default TokenSettings buildTokenSettings(RegisteredClientEntity entity) {
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofSeconds(entity.getAccessValidity()))
                .refreshTokenTimeToLive(Duration.ofSeconds(entity.getRefreshValidity()))
                .reuseRefreshTokens(false)
                .build();
    }

    @Mapping(target = "id", expression = "java(UUID.fromString(client.getId()))")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "clientSecret", source = "clientSecret")
    @Mapping(target = "createdAt", expression = "java(OffsetDateTime.ofInstant(client.getClientIdIssuedAt(), ZoneId.systemDefault()))")
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "accessValidity", expression = "java(extractAccessValidity(client))")
    @Mapping(target = "refreshValidity", expression = "java(extractRefreshValidity(client))")
    @Mapping(target = "scopes", source = "scopes")
    @Mapping(target = "postLogoutUris", source = "postLogoutRedirectUris")
    @Mapping(target = "redirectUris", source = "redirectUris")
    @Mapping(target = "grantTypes", expression = "java(mapGrantTypesToEnum(client.getAuthorizationGrantTypes()))")
    @Mapping(target = "authMethods", expression = "java(mapAuthMethodsToEnum(client.getClientAuthenticationMethods()))")
    @Mapping(target = "isFirstParty", expression = "java(extractFirstParty(client))")
    RegisteredClientEntity toEntity(RegisteredClient client);

    default int extractAccessValidity(RegisteredClient client) {
        return (int) client.getTokenSettings().getAccessTokenTimeToLive().getSeconds();
    }

    default int extractRefreshValidity(RegisteredClient client) {
        return (int) client.getTokenSettings().getRefreshTokenTimeToLive().getSeconds();
    }

    default boolean extractFirstParty(RegisteredClient client) {
        return !client.getClientSettings().isRequireAuthorizationConsent();
    }

    default Set<GrantTypeEnum> mapGrantTypesToEnum(Set<AuthorizationGrantType> grantTypes) {
        return grantTypes.stream()
                .map(this::toGrantTypeEnum)
                .collect(Collectors.toSet());
    }

    default Set<ClientAuthMethodsEnum> mapAuthMethodsToEnum(Set<ClientAuthenticationMethod> authMethods) {
        return authMethods.stream()
                .map(this::toClientAuthMethodEnum)
                .collect(Collectors.toSet());
    }

    default GrantTypeEnum toGrantTypeEnum(AuthorizationGrantType grantType) {
        if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(grantType)) return GrantTypeEnum.CLIENT_CREDENTIALS;
        if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(grantType)) return GrantTypeEnum.AUTHORIZATION_CODE;
        if (AuthorizationGrantType.REFRESH_TOKEN.equals(grantType)) return GrantTypeEnum.REFRESH_TOKEN;
        if (AuthorizationGrantType.DEVICE_CODE.equals(grantType)) return GrantTypeEnum.DEVICE_CODE;
        if (AuthorizationGrantType.JWT_BEARER.equals(grantType)) return GrantTypeEnum.JWT_BEARER;
        if (AuthorizationGrantType.TOKEN_EXCHANGE.equals(grantType)) return GrantTypeEnum.TOKEN_EXCHANGE;
        throw new IllegalArgumentException("Unsupported AuthorizationGrantType: " + grantType.getValue());
    }

    default ClientAuthMethodsEnum toClientAuthMethodEnum(ClientAuthenticationMethod method) {
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.equals(method)) return ClientAuthMethodsEnum.CLIENT_SECRET_BASIC;
        if (ClientAuthenticationMethod.CLIENT_SECRET_POST.equals(method)) return ClientAuthMethodsEnum.CLIENT_SECRET_POST;
        if (ClientAuthenticationMethod.CLIENT_SECRET_JWT.equals(method)) return ClientAuthMethodsEnum.CLIENT_SECRET_JWT;
        if (ClientAuthenticationMethod.PRIVATE_KEY_JWT.equals(method)) return ClientAuthMethodsEnum.PRIVATE_KEY_JWT;
        if (ClientAuthenticationMethod.NONE.equals(method)) return ClientAuthMethodsEnum.NONE;
        if (ClientAuthenticationMethod.TLS_CLIENT_AUTH.equals(method)) return ClientAuthMethodsEnum.TLS_CLIENT_AUTH;
        if (ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH.equals(method)) return ClientAuthMethodsEnum.SELF_SIGNED_TLS_CLIENT_AUTH;
        throw new IllegalArgumentException("Unsupported ClientAuthenticationMethod: " + method.getValue());
    }
}