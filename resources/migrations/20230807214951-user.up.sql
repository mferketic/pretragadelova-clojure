CREATE TABLE users
(id INTEGER PRIMARY KEY AUTO_INCREMENT,
 username VARCHAR(30),
 email VARCHAR(30),
 password VARCHAR(30),
 idRole INT,
 timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (idRole) REFERENCES roles(id)
);