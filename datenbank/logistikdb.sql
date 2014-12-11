-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 06. Dez 2014 um 21:58
-- Server Version: 5.5.32
-- PHP-Version: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `logistikdb`
--
CREATE DATABASE IF NOT EXISTS `logistikdb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `logistikdb`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `adminlieferantnachricht`
--

CREATE TABLE IF NOT EXISTS `adminlieferantnachricht` (
  `lieferantid` varchar(36) NOT NULL,
  `nachrichtenid` varchar(36) NOT NULL,
  `read` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `adminnachrichtgesendet`
--

CREATE TABLE IF NOT EXISTS `adminnachrichtgesendet` (
  `id` varchar(36) NOT NULL,
  `datum` text NOT NULL,
  `nachricht` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lieferanten`
--

CREATE TABLE IF NOT EXISTS `lieferanten` (
  `id` varchar(36) NOT NULL,
  `Pin` text NOT NULL,
  `Vorname` text NOT NULL,
  `Name` text NOT NULL,
  `Telefon` text NOT NULL,
  `Email` text NOT NULL,
  `Adresse` text NOT NULL,
  `Aufgaben` text NOT NULL,
  `Notizen` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lieferantnachrichtgesendet`
--

CREATE TABLE IF NOT EXISTS `lieferantnachrichtgesendet` (
  `id` varchar(36) NOT NULL,
  `lieferantid` varchar(36) NOT NULL,
  `read` tinyint(1) NOT NULL,
  `datum` text NOT NULL,
  `nachricht` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `termine`
--

CREATE TABLE IF NOT EXISTS `termine` (
  `id` varchar(36) NOT NULL,
  `Title` text NOT NULL,
  `Start` text NOT NULL,
  `End` text NOT NULL,
  `AllDay` tinyint(1) NOT NULL,
  `Notizen` text NOT NULL,
  `Lieferant` varchar(36) NOT NULL,
  `BesucherrhythmusTage` int NOT NULL,
  `jobid` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Tabellenstruktur für Tabelle `Job`
--

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` varchar(36) NOT NULL,
  `client_id` varchar(36) NOT NULL,
  `markt_id` text NOT NULL,
  `timestamp_start` bigint(22) NOT NULL,
  `timestamp_end` bigint(22) NOT NULL,
  `fixtermin` tinyint(1) NOT NULL,
  `pending` tinyint(1) NOT NULL,
  `finished` tinyint(1) NOT NULL,
  `checked_out` tinyint(1) NOT NULL,
  `besuch` tinyint(1) NOT NULL,
  `cb_auftrag_getaetigt` tinyint(1) NOT NULL,
  `bestellung` tinyint(1) NOT NULL,
  `verraeumung` tinyint(1) NOT NULL,
  `austausch` tinyint(1) NOT NULL,
  `t_ziel` text NOT NULL,
  `t_grund` text NOT NULL,
  `t_thematik` text NOT NULL,
  `cb_mhd` tinyint(1) NOT NULL,
  `cb_ruecknahme` tinyint(1) NOT NULL,
  `cb_reklamation` tinyint(1) NOT NULL,
  `cb_warenaufbau` tinyint(1) NOT NULL,
  `cb_umbau` tinyint(1) NOT NULL,
  `cb_info_gespraech` tinyint(1) NOT NULL,
  `cb_nr_abgabe` tinyint(1) NOT NULL,
  `t_vk_euro_abgabe` int NOT NULL,
  `t_warengruppe` text NOT NULL,
  `cb_verkostung` tinyint(1) NOT NULL,
  `cb_sortimentsinfo` tinyint(1) NOT NULL,
  `cb_aktionsabsprache` tinyint(1) NOT NULL,
  `cb_bemusterung` tinyint(1) NOT NULL,
  `cb_verlosung` tinyint(1) NOT NULL,
  `t_notizen` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
