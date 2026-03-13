CREATE TABLE IF NOT EXISTS shift_type (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    active_status BOOLEAN NOT NULL,
    tenant_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    logged_in BOOLEAN NOT NULL,
    timezone VARCHAR(50) NOT NULL,
    tenant_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shift (
    id UUID PRIMARY KEY,
    shift_type_id UUID NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    tenant_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_shift_type FOREIGN KEY (shift_type_id) REFERENCES shift_type(id)
);

CREATE TABLE IF NOT EXISTS shift_user (
    id UUID PRIMARY KEY,
    shift_id UUID NOT NULL,
    user_id UUID NOT NULL,
    tenant_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_shift FOREIGN KEY (shift_id) REFERENCES shift(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
