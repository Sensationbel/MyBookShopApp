DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

CREATE TABLE authors(
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL
);

CREATE TABLE  books(
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(250) NOT NULL,
price_Old  VARCHAR(250) DEFAULT NULL,
price VARCHAR(250) DEFAULT NULL,
author_id INT NOT NULL
);

