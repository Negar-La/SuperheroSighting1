DROP DATABASE IF EXISTS HeroSightingsTest;

CREATE DATABASE HeroSightingsTest;

USE HeroSightingsTest;

CREATE TABLE IF NOT EXISTS Power (
	PowerPK INT NOT NULL AUTO_INCREMENT,
	Power VARCHAR(50) NOT NULL,
	`Description` VARCHAR(255) NOT NULL,
	PRIMARY KEY pk_Power (powerPK)  
);

CREATE TABLE IF NOT EXISTS Hero (
	HeroPK INT NOT NULL AUTO_INCREMENT,
	HeroName VARCHAR(50) NOT NULL,
	`Type` VARCHAR(15) NOT NULL,
	`Description` VARCHAR(255) NOT NULL,
	PowerPK INT NULL,
    PRIMARY KEY pk_Hero (heroPK) 
);

CREATE TABLE IF NOT EXISTS Location (
	LocationPK INT NOT NULL AUTO_INCREMENT,
	LocationName VARCHAR(50) NOT NULL,
	`Description` VARCHAR(255) NOT NULL,
	LocationAddress VARCHAR(150) NOT NULL,
	Latitude VARCHAR(10) NOT NULL,
	Longitude VARCHAR(10) NOT NULL,
	PRIMARY KEY pk_Location (LocationPK) 
  );
  
  CREATE TABLE IF NOT EXISTS Sighting (
	SightingPK INT NOT NULL AUTO_INCREMENT,
	SightingDate DATETIME NOT NULL,
	`Description` VARCHAR(255) NULL,
	HeroPK INT NOT NULL,
	LocationPK INT NOT NULL,
	PRIMARY KEY pk_Sighting (SightingPK) 
);

CREATE TABLE IF NOT EXISTS `Organization` (
	OrganizationPK INT NOT NULL AUTO_INCREMENT,
	OrganizationName VARCHAR(50) NOT NULL,
    `Type` VARCHAR(15) NOT NULL,
	`Description` VARCHAR(255) NOT NULL,
	OrganizationAddress VARCHAR(150) NOT NULL,
   	Phone VARCHAR(20) NOT NULL,
	ContactInfo VARCHAR(150), 
  	PRIMARY KEY pk_Organization (OrganizationPK)
  );
  
CREATE TABLE IF NOT EXISTS HeroOrganization (
	HeroPK INT NOT NULL,
	OrganizationPK INT NOT NULL,
	PRIMARY KEY pk_HeroOrganization (HeroPK, OrganizationPK)
);
  
  
  
-- FKs
ALTER TABLE Hero
	ADD CONSTRAINT fk_HeroPower
    FOREIGN KEY fk_HeroPower (PowerPK)
    REFERENCES Power (PowerPK);
    
ALTER TABLE Sighting
	ADD CONSTRAINT fk_SightingHero
    FOREIGN KEY fk_SightingHero (HeroPK)
    REFERENCES Hero (HeroPK);
    
ALTER TABLE Sighting
	ADD CONSTRAINT fk_SightingLocation
    FOREIGN KEY fk_SightingLocation (LocationPK)
    REFERENCES Location (LocationPK);
    
ALTER TABLE HeroOrganization
	ADD CONSTRAINT fk_HeroOrganizationHero
    FOREIGN KEY fk_HeroOrganizationHero (HeroPK)
    REFERENCES Hero (HeroPK);
    
ALTER TABLE HeroOrganization
	ADD CONSTRAINT fk_HeroOrganizationOrganization
    FOREIGN KEY fk_HeroOrganizationOrganization (OrganizationPK)
    REFERENCES `Organization` (OrganizationPK);
    
 USE HeroSightingsTest;
 SELECT * FROM hero;
 SELECT * FROM heroorganization;
 SELECT * FROM location;
 SELECT * FROM `organization`;
 SELECT * FROM power;
 SELECT * FROM sighting;
    

    
    

 
