CREATE TABLE _user (
    user_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(75),
    password TEXT NOT NULL,
    email VARCHAR(75) NOT NULL,
    avatar TEXT,
    otp_code VARCHAR(10),
    otp_expiration_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    status INTEGER NOT NULL
);

CREATE TABLE _role (
    role_id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(75) NOT NULL,
    description VARCHAR(255),

    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP,
    status INTEGER NOT NULL
);

CREATE TABLE user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT null,

    CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES _role(role_id),
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES _user(user_id)
);
