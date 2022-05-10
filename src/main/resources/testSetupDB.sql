CREATE SCHEMA IF NOT EXISTS CarportProjectTestDB;
USE CarportProjectTestDB;

DROP TABLE IF EXISTS Inquiry;
DROP TABLE IF EXISTS Shack;
DROP TABLE IF EXISTS Carport;
DROP TABLE IF EXISTS `Order`;
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
CREATE TABLE `Order` LIKE CarportProjectDB.`Order`;
CREATE TABLE Carport LIKE CarportProjectDB.Carport;
CREATE TABLE Shack LIKE CarportProjectDB.Shack;





