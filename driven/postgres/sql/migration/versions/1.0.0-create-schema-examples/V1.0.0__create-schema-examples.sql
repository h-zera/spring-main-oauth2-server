CREATE DATABASE main_oauth2_db;

CREATE TYPE user_role AS ENUM (
    'USER',
    'ADMIN',
    'SUPPORT'
);

CREATE TYPE grant_type AS ENUM (
    'authorization_code',
    'client_credentials',
    'refresh_token',
    'device_code',
    'jwt_bearer',
    'token_exchange'
);

CREATE TABLE registered_client (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id VARCHAR(255) UNIQUE NOT NULL,
    client_secret_hash VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    access_validity INTEGER NOT NULL,
    refresh_validity INTEGER NOT NULL,
    scopes TEXT[] NOT NULL,
    post_logout_uris TEXT[],
    redirect_uris TEXT[],
    grant_types grant_type[],
    is_first_party BOOLEAN DEFAULT FALSE
);

CREATE TABLE registered_client_application (
    client_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    logo_url TEXT,
    policy_url TEXT,
    revocation_webhook_uri TEXT NOT NULL,
    CONSTRAINT fk_client_application_client
        FOREIGN KEY(client_id)
            REFERENCES registered_client(id)
            ON DELETE CASCADE
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100),
    last_name VARCHAR(100),
    username VARCHAR(100),
    unique_username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    role user_role DEFAULT 'USER',
    password_hash VARCHAR(255),
    email_confirmed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_sign_in_at TIMESTAMP WITH TIME ZONE,
    banned_until TIMESTAMP WITH TIME ZONE,
    is_sso_user BOOLEAN DEFAULT FALSE
);

CREATE TABLE oauth2_consent (
    client_id UUID NOT NULL,
    user_id UUID NOT NULL,
    granted_scopes TEXT[],
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (client_id, user_id),
    CONSTRAINT fk_consent_client
        FOREIGN KEY(client_id)
            REFERENCES registered_client(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_consent_user
        FOREIGN KEY(user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);

CREATE TABLE user_phone (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    phone_number VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_phone_user
        FOREIGN KEY(user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);

CREATE TABLE sso_provider (
    id SERIAL PRIMARY KEY,
    client_id VARCHAR(255) UNIQUE NOT NULL,
    client_secret_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    scopes TEXT[],
    auth_url TEXT NOT NULL,
    token_url TEXT NOT NULL,
    user_info_url TEXT NOT NULL
);

CREATE TABLE user_identity (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    sso_id INTEGER NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_sign_in_at TIMESTAMP WITH TIME ZONE,
    identity_data JSONB,
    CONSTRAINT fk_user_identity_user
        FOREIGN KEY(user_id)
            REFERENCES users(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_user_identity_sso
        FOREIGN KEY(sso_id)
            REFERENCES sso_provider(id)
            ON DELETE RESTRICT,
    CONSTRAINT unique_user_sso UNIQUE (user_id, sso_id)
);