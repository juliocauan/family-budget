DROP TABLE IF EXISTS revenues;
DROP TABLE IF EXISTS expenses;

CREATE TABLE revenues(
    id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    incoming_date DATE NOT NULL
) ENGINE=InnoDB;

CREATE TABLE expenses(
    id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    outcoming_date DATE NOT NULL
) ENGINE=InnoDB;
