CREATE SCHEMA IF NOT EXISTS CarportProjectTestDB;
USE CarportProjectTestDB;

DROP TABLE IF EXISTS Inquiry;
DROP TABLE IF EXISTS Shack;
DROP TABLE IF EXISTS Carport;
DROP TABLE IF EXISTS `Account`;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS Bill_of_material_line_item;
DROP TABLE IF EXISTS Product_variant;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Product_type;
DROP TABLE IF EXISTS Size;

CREATE TABLE Product_type LIKE CarportProjectDB.Product_type;
CREATE TABLE Product LIKE CarportProjectDB.Product;
CREATE TABLE Size LIKE CarportProjectDB.Size;
CREATE TABLE Product_variant LIKE CarportProjectDB.Product_variant;
CREATE TABLE Address LIKE CarportProjectDB.Address;
CREATE TABLE Person LIKE CarportProjectDB.Person;
CREATE TABLE `Account` LIKE CarportProjectDB.`Account`;
CREATE TABLE Bill_of_material_line_item LIKE CarportProjectDB.Bill_of_material_line_item;
CREATE TABLE `Inquiry` LIKE CarportProjectDB.`Inquiry`;
CREATE TABLE Carport LIKE CarportProjectDB.Carport;
CREATE TABLE Shack LIKE CarportProjectDB.Shack;

CREATE VIEW `PersonDTO` AS SELECT * FROM Person INNER JOIN Address USING(address_ID);
CREATE VIEW `AccountDTO` AS SELECT * FROM `Account` INNER JOIN PersonDTO USING(person_ID);
CREATE VIEW `CarportDTO` AS SELECT * FROM Carport LEFT JOIN Shack USING(shack_ID);
CREATE VIEW `ProductDTO` AS SELECT * FROM Product INNER JOIN Product_type USING(product_type_ID);
CREATE VIEW `ProductVariantDTO` AS SELECT * FROM Product_variant INNER JOIN ProductDTO USING(product_ID) INNER JOIN Size USING(size_ID);
CREATE VIEW `lineitemdto` AS SELECT * FROM Bill_of_material_line_item INNER JOIN productvariantdto USING(product_variant_ID);


delete from Product_variant;
delete from Size;
delete from Product;
delete from Product_type;



ALTER TABLE Product_type DISABLE KEYS;
ALTER TABLE Product_type  AUTO_INCREMENT = 1;
INSERT INTO Product_type (product_type_name) values ('Stolpe'), ('Spærtræ'), ('Brædt'), ('Træ'),
                                                    ('Lægte'), ('Tagplade'), ('Skrue'), ('Bolte'),
                                                    ('Skive'), ('Bånd'), ('Universal'), ('Dørgreb'),
                                                    ('Hængsel'), ('Beslag');
ALTER TABLE Product_type ENABLE KEYS;


ALTER TABLE Product DISABLE KEYS;
ALTER TABLE Product  AUTO_INCREMENT = 1;
INSERT INTO Product (product_description, product_unit_price, product_unit, product_type_ID, product_amount_unit)
values ('97x97 mm. trykimp. Stolpe', 50, 'METER', 1, 'PIECE'),
('45x195 mm. Spærtræ ubh.', 50, 'METER', 2, 'PIECE'),
('25x200 mm. trykimp. Brædt', 40, 'METER', 3, 'PIECE'),
('25x125 mm. trykimp. Brædt', 30, 'METER', 3, 'PIECE'),
('19x100 mm. trykimp. Brædt', 20, 'METER', 3, 'PIECE'),
('45x95 mm. regular ub.', 30, 'METER', 4, 'PIECE'),
('38x73 mm. Lægte ubh.', 10, 'METER', 5, 'PIECE'),
('Plastmo Ecolite blåtonet', 15, 'METER', 6, 'PIECE'),
('Plastmo bundskruer 200 stk.', 5, 'PIECE', 7, 'PACK'),
('4,0x50 mm. Beslagskruer 250 stk.', 5, 'PIECE', 7, 'PACK'),
('4,5x50 mm. Skruer	300	stk.', 5, 'PIECE', 7, 'PACK'),
('4,5x60 mm. Skruer 200 stk.', 5, 'PIECE', 7, 'PACK'),
('4,5x70 mm. Skruer 400 stk.', 5, 'PIECE', 7, 'PACK'),
('Bræddebolt 10x120 mm.', 10, 'PIECE', 8, 'PIECE'),
('Firkantskiver 40x40x11 mm.', 15, 'PIECE', 9, 'PIECE'),
('Hulbånd 1x20 mm. 10 mtr', 5, 'METER', 10, 'ROLL'),
('Universal 190 mm. højre', 10, 'PIECE', 11, 'PIECE'),
('Universal 190 mm. venstre', 10, 'PIECE', 11, 'PIECE'),
('Stalddørsgreb 50x75', 35, 'PIECE', 12, 'SET'),
('T hængsel 390 mm.', 15, 'PIECE', 13, 'PIECE'),
('Vinkelbeslag 35', 15, 'PIECE', 14, 'PIECE');
ALTER TABLE Product ENABLE KEYS;


ALTER TABLE Size DISABLE KEYS;
ALTER TABLE Size  AUTO_INCREMENT = 1;
INSERT INTO Size (size_detail, size_type)
values (210, 'LENGTH'), (240, 'LENGTH'), (270, 'LENGTH'),
(300, 'LENGTH'), (330, 'LENGTH'), (360, 'LENGTH'),
(390, 'LENGTH'), (420, 'LENGTH'), (450, 'LENGTH'),
(480, 'LENGTH'), (510, 'LENGTH'), (540, 'LENGTH'),
(570, 'LENGTH'), (600, 'LENGTH'),
(100, 'PIECES'), (150, 'PIECES'), (200, 'PIECES'),
(250, 'PIECES'), (300, 'PIECES'), (350, 'PIECES'), (400, 'PIECES');
ALTER TABLE Size ENABLE KEYS;


ALTER TABLE Product_variant DISABLE KEYS;
ALTER TABLE Product_variant  AUTO_INCREMENT = 1;
INSERT INTO Product_variant (product_ID, size_ID)
values (1, 4), (1, 5),
       (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8), (2, 9), (2, 10),
       (2, 11), (2, 12), (2, 13), (2, 14),
       (3, 4), (3, 6), (3, 8), (3, 10), (3, 12), (3, 14),
       (4, 4), (4, 6), (4, 8), (4, 10), (4, 12), (4, 14),
       (5, 4), (5, 6), (5, 8), (5, 10), (5, 12), (5, 14),
       (6, 1), (6, 2), (6, 3), (6, 4),
       (7, 8),
       (8, 2), (8, 6), (8, 10), (8, 14),
       (9, 17),
       (10, 18),
       (11, 17), (10, 19), (10, 21),
       (12, 17), (12, 19), (12, 21),
       (13, 17), (12, 19), (12, 21);
ALTER TABLE Product_variant ENABLE KEYS;



