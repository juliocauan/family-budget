DROP TABLE IF EXISTS revenues;
DROP TABLE IF EXISTS expenses;

CREATE TABLE revenues(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    income_date DATE NOT NULL
);

CREATE TABLE expenses(
    id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    outcome_date DATE NOT NULL,
    category VARCHAR(15)
);

INSERT INTO revenues(description, quantity, income_date) VALUES('Salário', 1383.14, '2022-09-15');
INSERT INTO revenues(description, quantity, income_date) VALUES('Investimento', 116.56, '2022-09-07');

INSERT INTO expenses(description, quantity, outcome_date, category) VALUES('Intercâmbio', 999.95, '2022-09-18', 'EDUCATION');
INSERT INTO expenses(description, quantity, outcome_date, category) VALUES('Academia', 120.00, '2022-09-05', 'HEALTH');
