CREATE SCHEMA IF NOT EXISTS fb;

DROP TABLE IF EXISTS fb.revenues;
DROP TABLE IF EXISTS fb.expenses;
DROP TABLE IF EXISTS fb.users;

CREATE TABLE fb.revenues(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    income_date DATE NOT NULL
);

CREATE TABLE fb.expenses(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    outcome_date DATE NOT NULL,
    category VARCHAR(15)
);

CREATE TABLE fb.users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    name VARCHAR(128) NOT NULL,
    email VARCHAR(128) UNIQUE,
    secret VARCHAR(255) NOT NULL
);

INSERT INTO fb.revenues(description, quantity, income_date) VALUES('Salário', 1383.14, '2022-09-15');
INSERT INTO fb.revenues(description, quantity, income_date) VALUES('Investimento', 116.56, '2022-09-07');

INSERT INTO fb.expenses(description, quantity, outcome_date, category) VALUES('Intercâmbio', 999.95, '2022-09-18', 'EDUCATION');
INSERT INTO fb.expenses(description, quantity, outcome_date, category) VALUES('Academia', 120.00, '2022-09-05', 'HEALTH');

INSERT INTO fb.users (name, email, secret) VALUES
    ('Julio', 'julio@test.com', '$2a$10$ofEy..aODV5QleKty0kkJ.8UXdOXIdr/CeyXswcjJGBVYgxU296NK');
INSERT INTO fb.users (name, email, secret) VALUES
    ('Cauan', 'cauan@test.com', '$2a$10$ofEy..aODV5QleKty0kkJ.8UXdOXIdr/CeyXswcjJGBVYgxU296NK');
