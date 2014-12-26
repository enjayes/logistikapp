-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 13. Dez 2014 um 22:06
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

--
-- Daten für Tabelle `adminlieferantnachricht`
--

INSERT INTO `adminlieferantnachricht` (`lieferantid`, `nachrichtenid`, `read`) VALUES
('6cd04a06-ce88-409d-b833-2e1fffca14ab', 'e395386c-26cb-4a47-9584-e6df105655c7', 0);

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

--
-- Daten für Tabelle `adminnachrichtgesendet`
--

INSERT INTO `adminnachrichtgesendet` (`id`, `datum`, `nachricht`) VALUES
('e395386c-26cb-4a47-9584-e6df105655c7', '1418336152755', 'h e gergfg egergegr ööö');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `jobs`
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
  `t_vk_euro_abgabe` int(11) NOT NULL,
  `t_warengruppe` text NOT NULL,
  `cb_verkostung` tinyint(1) NOT NULL,
  `cb_sortimentsinfo` tinyint(1) NOT NULL,
  `cb_aktionsabsprache` tinyint(1) NOT NULL,
  `cb_bemusterung` tinyint(1) NOT NULL,
  `cb_verlosung` tinyint(1) NOT NULL,
  `t_notizen` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `jobs`
--

INSERT INTO `jobs` (`id`, `client_id`, `markt_id`, `timestamp_start`, `timestamp_end`, `fixtermin`, `pending`, `finished`, `checked_out`, `besuch`, `cb_auftrag_getaetigt`, `bestellung`, `verraeumung`, `austausch`, `t_ziel`, `t_grund`, `t_thematik`, `cb_mhd`, `cb_ruecknahme`, `cb_reklamation`, `cb_warenaufbau`, `cb_umbau`, `cb_info_gespraech`, `cb_nr_abgabe`, `t_vk_euro_abgabe`, `t_warengruppe`, `cb_verkostung`, `cb_sortimentsinfo`, `cb_aktionsabsprache`, `cb_bemusterung`, `cb_verlosung`, `t_notizen`) VALUES
('27ab9eca-40fd-43b6-94dd-133a36245635', '1', '', 1418332790285, 1418332933373, 0, 0, 1, 1, 1, 0, 0, 0, 0, '                ', '\n                ', '\n                ', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, ''),
('9ef58605-9549-469d-bca4-cd739ed98c95', '1', '', 1418333107991, 1418333114778, 0, 0, 1, 1, 0, 0, 1, 0, 0, '                ', '\n                ', '\n                ', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, ''),
('aa188824-e44d-42a5-9945-f1b5a03aeda0', '1', '', 1418333141408, 1418333148595, 0, 0, 1, 1, 0, 0, 1, 0, 0, '                ', '\n                ', '\n                ', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, ''),
('cb2e1f6b-3110-4ea1-8b28-b759f6d2b184', '1', '', 1418333121889, 1418333139451, 0, 0, 1, 1, 0, 0, 0, 1, 0, '                ', '\n                ', '\n                ', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, ''),
('d9a2e338-74eb-4ac4-b588-29f18d3fc6d4', '1', '', 1418335582872, 1418336419554, 0, 0, 1, 1, 0, 0, 0, 0, 1, '                ', '\n                ', '\n                ', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lieferanten`
--

CREATE TABLE IF NOT EXISTS `lieferanten` (
  `id` varchar(36) NOT NULL,
  `Pin` text NOT NULL,
  `PinSHA` text NOT NULL,
  `Vorname` text NOT NULL,
  `Name` text NOT NULL,
  `Telefon` text NOT NULL,
  `Email` text NOT NULL,
  `Adresse` text NOT NULL,
  `Aufgaben` text NOT NULL,
  `Notizen` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Daten für Tabelle `lieferanten`
--

INSERT INTO `lieferanten` (`id`, `Pin`, `PinSHA`, `Vorname`, `Name`, `Telefon`, `Email`, `Adresse`, `Aufgaben`, `Notizen`) VALUES
('154942d1-e13e-48dd-a99a-17a28edbea7c', '4837', 'c39292751ce6c0a7a0dc3f82d59f0c7260f2ad0250b5b00d3e27bf767c2600157ab5471cd154d61852450e22e82a89af316782ca24ea396dea684b11fefb9691', 'Unbenannt', 'Unbenannt', '', '', '', '', ''),
('394788b9-41ff-458d-bb8e-8d1de37fc3f0', '0168', '695afa5628c76f99a9db33636f13df6c664788ef2b4e693ede190e4ab5d9c6181e72e4afe92774aaa6798ed54bc741b68661f4f5d9e954fde629aa1aecc9c3b9', 'Unbenannt', 'Unbenannt', '', '', '', '', ''),
('4bcd5f66-9fa9-4052-93b9-9ba084507031', '6584', 'e2f16ca521bc2c1b27ec9df6966acad00ab519046d39073bf4caea320261769c1be86ad4948282850defb26ab0a191f8d80c560b13aa0f928ceeef8fb47f9548', 'Unbenannt', 'Unbenannt', '', '', '', '', ''),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', '8698', '3cad0f0150e97428421e5701d3d2ad66c5c7e7047c28e797cbe7fede90b992120a7917c2131fc677c9d839f03e6258a8e0174440089a2420e4d21ca5f2bb590b', 'Fritz', 'Müller', '', '', '', '', ''),
('ba1f32bd-7ab1-4148-a7ab-18dcf7397792', '2663', '172cfc7ab6a05b9de09c43c0769ab49d1a7fbb70512210f1b4b7d6450538ea9f707d32ec4b62eca8bdea5825fda2bbdeff455bcb10a4d259fee673e113e40329', 'Unbenannt', 'Unbenannt', '', '', '', '', ''),
('c7bd3019-222f-457d-99f9-b56a5cc1825f', '3467', '372d6bdc3c149e643de1dd4fd6e3a9db394f9c55a8d74b2e54bbf7b3899f352fe2f8e5ef4905f31f5fcda43ffb027545e338bab2cd65803e09add4f3721cadd9', 'Unbenannt', 'Unbenannt', '', '', '', '', ''),
('dbaa0473-d19d-40fc-a073-b31929b84453', '5501', 'c463604862db2ed2b9cd11f41b4777e3d1f229aef163f5edf4c4c61a1673a8c13e6755ca549930d2d8e65db2f0ed061509095204a5dd019d11d5c44195136727', 'Unbenannt', 'Unbenannt', '', '', '', '', '');

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

--
-- Daten für Tabelle `lieferantnachrichtgesendet`
--

INSERT INTO `lieferantnachrichtgesendet` (`id`, `lieferantid`, `read`, `datum`, `nachricht`) VALUES
('97ddd2d7-7954-11e4-9ac9-3c77e60d15e2', 'f374a491-879a-4b4f-afff-ef20b67f3d5f', 1, '1970-01-01T00:00:00.000Z', 'werwrwerwerwrwer weerwer');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `maerkte`
--

CREATE TABLE IF NOT EXISTS `maerkte` (
  `id` varchar(36) NOT NULL,
  `name` text NOT NULL,
  `telefon` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `maerkte`
--

INSERT INTO `maerkte` (`id`, `name`, `telefon`) VALUES
('0', 'Sindelfingen', '08961257833333333'),
('1', 'Leonberg', '353543535t45');

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
  `RepeatDays` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `termine`
--

INSERT INTO `termine` (`id`, `Title`, `Start`, `End`, `AllDay`, `Notizen`, `Lieferant`) VALUES
('8d07d3f6-38c2-4ce7-a369-4f28a76cfc49', 'Unbenannt', '2014-11-13', '', 1, '', '229ea0af-a9ba-415c-8334-04a451c805fd'),
('c78bc7c7-f5cf-413e-9207-84399ceca5ba', 'tutututurp', '2014-11-27', '', 1, '', 'dc643b27-669a-4ff5-b7ae-3a54d1539050'),
('8462e1f4-2a38-40ac-bab7-341be2134e20', 'Abendessen Mama', '2014-11-15T02:00:00', '2014-11-15T07:30:00', 0, '', '229ea0af-a9ba-415c-8334-04a451c805fd'),
('d39876bb-ab2f-47c2-abb5-75e8cdce6b9b', 'dddd', '2014-11-25T10:00:00', '', 0, 'Test', 'dc643b27-669a-4ff5-b7ae-3a54d1539050'),
('9b7de2a8-acd4-49ed-a572-f03ad5440ece', 'dddddddddddd', '2014-11-05T10:00:00', '2014-11-05T12:30:00', 0, '', 'dc643b27-669a-4ff5-b7ae-3a54d1539050'),
('daec6471-0f14-4053-820c-5c232fec22cc', 'Unbenannt', '2014-11-18T12:00:00+01:00', '', 0, '', '229ea0af-a9ba-415c-8334-04a451c805fd'),
('26fe5dff-38a4-46e5-8ab7-a3d4014216b5', 'sdfsf', '2014-10-31', '', 1, '', ''),
('05c6b78b-345f-4d8d-b92f-0409277bfef0', 'Unbenannt', '2014-11-20', '', 1, '', 'dc643b27-669a-4ff5-b7ae-3a54d1539050'),
('582030dc-0fcb-4c40-afc3-4055f0b81fb7', 'Unbenannt', '2014-12-19T02:00:00', '', 0, '', 'f374a491-879a-4b4f-afff-ef20b67f3d5f'),
('be2c5af4-ab27-4a06-abe0-a74b51d0a8bf', 'Unbenannt', '2014-11-21', '', 1, '', '229ea0af-a9ba-415c-8334-04a451c805fd'),
('ba0da3c4-db29-47c6-82a5-52758707cd94', 'Unbenannt', '2014-11-29', '', 1, '', '229ea0af-a9ba-415c-8334-04a451c805fd'),
('62d0c9eb-27a1-4859-904a-3002a6a0daa4', 'Unbenannt', '2014-11-05T00:00:00+01:00', '', 0, '', ''),
('3767a74d-c7ec-4bee-9799-01de1e0e6ec3', 'Unbenannt', '2014-12-28T15:10:00', '', 0, '', '24dd0940-c51a-4b1b-b3af-5e2422681581'),
('52edcc6b-87bc-43d1-bf06-e37abe215f46', 'Unbenannt', '2014-12-24', '', 1, '', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
