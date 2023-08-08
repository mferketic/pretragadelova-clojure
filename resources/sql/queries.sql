---------------------ROLE--------------------------------

-- :name create-role! :! :n
-- :doc creates a new role record
INSERT INTO roles
(name)
VALUES (:name)

--------------------USERS---------------------------------

-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(username, email, password, idRole)
VALUES (:username, :email, :password, :idRole)

-- :name get-user-by-id :? :1
-- :doc get user by his id
SELECT * FROM users
WHERE id = :id

--------------------SERVICE---------------------------------

-- :name create-service! :! :n
-- :doc creates a new service record
INSERT INTO service
(name)
VALUES (:name)

--------------------TYPE-------------------------------------

-- :name create-type! :! :n
-- :doc creates a new type record
INSERT INTO types
(name, idservice)
VALUES (:name, :idservice)

-- :name get-types-by-service-id :? :*
-- :doc selects types that has service id
SELECT * FROM types
WHERE idService = :idService

--------------------MODEL-------------------------------------

-- :name create-model! :! :n
-- :doc creates a new type record
INSERT INTO models
(name)
VALUES (:name)

-- :name get-models :? :*
-- :doc shows all models
SELECT * from models

--------------------PART-------------------------------------

-- :name create-part! :! :n
-- :doc creates a new part record
INSERT INTO parts
(name, from_year, to_year, price, description, idModel, idType)
VALUES (:name, :from_year, :to_year, :price, :description, :idModel, :idType)

-- :name update-part! :! :n
-- :doc update existing part with id
UPDATE parts
SET name = :name, from_year = :from_year, to_year = :to_year, price = :price, description = :description, idModel = :idModel, idType = :idType
WHERE id = :id

-- :name delete-part! :! :n
-- :doc delete part with id
DELETE FROM parts
WHERE id = :id

-- :name get-part-by-id :? :1
-- :doc selects part that has this id
SELECT * FROM parts
WHERE id = :id

-- :name get-parts :? :*
-- :doc shows all parts
SELECT * from parts

-- :name get-my-parts :? :*
-- :doc shows all parts with model and year user entered
SELECT * FROM `parts` JOIN types ON parts.idType = types.id
                      JOIN services ON types.idService = services.id
                      JOIN models ON parts.idModel = models.id
                        WHERE services.id = idService AND models.id = idModel
                            AND :year BETWEEN parts.from_year AND parts.to_year;

--------------------CART-------------------------------------

-- :name add-to-cart! :! :n
-- :doc creates a new cart record
INSERT INTO cart
(idUser, idPart)
VALUES (:idUser, :idPart)

-- :name delete-from-cart! :! :n
-- :doc delete cart with id
DELETE FROM cart
WHERE id = :id

-- :name get-cart-for-user-id :? :1
-- :doc selects cart items for user
SELECT * FROM cart
                  JOIN users ON users.id = cart.idUser
                  JOIN parts ON parts.id = cart.idPart
                  JOIN types ON parts.idType = types.id
                    WHERE users.id = :id;




