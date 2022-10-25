DROP TABLE IF EXISTS revenues;
DROP TABLE IF EXISTS expenses;

CREATE TABLE revenues(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    income_date DATE NOT NULL
);

CREATE TABLE expenses(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    outcome_date DATE NOT NULL
);

INSERT INTO revenues(description, value, income_date) VALUES('Salário', 1383.14, '2022-09-15');
INSERT INTO revenues(description, value, income_date) VALUES('Investimento', 116.56, '2022-09-07');

INSERT INTO expenses(description, value, outcome_date) VALUES('Intercâmbio', 999.95, '2022-09-18');
INSERT INTO expenses(description, value, outcome_date) VALUES('Academia', 120.00, '2022-09-05');