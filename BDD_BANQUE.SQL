DROP DATABASE IF EXISTS banque;

CREATE DATABASE IF NOT EXISTS banque;
USE banque;


DROP TABLE agence;
CREATE TABLE IF NOT EXISTS agence
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   adresse VARCHAR(254) NOT NULL,
   nom VARCHAR(254) NOT NULL,
   PRIMARY KEY (id) 
 );
 
DROP TABLE client; 
CREATE TABLE IF NOT EXISTS client
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_agence INT(4) NOT NULL  ,
   nom VARCHAR(254) NOT NULL  ,
   prenom VARCHAR(254) NOT NULL  ,
   adresse VARCHAR(254) NOT NULL ,
   PRIMARY KEY (id),
   INDEX(id_agence),
   FOREIGN KEY (id_agence) REFERENCES  agence (id)
 ); 
 
DROP TABLE compte; 
CREATE TABLE IF NOT EXISTS compte
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_client INT(4) NOT NULL  ,
   libelle VARCHAR(254) NOT NULL  ,
   solde INT DEFAULT NULL , 
   taux FLOAT UNSIGNED DEFAULT NULL,
   plafond INT DEFAULT NULL,
   depot BIT(1) DEFAULT 0  ,
   PRIMARY KEY (id) ,
   INDEX(id_client),
   FOREIGN KEY (id_client) REFERENCES client (id)
 );
 
DROP TABLE `operation`;
CREATE TABLE IF NOT EXISTS `operation`
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_compte INT(4) NOT NULL  ,
   libelle VARCHAR(254) NOT NULL  ,
   montant INT NOT NULL,
   PRIMARY KEY (id),
   INDEX(id_compte),
   FOREIGN KEY (id_compte) REFERENCES  compte (id)
 ); 

DROP TRIGGER maj_solde; 

DELIMITER //
CREATE TRIGGER maj_solde AFTER INSERT ON `operation`
FOR EACH ROW 
BEGIN
UPDATE compte SET solde = solde + new.montant WHERE id = new.id_compte; 
END;
END //
DELIMITER ;
 

INSERT INTO agence VALUES (null,'55 rue des Peupliers 78344 Poissy','Banque Poissy'); 
INSERT INTO client VALUES (null,LAST_INSERT_ID(),'Grégoire','Julien','55 rue de Mareil 78100 Saint Germain en Laye');
INSERT INTO compte VALUES (null,LAST_INSERT_ID(),'Compte de dépots',1000,null,null,0);
INSERT INTO `operation` VALUES (null,LAST_INSERT_ID(),'Virement SageCom #13424',200);




