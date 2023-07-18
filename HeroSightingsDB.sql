DROP DATABASE IF EXISTS herosightings;

CREATE DATABASE herosightings;

USE herosightings;

CREATE TABLE IF NOT EXISTS power (
	PowerPK INT NOT NULL AUTO_INCREMENT,
	Power VARCHAR(50) NOT NULL,
	`Description` VARCHAR(255) NOT NULL,
	PRIMARY KEY pk_Power (powerPK)  
);

CREATE TABLE IF NOT EXISTS hero (
	HeroPK INT NOT NULL AUTO_INCREMENT,
	HeroName VARCHAR(50) NOT NULL,
	`Type` VARCHAR(15) NOT NULL,
	`Description` VARCHAR(255) NOT NULL,
	PowerPK INT NULL,
   	PRIMARY KEY pk_Hero (heroPK) 
);

CREATE TABLE IF NOT EXISTS location (
	LocationPK INT NOT NULL AUTO_INCREMENT,
	LocationName VARCHAR(50) NOT NULL,
	`Description` VARCHAR(255) NOT NULL,
	LocationAddress VARCHAR(150) NOT NULL,
	Latitude VARCHAR(10) NOT NULL,
	Longitude VARCHAR(10) NOT NULL,
	PRIMARY KEY pk_Location (LocationPK) 
  );
  
  CREATE TABLE IF NOT EXISTS sighting (
	SightingPK INT NOT NULL AUTO_INCREMENT,
	SightingDate DATETIME NOT NULL,
	`Description` VARCHAR(255) NULL,
	HeroPK INT NOT NULL,
	LocationPK INT NOT NULL,
	PRIMARY KEY pk_Sighting (SightingPK) 
);

CREATE TABLE IF NOT EXISTS `organization` (
	OrganizationPK INT NOT NULL AUTO_INCREMENT,
	OrganizationName VARCHAR(50) NOT NULL,
    `Type` VARCHAR(15) NOT NULL,
	`Description` VARCHAR(255) NOT NULL,
	OrganizationAddress VARCHAR(150) NOT NULL,
   	Phone VARCHAR(20) NOT NULL,
	ContactInfo VARCHAR(150), 
  	PRIMARY KEY pk_Organization (OrganizationPK)
  );
  
CREATE TABLE IF NOT EXISTS heroorganization (
	HeroPK INT NOT NULL,
	OrganizationPK INT NOT NULL,
	PRIMARY KEY pk_HeroOrganization (HeroPK, OrganizationPK)
);
  
  
  
-- FKs
ALTER TABLE hero
	ADD CONSTRAINT fk_HeroPower
    FOREIGN KEY fk_HeroPower (PowerPK)
    REFERENCES power (PowerPK);
    
ALTER TABLE sighting
	ADD CONSTRAINT fk_SightingHero
    FOREIGN KEY fk_SightingHero (HeroPK)
    REFERENCES hero (HeroPK);
    
ALTER TABLE sighting
	ADD CONSTRAINT fk_SightingLocation
    FOREIGN KEY fk_SightingLocation (LocationPK)
    REFERENCES location (LocationPK);
    
ALTER TABLE heroorganization
	ADD CONSTRAINT fk_HeroOrganizationHero
    FOREIGN KEY fk_HeroOrganizationHero (HeroPK)
    REFERENCES hero (HeroPK);
    
ALTER TABLE heroorganization
	ADD CONSTRAINT fk_HeroOrganizationOrganization
    FOREIGN KEY fk_HeroOrganizationOrganization (OrganizationPK)
    REFERENCES `organization` (OrganizationPK);
    
USE herosightings;
SELECT * FROM hero;
SELECT * FROM heroorganization;
SELECT * FROM location;
SELECT * FROM `organization`;
SELECT * FROM power;
SELECT * FROM sighting;



    

    
    

 
