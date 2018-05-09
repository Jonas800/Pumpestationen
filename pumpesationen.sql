-- MySQL Script generated by MySQL Workbench
-- Tue May  8 10:04:57 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pumpestationen
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pumpestationen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pumpestationen` DEFAULT CHARACTER SET utf8 ;
USE `pumpestationen` ;

-- -----------------------------------------------------
-- Table `pumpestationen`.`activities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pumpestationen`.`activities` (
  `activity_id` INT(11) NOT NULL AUTO_INCREMENT,
  `activity_name` VARCHAR(45) NOT NULL,
  `activity_description` VARCHAR(300) NOT NULL,
  `activity_startDatetime` DATETIME NOT NULL,
  `activity_endDatetime` DATETIME NOT NULL,
  PRIMARY KEY (`activity_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pumpestationen`.`zipcodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pumpestationen`.`zipcodes` (
  `zipcode` INT(11) NOT NULL,
  `zipcode_city` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`zipcode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pumpestationen`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pumpestationen`.`employees` (
  `employee_id` INT(11) NOT NULL AUTO_INCREMENT,
  `employee_firstName` VARCHAR(12) NOT NULL,
  `employee_lastName` VARCHAR(12) NOT NULL,
  `employee_address` VARCHAR(30) NOT NULL,
  `employee_phone` VARCHAR(11) NOT NULL,
  `employee_cpr` VARCHAR(11) NOT NULL,
  `zipcodes_zipcode` INT(11) NOT NULL,
  PRIMARY KEY (`employee_id`),
  INDEX `fk_employees_zipcodes1_idx` (`zipcodes_zipcode` ASC),
  CONSTRAINT `fk_employees_zipcodes1`
    FOREIGN KEY (`zipcodes_zipcode`)
    REFERENCES `pumpestationen`.`zipcodes` (`zipcode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pumpestationen`.`jobs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pumpestationen`.`jobs` (
  `job_id` INT(11) NOT NULL AUTO_INCREMENT,
  `job_title` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`job_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pumpestationen`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pumpestationen`.`members` (
  `member_id` INT(11) NOT NULL AUTO_INCREMENT,
  `member_firstName` VARCHAR(12) NOT NULL,
  `member_lastName` VARCHAR(12) NOT NULL,
  `member_dateOfBirth` DATE NOT NULL,
  `member_cpr` VARCHAR(11) NOT NULL,
  `member_address` VARCHAR(30) NOT NULL,
  `zipcodes_zipcode` INT(11) NOT NULL,
  PRIMARY KEY (`member_id`),
  INDEX `fk_members_zipcodes_idx` (`zipcodes_zipcode` ASC),
  CONSTRAINT `fk_members_zipcodes`
    FOREIGN KEY (`zipcodes_zipcode`)
    REFERENCES `pumpestationen`.`zipcodes` (`zipcode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;