CREATE TABLE product (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL
);

-- DROP TABLE IF EXISTS messages cascade;
-- DROP TABLE IF EXISTS rooms cascade;
-- DROP TABLE IF EXISTS users cascade;
-- DROP TABLE IF EXISTS product cascade;
