DROP DATABASE IF EXISTS banque;

CREATE DATABASE IF NOT EXISTS banque;
USE banque;


DROP TABLE agence;
CREATE TABLE IF NOT EXISTS agence
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   adresse VARCHAR(254) NOT NULL,
   nom VARCHAR(254) NOT NULL,
   PRIMARY KEY (id),
   INDEX(id)
 );
 
DROP TABLE client; 
CREATE TABLE IF NOT EXISTS client
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_agence INT(4) NOT NULL  ,
   nom VARCHAR(254) NOT NULL  ,
   prenom VARCHAR(254) NOT NULL  ,
   adresse VARCHAR(254) NOT NULL ,
   password VARCHAR(254) NOT NULL,
   PRIMARY KEY (id),
   INDEX(id_agence),
   INDEX(id),
   FOREIGN KEY (id_agence) REFERENCES  agence (id)
 ); 
 
DROP TABLE compte_depot; 
CREATE TABLE IF NOT EXISTS compte_depot
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_client INT(4) NOT NULL  ,
   libelle VARCHAR(254) NOT NULL  ,
   solde FLOAT DEFAULT NULL , 
   PRIMARY KEY (id) ,
   INDEX(id_client),
   INDEX(id),
   FOREIGN KEY (id_client) REFERENCES client (id)
 );
 
DROP TABLE livret_a; 
CREATE TABLE IF NOT EXISTS livret_a
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_client INT(4) NOT NULL  ,
   libelle VARCHAR(254) NOT NULL  ,
   solde FLOAT DEFAULT NULL , 
   taux FLOAT UNSIGNED DEFAULT NULL,
   plafond FLOAT DEFAULT NULL,
   PRIMARY KEY (id) ,
   INDEX(id_client),
   INDEX(id),
   FOREIGN KEY (id_client) REFERENCES client (id)
 );

DROP TABLE livret_jeune; 
CREATE TABLE IF NOT EXISTS livret_jeune
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_client INT(4) NOT NULL  ,
   libelle VARCHAR(254) NOT NULL  ,
   solde FLOAT DEFAULT NULL , 
   taux FLOAT UNSIGNED DEFAULT NULL,
   plafond FLOAT DEFAULT NULL,
   PRIMARY KEY (id) ,
   INDEX(id_client),
   INDEX(id),
   FOREIGN KEY (id_client) REFERENCES client (id)
 ); 
  
DROP TABLE operation_compte_depot;
CREATE TABLE IF NOT EXISTS operation_compte_depot
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_compte INT(4) NOT NULL  ,
   libelle VARCHAR(254) NOT NULL  ,
   montant FLOAT NOT NULL,
   date_op DATETIME NOT NULL,
   PRIMARY KEY (id),
   INDEX(id_compte),
   FOREIGN KEY (id_compte) REFERENCES  compte_depot (id)
 ); 

 DROP TABLE operation_livret_a;
CREATE TABLE IF NOT EXISTS operation_livret_a
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_compte INT(4) NOT NULL  ,
   libelle VARCHAR(254) NOT NULL  ,
   montant FLOAT NOT NULL,
   date_op DATETIME NOT NULL,
   PRIMARY KEY (id),
   INDEX(id_compte),
   FOREIGN KEY (id_compte) REFERENCES  livret_a (id)
 );

DROP TABLE operation_livret_jeune;
CREATE TABLE IF NOT EXISTS operation_livret_jeune
 (
   id INT(4) NOT NULL AUTO_INCREMENT,
   id_compte INT(4) NOT NULL  ,
   libelle VARCHAR(254) NOT NULL  ,
   montant FLOAT NOT NULL,
   date_op DATETIME NOT NULL,
   PRIMARY KEY (id),
   INDEX(id_compte),
   FOREIGN KEY (id_compte) REFERENCES  livret_jeune (id)
 );  
 
 
DROP TRIGGER maj_solde_compte_depot; 
DELIMITER //
CREATE TRIGGER maj_solde_compte_depot AFTER INSERT ON operation_compte_depot
FOR EACH ROW 
BEGIN
UPDATE compte_depot SET solde = solde + new.montant WHERE id = new.id_compte; 
END;
END //
DELIMITER ;

DROP TRIGGER maj_solde_livret_a; 
DELIMITER //
CREATE TRIGGER maj_solde_livret_a AFTER INSERT ON operation_livret_a
FOR EACH ROW 
BEGIN
UPDATE livret_a SET solde = solde + new.montant WHERE id = new.id_compte; 
END;
END //
DELIMITER ;

DROP TRIGGER maj_solde_livret_jeune; 
DELIMITER //
CREATE TRIGGER maj_solde_livret_jeune AFTER INSERT ON operation_livret_jeune
FOR EACH ROW 
BEGIN
UPDATE livret_jeune SET solde = solde + new.montant WHERE id = new.id_compte; 
END;
END //
DELIMITER ;
 

INSERT INTO agence VALUES (null,'55 rue des Peupliers 75000 Paris','Banque LCL Paris'); 

INSERT INTO client VALUES (null,LAST_INSERT_ID(),'Grégoire','Julien','55 rue de Mareil 78100 Saint Germain en Laye','123');
INSERT INTO compte_depot VALUES (null,LAST_INSERT_ID(),'Compte de dépots',10.00);
INSERT INTO operation_compte_depot VALUES (null,LAST_INSERT_ID(),'Virement SageCom #13424',1234.00,NOW());
INSERT INTO operation_compte_depot VALUES (null,1,'Retrait distrib La Défense',-10.00,NOW());
INSERT INTO operation_compte_depot VALUES (null,1,'Depot Argent ',50.00,NOW());
INSERT INTO operation_compte_depot VALUES (null,1,'Cheque N°203923',-450.00,NOW());
INSERT INTO operation_compte_depot VALUES (null,1,'EDF',-35.00,NOW());
INSERT INTO livret_jeune VALUES (null,1,'Livret jeune',0.00,1.25,1600);
INSERT INTO operation_livret_jeune VALUES (null,1,'Virement',45.00,NOW());
INSERT INTO operation_livret_jeune VALUES (null,1,'Virement',20.00,NOW());
INSERT INTO operation_livret_jeune VALUES (null,1,'Virement',100.00,NOW());
INSERT INTO operation_livret_jeune VALUES (null,1,'Virement',400.00,NOW());
INSERT INTO operation_livret_jeune VALUES (null,1,'Virement',90.20,NOW());
INSERT INTO operation_livret_jeune VALUES (null,1,'Virement',660.00,NOW());


INSERT INTO client VALUES (null,1,'Hamid','MZé','10 rue DTC 7500 Paris','123');
INSERT INTO compte_depot VALUES (null,LAST_INSERT_ID(),'Compte de dépots',100.00);
INSERT INTO operation_compte_depot VALUES (null,2,'Virement FNAC #132',1430.00,NOW());
INSERT INTO operation_compte_depot VALUES (null,2,'Retrait distrib Paris 14',-10.00,NOW());
INSERT INTO operation_compte_depot VALUES (null,2,'Mc DO',-40.00,NOW());
INSERT INTO operation_compte_depot VALUES (null,2,'KFC',-6.80,NOW());




