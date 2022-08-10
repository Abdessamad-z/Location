CREATE DATABASE  IF NOT EXISTS `java` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `java`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: java
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `codeClient` int NOT NULL AUTO_INCREMENT,
  `nomComplet` varchar(25) NOT NULL,
  `adresse` varchar(50) NOT NULL,
  `numGSM` varchar(15) NOT NULL,
  `permis` longblob,
  PRIMARY KEY (`codeClient`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contrat`
--

DROP TABLE IF EXISTS `contrat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contrat` (
  `numContrat` int NOT NULL AUTO_INCREMENT,
  `dateContrat` date DEFAULT (curdate()),
  `dateEcheance` date NOT NULL,
  `dateRestitution` date DEFAULT NULL,
  `signé` tinyint NOT NULL,
  `codeReservation` int DEFAULT NULL,
  PRIMARY KEY (`numContrat`),
  KEY `contrat_ibfk_1` (`codeReservation`),
  CONSTRAINT `contrat_ibfk_1` FOREIGN KEY (`codeReservation`) REFERENCES `reservation` (`codeReservation`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `facture`
--

DROP TABLE IF EXISTS `facture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facture` (
  `numFacture` int NOT NULL AUTO_INCREMENT,
  `dateFacture` date DEFAULT (curdate()),
  `montant` double NOT NULL,
  `numContrat` int DEFAULT NULL,
  PRIMARY KEY (`numFacture`),
  KEY `numContrat` (`numContrat`),
  CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`numContrat`) REFERENCES `contrat` (`numContrat`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parking`
--

DROP TABLE IF EXISTS `parking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parking` (
  `numParking` int NOT NULL AUTO_INCREMENT,
  `capacite` int NOT NULL,
  `rue` varchar(20) NOT NULL,
  `arrondissement` varchar(20) NOT NULL,
  PRIMARY KEY (`numParking`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `codeReservation` int NOT NULL AUTO_INCREMENT,
  `dateReservation` date DEFAULT (curdate()),
  `dateDepart` date NOT NULL,
  `dateRetour` date NOT NULL,
  `etat` varchar(20) DEFAULT 'Non validée',
  `codeClient` int DEFAULT NULL,
  `numImmatruculation` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`codeReservation`),
  KEY `codeClient` (`codeClient`),
  KEY `numImmatruculation` (`numImmatruculation`),
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`codeClient`) REFERENCES `client` (`codeClient`),
  CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`numImmatruculation`) REFERENCES `vehicule` (`numImmatruculation`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sanction`
--

DROP TABLE IF EXISTS `sanction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanction` (
  `numSanction` int NOT NULL AUTO_INCREMENT,
  `montant` double NOT NULL,
  `enCours` tinyint NOT NULL DEFAULT '1',
  `numContrat` int DEFAULT NULL,
  PRIMARY KEY (`numSanction`),
  KEY `numContrat` (`numContrat`),
  CONSTRAINT `sanction_ibfk_1` FOREIGN KEY (`numContrat`) REFERENCES `contrat` (`numContrat`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateur` (
  `identifiant` varchar(20) NOT NULL,
  `motDePasse` varchar(30) NOT NULL,
  `nomComplet` varchar(25) NOT NULL,
  `adresse` varchar(50) NOT NULL,
  `numGSM` varchar(15) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  PRIMARY KEY (`identifiant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vehicule`
--

DROP TABLE IF EXISTS `vehicule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicule` (
  `numImmatruculation` varchar(20) NOT NULL,
  `marque` varchar(20) NOT NULL,
  `modele` varchar(30) NOT NULL,
  `type` varchar(20) NOT NULL,
  `carburant` varchar(20) NOT NULL,
  `compteurKM` double NOT NULL,
  `dateMiseEnCirculation` date NOT NULL,
  `disponibilité` tinyint(1) NOT NULL,
  `image` longblob,
  `numParking` int DEFAULT NULL,
  PRIMARY KEY (`numImmatruculation`),
  KEY `numParking` (`numParking`),
  CONSTRAINT `vehicule_ibfk_1` FOREIGN KEY (`numParking`) REFERENCES `parking` (`numParking`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-12 23:06:59
