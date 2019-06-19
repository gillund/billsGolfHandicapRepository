-- phpMyAdmin SQL Dump
-- version 3.4.3.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 20, 2011 at 01:29 PM
-- Server version: 5.5.12
-- PHP Version: 5.3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `explorecalifornia`
--
USE `playerHandicapDb`;

--
-- Drop tables if they already exist
--
DROP TABLE IF EXISTS `admin`;
DROP TABLE IF EXISTS `players`;
DROP TABLE IF EXISTS `courses`;
DROP TABLE IF EXISTS `round`;


-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`adminId`, `userName`, `password`) VALUES
(1, 'gillundb', 'Addison1g$');

-- --------------------------------------------------------

--
-- Table structure for table `explorers`
--
DROP TABLE IF EXISTS `players`;
CREATE TABLE IF NOT EXISTS `players` (
  `playerId` 		int(11) NOT NULL AUTO_INCREMENT,
  `name`	 		varchar(50) NOT NULL,
  `email` 			varchar(100) NOT NULL,
  `phonenumber` 	varchar(15) NOT NULL,
  `userid` 			varchar(50) NOT NULL,
  `password` 		varchar(20) NOT NULL,
   PRIMARY KEY (`playerId`)
) ENGINE=Innodb  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `explorers`
--

INSERT INTO `players` (`playerId`, `name`, `email`,`phonenumber`,`userid`,`password`) VALUES
(1, 'Bill Gillund', 'bill.gillund@gmail.com','708-666-7777','gillundb','addison1g'),
(2, 'Mike Gillund', 'mgillund@gillundsales.com','708-666-7777','gillundm','donna'),
(3, 'Dan Hathaway', 'hathaway@gmail.com','708-666-1212','hathawayd','candice'),
(4, 'Rich Graham', 'rgraham@gmail.com','708-666-3434','grahamr','deb'),
(5, 'Mark Woyna', 'mwayna@cboe.com','708-666-5656','woynam','single');

-- --------------------------------------------------------

--
-- Table structure for table `packages`
--
DROP TABLE IF EXISTS `courses`;
CREATE TABLE IF NOT EXISTS `courses` (
  `courseId` 	int(11) NOT NULL AUTO_INCREMENT,
  `coursename` 	varchar(80) NOT NULL,
  `slope`		int(4) NOT NULL,
  `rating` 		float(4) NOT NULL,
  `yardage` 	int(4) NOT NULL,
  `comment` 	varchar(30) NOT NULL,
  PRIMARY KEY (`courseId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `packages`
--

INSERT INTO `courses` (`courseId`, `coursename`, `slope`, `rating`,`yardage`,`comment`) VALUES
(1, 'IdleWild Country Club',136, 72.1,6400, 'My Home Course');

-- --------------------------------------------------------

--
-- Table structure for table `states`
--
DROP TABLE IF EXISTS `round`;
CREATE TABLE IF NOT EXISTS `round` (
  `roundId`		 	int(11) NOT NULL AUTO_INCREMENT,
  `playerId` 		int(11) NOT NULL,
  `rounddatetime` 	DATETIME NOT NULL,
  `player` 			varchar(50) NOT NULL,
  `course` 			varchar(50) NOT NULL,
  `score` 			int(4) NOT NULL,
  `slope` 			int(4) NOT NULL,
  `rating` 			float(4) NOT NULL,
  `delta` 			float(4) NOT NULL,
  PRIMARY KEY (`roundid`),
  INDEX (`player`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `states`
--

INSERT INTO `round` (`roundId`,`playerId`,`rounddatetime`, `player`, `course`, `score`, `slope`, `rating`, `delta` ) VALUES
(1,1,'2019-10-25 10:00:00', 'Bill Gillund', 'Idlewild Country Club',72,136,72.1,3),
(2,1,'2019-10-26 10:00:00', 'Bill Gillund', 'Idlewild Country Club',73,136,72.1,3),
(3,1,'2019-10-27 10:00:00', 'Bill Gillund', 'Idlewild Country Club',74,136,72.1,3),
(4,1,'2019-10-28 10:00:00', 'Bill Gillund', 'Idlewild Country Club',75,136,72.1,3),
(5,1,'2019-10-29 10:00:00', 'Bill Gillund', 'Idlewild Country Club',76,136,72.1,3),
(6,1,'2019-10-30 10:00:00', 'Bill Gillund', 'Idlewild Country Club',77,136,72.1,3),
(7,1,'2019-10-2 10:00:00', 'Bill Gillund', 'Idlewild Country Club',78,136,72.1,3),
(8,1,'2019-10-3 10:00:00', 'Bill Gillund', 'Idlewild Country Club',79,136,72.1,3),
(9,1,'2019-10-4 10:00:00', 'Bill Gillund', 'Idlewild Country Club',80,136,72.1,3),
(10,1,'2019-10-5 10:00:00', 'Bill Gillund', 'Idlewild Country Club',81,136,72.1,3),
(11,1,'2019-10-6 10:00:00', 'Bill Gillund', 'Idlewild Country Club',82,136,72.1,3);

-- --------------------------------------------------------


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
