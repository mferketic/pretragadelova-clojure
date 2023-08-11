--------------------USERS---------------------------------

-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(email, password)
VALUES (:email, :password)

-- :name get-user-by-id :? :1
-- :doc get user by his id
SELECT * FROM users
WHERE email = :email

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
(typeName, idservice)
VALUES (:name, :idservice)

-- :name get-types :? :*
-- :doc selects types that has service id
SELECT * FROM types

-- :name get-types-by-service-id :? :*
-- :doc selects types that has service id
SELECT * FROM types
WHERE idService = :idService

--------------------MODEL-------------------------------------

-- :name create-model! :! :n
-- :doc creates a new type record
INSERT INTO models
(modelName)
VALUES (:name)

-- :name get-models :? :*
-- :doc shows all models
SELECT * from models

--------------------PART-------------------------------------

-- :name create-part! :! :n
-- :doc creates a new part record
INSERT INTO parts
(name, from_year, to_year, price, description, idModel, idType)
VALUES (:partName, :fromYear, :toYear, :price, :description, :model1, :type1)

-- :name update-part! :! :n
-- :doc update existing part with id
UPDATE parts
SET name = :name, from_year = :from_year, to_year = :to_year, price = :price, description = :description, idModel = :idModel, idType = :idType
WHERE idPart = :id

-- :name delete-part! :! :n
-- :doc delete part with id
DELETE FROM parts
WHERE idPart = :id

-- :name get-part-by-id :? :1
-- :doc selects part that has this id
SELECT * FROM parts
WHERE idPart = :id

-- :name get-parts :? :*
-- :doc shows all parts
SELECT * from parts
    JOIN types ON parts.idType = types.idType
    JOIN models ON parts.idModel = models.idModel

-- :name get-my-parts :? :*
-- :doc shows all parts with model and year user entered (with type parts)
SELECT *FROM parts
         JOIN types ON parts.idType = types.idType
         JOIN models ON parts.idModel = models.idModel
WHERE models.idModel = :model1
    AND :year1 BETWEEN parts.from_year AND parts.to_year
    AND
        (CASE
            WHEN :type1 != 0 THEN parts.idType = :type1
            ELSE 1
        END);

--------------------ORDER-------------------------------------

-- :name add-order! :! :n
-- :doc creates a new order record
INSERT INTO orders
(city, email, phone, quantity, idPart)
VALUES (:city, :email, :phone, :quantity, :idPart)

-- :name get-orders-cart! :? :*
-- :doc get all orders
SELECT * FROM orders





