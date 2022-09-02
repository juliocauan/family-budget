DROP TABLE IF EXISTS revenues;
DROP TABLE IF EXISTS expenses;

CREATE TABLE revenues(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    day DATE NOT NULL
);

CREATE TABLE expenses(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    day DATE NOT NULL
);

INSERT INTO revenues(description, value, day) VALUES('Salário', 1383.14, '2022-09-15');
INSERT INTO revenues(description, value, day) VALUES('Investimento', 116.56, '2022-09-07');

INSERT INTO expenses(description, value, day) VALUES('Intercâmbio', 999.95, '2022-09-18');
INSERT INTO expenses(description, value, day) VALUES('Academia', 120.00, '2022-09-05');
