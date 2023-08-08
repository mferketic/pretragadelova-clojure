CREATE TABLE parts
(id INTEGER PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(30),
 from_year INT,
 to_year INT,
 price DOUBLE,
 description VARCHAR(300),
 idModel INT,
 idType INT,
 timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (idModel) REFERENCES models(id),
 FOREIGN KEY (idType) REFERENCES types(id)
);