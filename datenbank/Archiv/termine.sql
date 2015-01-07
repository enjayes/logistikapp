-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 05. Jan 2015 um 15:41
-- Server Version: 5.6.20
-- PHP-Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `logistikdb`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `termine`
--

CREATE TABLE IF NOT EXISTS `termine` (
  `id` varchar(36) NOT NULL,
  `Title` text NOT NULL,
  `Start` text NOT NULL,
  `StartMilli` bigint(20) NOT NULL,
  `End` text NOT NULL,
  `EndMilli` bigint(20) NOT NULL,
  `AllDay` tinyint(1) NOT NULL,
  `Notizen` text NOT NULL,
  `Lieferant` varchar(36) NOT NULL,
  `RepeatDays` int(11) NOT NULL,
  `jobId` varchar(36) NOT NULL,
  `marktId` varchar(36) NOT NULL,
  `alarm` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `termine`
--

INSERT INTO `termine` (`id`, `Title`, `Start`, `StartMilli`, `End`, `EndMilli`, `AllDay`, `Notizen`, `Lieferant`, `RepeatDays`, `jobId`, `marktId`, `alarm`) VALUES
('241b6b15-37d8-4e28-a0f3-1bbfbf0a3160', 'Unbenannt', '2014-12-06', 1417824000000, '', 0, 1, '', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 7, '', '1', 0),
('8282dd45-b18b-475f-8d00-98472b3f425e', 'Unbenannt', '2015-01-07', 1420588800000, '', 0, 1, '', '08af7639-7c29-4664-b130-dee39c3d333a', 15, '', '0', 0),
('5f638b50-4820-4085-bc79-98bd8e15cc92', 'Unbenannt', '2014-12-13', 1418428800000, '', 0, 1, '', '394788b9-41ff-458d-bb8e-8d1de37fc3f0', 0, '', '', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
