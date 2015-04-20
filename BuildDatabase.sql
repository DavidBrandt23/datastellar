DROP DATABASE Datastellar;
DROP TABLE Moon;
DROP TABLE Planet;
DROP TABLE Star;
DROP TABLE Galaxy;
DROP TABLE Species;
DROP TABLE Inhabits;
CREATE DATABASE Datastellar;
Use Datastellar;
CREATE TABLE Galaxy(
	name VARCHAR(20),
    shape VARCHAR(20),
    diameter INT,
    PRIMARY KEY (name)
);
CREATE TABLE Star(
	name VARCHAR(20),
    mass INT,
    class VARCHAR(20),
	`in-galaxy` VARCHAR(20),
    FOREIGN KEY(`in-galaxy`) REFERENCES Galaxy(name),
    PRIMARY KEY (name)
);
CREATE TABLE Planet(
	name VARCHAR(20),
    mass INT,
	`orbits-star` VARCHAR(20),
    `orbital-period` INT,
    population BIGINT,
	`in-galaxy` VARCHAR(20),
    FOREIGN KEY(`orbits-star`) REFERENCES Star(name),
    FOREIGN KEY(`in-galaxy`) REFERENCES Galaxy(name),
    PRIMARY KEY (name)
);
CREATE TABLE Moon(
	name VARCHAR(20),
    mass INT,
	`orbits-planet` VARCHAR(20),
    `orbital-period` INT,
	`in-galaxy` VARCHAR(20),
    FOREIGN KEY(`orbits-planet`) REFERENCES Planet(name),
    FOREIGN KEY(`in-galaxy`) REFERENCES Galaxy(name),
    PRIMARY KEY (name)
);
CREATE TABLE Species(
	name VARCHAR(20),
    height INT,
    `number-living` BIGINT,
    hostility INT,
    PRIMARY KEY (name)
);
CREATE TABLE Inhabits(
	`species-name` VARCHAR(20),
	`planet-name` VARCHAR(20),
    FOREIGN KEY(`species-name`) REFERENCES Species(name),
    FOREIGN KEY(`planet-name`) REFERENCES Planet(name),
    PRIMARY KEY (`species-name`,`planet-name`)
);
INSERT INTO Galaxy VALUE("Milky Way","Spiral",10000);
INSERT INTO Star VALUE("Sol",9999,"Yellow Giant","Milky Way");
INSERT INTO Planet VALUE("Earth",999,"Sol",1000,6000000000,"Milky Way");
INSERT INTO Moon VALUE("Luna",100,"Earth",10,"Milky Way");
INSERT INTO Species VALUE("Human",6,6000000000,1);
INSERT INTO Inhabits VALUE("Human","Earth");
