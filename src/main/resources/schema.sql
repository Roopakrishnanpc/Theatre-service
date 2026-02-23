CREATE DATABASE theatre_db_write;
USE theatre_db_write;

CREATE TABLE theatre (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    partner_username VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT NOT NULL,
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_theatre_partner ON theatre(partner_username);
CREATE INDEX idx_theatre_active ON theatre(active);

CREATE TABLE theatre_show (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    theatre_id BIGINT NOT NULL,
    movie_name VARCHAR(255) NOT NULL,
    language VARCHAR(100),
    genre VARCHAR(100),
    show_date DATE NOT NULL,
    show_time TIME NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_show_theatre
        FOREIGN KEY (theatre_id)
        REFERENCES theatre(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_show_theatre ON theatre_show(theatre_id);
CREATE INDEX idx_show_movie_date ON theatre_show(movie_name, show_date);
CREATE INDEX idx_show_active ON theatre_show(active);
CREATE DATABASE theatre_db_read;
USE theatre_db_read;

CREATE TABLE theatre (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    city VARCHAR(255),
    partner_username VARCHAR(255),
    active BOOLEAN
);

CREATE INDEX idx_read_theatre_city ON theatre(city);

CREATE TABLE theatre_show (
    id BIGINT PRIMARY KEY,
    theatre_id BIGINT,
    movie_name VARCHAR(255),
    language VARCHAR(100),
    genre VARCHAR(100),
    show_date DATE,
    show_time TIME,
    active BOOLEAN
);

CREATE INDEX idx_read_show_movie_date ON theatre_show(movie_name, show_date);
CREATE INDEX idx_read_show_theatre ON theatre_show(theatre_id);