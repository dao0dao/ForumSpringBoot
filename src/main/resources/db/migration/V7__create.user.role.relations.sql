CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    user_system_name VARCHAR(50) UNIQUE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_by VARCHAR(50) NOT NULL
);

CREATE Table users_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);