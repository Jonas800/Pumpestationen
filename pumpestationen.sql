CREATE DATABASE  IF NOT EXISTS `pumpestationen` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pumpestationen`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: pumpestationen
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activities`
--

DROP TABLE IF EXISTS `activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activities` (
  `activity_id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) NOT NULL,
  `activity_description` varchar(300) NOT NULL,
  `activity_startDate` date NOT NULL,
  `activity_endDate` date NOT NULL,
  `activity_startTime` time NOT NULL,
  `activity_endTime` time NOT NULL,
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activities`
--

LOCK TABLES `activities` WRITE;
/*!40000 ALTER TABLE `activities` DISABLE KEYS */;
INSERT INTO `activities` VALUES (2,'Skakturnening','Skakturnering for medlemmer under 10','2014-02-14','2017-02-13','18:04:00','06:43:00'),(3,'Fodbold','Fodboldturnening','2018-05-18','2018-05-18','19:56:00','22:56:00');
/*!40000 ALTER TABLE `activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_firstName` varchar(12) NOT NULL,
  `employee_lastName` varchar(12) NOT NULL,
  `employee_address` varchar(30) NOT NULL,
  `employee_phone` varchar(11) NOT NULL,
  `employee_cpr` varchar(11) NOT NULL,
  `zipcodes_zipcode` int(11) NOT NULL,
  `employee_jobPosition` varchar(30) NOT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `fk_employees_zipcodes1_idx` (`zipcodes_zipcode`),
  CONSTRAINT `fk_employees_zipcodes1` FOREIGN KEY (`zipcodes_zipcode`) REFERENCES `zipcodes` (`zipcode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Jonas','Olsen','Pulsen 8','45456480','34093493-2',4000,'GUD'),(2,'Kim','Kimsen','Frederikssundvej 213','40909090','210902-1111',4000,'Vagt'),(8,'Jakob','Hector','NØRREBRONX','50392090','200985-5929',2200,'Souschef');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobs`
--

DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobs` (
  `job_id` int(11) NOT NULL AUTO_INCREMENT,
  `job_title` varchar(15) NOT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobs`
--

LOCK TABLES `jobs` WRITE;
/*!40000 ALTER TABLE `jobs` DISABLE KEYS */;
INSERT INTO `jobs` VALUES (13,'kok'),(14,'kok2'),(16,'kfgkf'),(17,'hghgb'),(18,'jgjdfg');
/*!40000 ALTER TABLE `jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `members` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_firstName` varchar(12) NOT NULL,
  `member_lastName` varchar(12) NOT NULL,
  `member_dateOfBirth` date NOT NULL,
  `member_cpr` varchar(11) NOT NULL,
  `member_address` varchar(30) NOT NULL,
  `zipcodes_zipcode` int(11) NOT NULL,
  PRIMARY KEY (`member_id`),
  KEY `fk_members_zipcodes_idx` (`zipcodes_zipcode`),
  CONSTRAINT `fk_members_zipcodes` FOREIGN KEY (`zipcodes_zipcode`) REFERENCES `zipcodes` (`zipcode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1,'Donald','Duck','1997-03-16','160397-1111','AndersVej 13',3600),(6,'Harforældre','Jensen','2002-05-24','210902-1111','Lygten 18',2200),(7,'Hans','Hansen','2009-05-24','123456-9999','Hansenvej',2200);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizers`
--

DROP TABLE IF EXISTS `organizers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organizers` (
  `organizer_id` int(11) NOT NULL AUTO_INCREMENT,
  `activities_activity_id` int(11) NOT NULL,
  `employees_employee_id` int(11) NOT NULL,
  PRIMARY KEY (`organizer_id`,`activities_activity_id`,`employees_employee_id`),
  KEY `fk_activities_has_employees_employees1_idx` (`employees_employee_id`),
  KEY `fk_activities_has_employees_activities1_idx` (`activities_activity_id`),
  CONSTRAINT `fk_activities_has_employees_activities1` FOREIGN KEY (`activities_activity_id`) REFERENCES `activities` (`activity_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_activities_has_employees_employees1` FOREIGN KEY (`employees_employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizers`
--

LOCK TABLES `organizers` WRITE;
/*!40000 ALTER TABLE `organizers` DISABLE KEYS */;
INSERT INTO `organizers` VALUES (2,3,1),(1,3,2);
/*!40000 ALTER TABLE `organizers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parents`
--

DROP TABLE IF EXISTS `parents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parents` (
  `parent_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_firstName` varchar(12) NOT NULL,
  `parent_lastName` varchar(12) NOT NULL,
  `parent_address` varchar(30) NOT NULL,
  `parent_phone` varchar(11) NOT NULL,
  `zipcodes_zipcode` int(11) NOT NULL,
  `members_member_id` int(11) NOT NULL,
  PRIMARY KEY (`parent_id`,`members_member_id`),
  KEY `fk_parents_zipcodes_idx` (`zipcodes_zipcode`),
  KEY `fk_parents_members1_idx` (`members_member_id`),
  CONSTRAINT `fk_parents_members1` FOREIGN KEY (`members_member_id`) REFERENCES `members` (`member_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_parents_zipcodes` FOREIGN KEY (`zipcodes_zipcode`) REFERENCES `zipcodes` (`zipcode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parents`
--

LOCK TABLES `parents` WRITE;
/*!40000 ALTER TABLE `parents` DISABLE KEYS */;
INSERT INTO `parents` VALUES (2,'Hans','Senior','Hansenvej','5609090',4000,6),(3,'Bente','Bentesen','Hansenvej','5609090',4000,6);
/*!40000 ALTER TABLE `parents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participants`
--

DROP TABLE IF EXISTS `participants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participants` (
  `participant_id` int(11) NOT NULL AUTO_INCREMENT,
  `activities_activity_id` int(11) NOT NULL,
  `members_member_id` int(11) NOT NULL,
  PRIMARY KEY (`participant_id`,`activities_activity_id`,`members_member_id`),
  KEY `fk_activities_has_members_members1_idx` (`members_member_id`),
  KEY `fk_activities_has_members_activities1_idx` (`activities_activity_id`),
  CONSTRAINT `fk_activities_has_members_activities1` FOREIGN KEY (`activities_activity_id`) REFERENCES `activities` (`activity_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_activities_has_members_members1` FOREIGN KEY (`members_member_id`) REFERENCES `members` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participants`
--

LOCK TABLES `participants` WRITE;
/*!40000 ALTER TABLE `participants` DISABLE KEYS */;
INSERT INTO `participants` VALUES (1,2,6),(2,2,7);
/*!40000 ALTER TABLE `participants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(20) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `employees_employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `employees_employee_id_UNIQUE` (`employees_employee_id`),
  KEY `fk_users_employee_id_idx` (`employees_employee_id`),
  CONSTRAINT `fk_users_employee_id` FOREIGN KEY (`employees_employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@admin.com','1000:e07ea5d679f17564bbc9d7252cfe390c:41bd28057cc61d81ebc36ffbacfca02f7d1498a4916185fa73e907d6063b629b01d6833d2868301f503b842b887b365ebf4a5d8fff6257511c9cb0d2d124fdbb',NULL),(2,'jonas@jonas.com','1000:81dc10cf38d5cbe49c20ce088b1892b6:8b312e9312ff93e97abb1c6a18f1896da652b1b50a8f57e5afa43a78e333649179c5fced330b6440ecf08615d79d20cf8f069be3c5ed06772fee78a58f41836d',NULL),(3,'benne@benne.com','1000:008908a340ffb9c5e5e397bb94db92fe:17c00cc00fd13263513105a4df7cbb37f9d9004d372007a90350ac6f5f6ca3c075b4605f0c88a3d16a24e53ed041462973a7686f8cb9875581c02c338b7172b5',NULL),(4,'jonas@jonas.com','1000:04d1b03f3d7d072fcad8523860cb7429:2bb4347630f1c840bd728e4ecb08b72882fc51542517545f10a8a96b7754d60489092c7e143a1d36aa3d6897fbdc648800e78629b3cbcc44d63c9b648406ee12',1),(5,'kim@kim.com','1000:054629dbbc2e339ca1bcb2d248120f06:0d4716be433d93183c3d4a8e11f5cf27b5810d07f93d3d393947650e5d8734975c532350fe8dc6178ea2c9fb68e28971744714b68219abb7da0c8c517d1566c1',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zipcodes`
--

DROP TABLE IF EXISTS `zipcodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zipcodes` (
  `zipcode` int(11) NOT NULL,
  `zipcode_city` varchar(20) NOT NULL,
  PRIMARY KEY (`zipcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zipcodes`
--

LOCK TABLES `zipcodes` WRITE;
/*!40000 ALTER TABLE `zipcodes` DISABLE KEYS */;
INSERT INTO `zipcodes` VALUES (2200,'København N'),(3310,'Ølsted'),(3600,'Frederikssund'),(4000,'Roskilde');
/*!40000 ALTER TABLE `zipcodes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-24 15:20:28
