CREATE SCHEMA IF NOT EXISTS app;
CREATE SCHEMA IF NOT EXISTS auth;

DROP TABLE IF EXISTS app.revenues;
DROP TABLE IF EXISTS app.expenses;
DROP TABLE IF EXISTS auth.users;
DROP TABLE IF EXISTS auth.roles;
DROP TABLE IF EXISTS auth.users_roles;

CREATE TABLE app.revenues(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    income_date DATE NOT NULL);

CREATE TABLE app.expenses(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    outcome_date DATE NOT NULL,
    category VARCHAR(15));

CREATE TABLE auth.users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    name VARCHAR(128) NOT NULL,
    email VARCHAR(128) UNIQUE NOT NULL,
    secret VARCHAR(255) NOT NULL);
CREATE TABLE auth.roles(
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL);
CREATE TABLE auth.users_roles(
    user_id UUID REFERENCES auth.users(id),
    role_id SMALLINT REFERENCES auth.roles(id),
    PRIMARY KEY(user_id, role_id));

INSERT INTO app.revenues(description, quantity, income_date) VALUES('Salário', 1383.14, '2022-09-15');
INSERT INTO app.revenues(description, quantity, income_date) VALUES('Investimento', 116.56, '2022-09-07');

INSERT INTO app.expenses(description, quantity, outcome_date, category) VALUES('Intercâmbio', 999.95, '2022-09-18', 'EDUCATION');
INSERT INTO app.expenses(description, quantity, outcome_date, category) VALUES('Academia', 120.00, '2022-09-05', 'HEALTH');

INSERT INTO auth.users(name, email, secret) VALUES
    ('Julio', 'julio@test.com', '$2a$10$ofEy..aODV5QleKty0kkJ.8UXdOXIdr/CeyXswcjJGBVYgxU296NK');
INSERT INTO auth.users(name, email, secret) VALUES
    ('Cauan', 'cauan@test.com', '$2a$10$ofEy..aODV5QleKty0kkJ.8UXdOXIdr/CeyXswcjJGBVYgxU296NK');

INSERT INTO auth.roles(name) VALUES ('ADMIN');
INSERT INTO auth.roles(name) VALUES ('CLIENT');
