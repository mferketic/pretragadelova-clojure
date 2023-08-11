CREATE TABLE orders
(idOrder INTEGER PRIMARY KEY AUTO_INCREMENT,
 city VARCHAR(70),
 email VARCHAR(70),
 phone INT,
 idPart INT,
 FOREIGN KEY (idPart) REFERENCES parts(idPart)
);