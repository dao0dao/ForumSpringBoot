CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    registration_status VARCHAR(30) NOT NULL,
    last_login TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT false
);

INSERT INTO
    users (
        username,
        password,
        email,
        created,
        updated,
        registration_status,
        last_login,
        deleted
    )
VALUES (
        'superadmin',
        '$2a$10$VSbvbBr1qVNWrz8Rt7JMi.n0EF0/uCqYaHPgVnEI5auQQsoYM0AKC',
        'superadmin@superadmin.com',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'ACTIVE',
        CURRENT_TIMESTAMP,
        FALSE
    ),
    (
        'admin',
        '$2a$10$KbE.e8dn3R9KcEfg/QTglu.yksJEDhnzI2qjYUEKFdOgve4AyBlZq',
        'admin@admin.com',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'ACTIVE',
        CURRENT_TIMESTAMP,
        FALSE
    ),
    (
        'user',
        '$2a$10$zkYHK.W.16DgApaA0MQtFe7awL4PeciM9UMpUTckn9ut4aww1EMn6',
        'user@user.com',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'ACTIVE',
        CURRENT_TIMESTAMP,
        FALSE
    );