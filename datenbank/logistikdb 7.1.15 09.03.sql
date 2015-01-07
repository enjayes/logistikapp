-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 07. Jan 2015 um 09:03
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
('96d520e2-b4a1-4c0f-8256-880ea47be8d6', '86a3f487-fdd5-4e88-ace3-edae93568590', 0),
('ba1f32bd-7ab1-4148-a7ab-18dcf7397792', '46abc3ef-a1d6-44cf-bab1-777fe51374e9', 0),
('dbaa0473-d19d-40fc-a073-b31929b84453', '4f4effb8-4cb4-4668-a8f3-e5d81d131ee2', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `adminnachrichtgesendet`
--

CREATE TABLE IF NOT EXISTS `adminnachrichtgesendet` (
  `id` varchar(36) NOT NULL,
  `datum` text NOT NULL,
  `nachricht` text NOT NULL,
  `maerkte` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `adminnachrichtgesendet`
--

INSERT INTO `adminnachrichtgesendet` (`id`, `datum`, `nachricht`, `maerkte`) VALUES
('46abc3ef-a1d6-44cf-bab1-777fe51374e9', '1420605307568', 'N2', '["0","1"]'),
('4f4effb8-4cb4-4668-a8f3-e5d81d131ee2', '1420605311434', 'N3', '["0","1"]'),
('86a3f487-fdd5-4e88-ace3-edae93568590', '1420605303224', 'N1', '["0","1"]');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `jobs`
--

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` varchar(36) NOT NULL,
  `lieferanten_id` varchar(36) NOT NULL,
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
  `template_name` text NOT NULL,
  `gespraechspartner` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `jobs`
--

INSERT INTO `jobs` (`id`, `lieferanten_id`, `markt_id`, `timestamp_start`, `timestamp_end`, `fixtermin`, `pending`, `finished`, `checked_out`, `besuch`, `cb_auftrag_getaetigt`, `bestellung`, `verraeumung`, `austausch`, `t_ziel`, `t_grund`, `t_thematik`, `cb_mhd`, `cb_ruecknahme`, `cb_reklamation`, `cb_warenaufbau`, `cb_umbau`, `cb_info_gespraech`, `cb_nr_abgabe`, `t_vk_euro_abgabe`, `t_warengruppe`, `cb_verkostung`, `cb_sortimentsinfo`, `cb_aktionsabsprache`, `cb_bemusterung`, `cb_verlosung`, `t_notizen`, `template_name`, `gespraechspartner`) VALUES
('1984e39c-7074-4f11-ab29-d4b8b5b8b262', 'dbaa0473-d19d-40fc-a073-b31929b84453', 'Sindelfingen', 1420617715219, 1420617738807, 1, 0, 1, 1, 0, 1, 0, 0, 0, 'sdfsdf', '', '', 0, 0, 0, 0, 0, 0, 1, 0, '', 0, 0, 0, 0, 0, '', '', 'sdfsdf'),
('2e734d76-12cf-4cb9-829c-33f776a20076', 'dbaa0473-d19d-40fc-a073-b31929b84453', 'Sindelfingen', 1420615643499, 1420615670178, 0, 0, 1, 1, 1, 0, 0, 0, 0, 'zdgdgsg', 'gesfsdf', 'tsdsdg', 0, 0, 0, 1, 0, 0, 1, 0, '', 0, 1, 0, 0, 0, '', '', 'gsdfsdf'),
('3776a05f-d811-436a-8121-e83cdd13dd6f', 'dbaa0473-d19d-40fc-a073-b31929b84453', 'Sindelfingen', 1420617286572, 1420617309107, 0, 0, 1, 1, 1, 0, 0, 0, 0, 'zsdgdg', '', '', 0, 1, 0, 0, 0, 0, 1, 0, '', 0, 0, 0, 0, 0, '', '', 'gsdddsdf'),
('431e5b1d-7636-47d3-81e1-6a80b388c6df', 'dbaa0473-d19d-40fc-a073-b31929b84453', 'Sindelfingen', 1420616030201, 1420616055627, 0, 0, 1, 1, 1, 0, 0, 0, 0, 'qwerh', 'er', 'ert', 0, 0, 0, 0, 0, 0, 1, 0, '', 1, 0, 0, 0, 0, '', '', 'er'),
('50fac1cd-b2ef-4224-a78a-81cd8cb61fcb', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 'Sindelfingen', 1420443842073, 1420444058802, 0, 0, 1, 1, 1, 1, 0, 0, 0, 'Absprache, Planung', 'Änderungen Sortiment', 'Fruchtgetränke', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', 'gpartner'),
('a6179760-f72a-4ce5-ba30-4f6711280bca', 'dbaa0473-d19d-40fc-a073-b31929b84453', 'Sindelfingen', 1420615582397, 1420615618948, 0, 0, 1, 1, 0, 1, 0, 0, 0, 'zsgfdgjh', 'gsdfghjh', 'tasfdgh', 0, 0, 0, 0, 0, 0, 0, 500, '', 0, 1, 0, 0, 0, '', '', 'gasxfgdg'),
('cb25efb9-4428-4037-893c-9c8e378052b5', 'dbaa0473-d19d-40fc-a073-b31929b84453', 'Sindelfingen', 1420617589921, 1420617615067, 0, 0, 1, 1, 1, 1, 0, 1, 0, 'adsfdghgjghkj', '', 'afsgfdhgj', 0, 0, 0, 0, 0, 0, 1, 0, '', 0, 0, 0, 0, 0, '', '', 'asfdg'),
('dbde38c0-a21d-4d17-a7cc-770386fe05e0', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 'Sindelfingen', 1419944244182, 1419944258344, 0, 0, 1, 1, 1, 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', '');

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
  `Firma` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Daten für Tabelle `lieferanten`
--

INSERT INTO `lieferanten` (`id`, `Pin`, `PinSHA`, `Vorname`, `Name`, `Telefon`, `Email`, `Adresse`, `Aufgaben`, `Notizen`, `Firma`) VALUES
('6cd04a06-ce88-409d-b833-2e1fffca14ab', '8698', '3cad0f0150e97428421e5701d3d2ad66c5c7e7047c28e797cbe7fede90b992120a7917c2131fc677c9d839f03e6258a8e0174440089a2420e4d21ca5f2bb590b', 'Fritz', 'Müller', '', 'sdfsdf', '', '', '', 'Müllersche GmbH'),
('96d520e2-b4a1-4c0f-8256-880ea47be8d6', '2054', 'e4ece5d221504860a2f3735b4572584f93400a5b680a0feaa66ddf135676b4787f0ff5a181353053be7f651d607d5ea5985d733b6d37e20a8fc39e278c6b0b4a', 'Peter', 'Falkner', '', '', '', '', '', ''),
('ba1f32bd-7ab1-4148-a7ab-18dcf7397792', '2663', '172cfc7ab6a05b9de09c43c0769ab49d1a7fbb70512210f1b4b7d6450538ea9f707d32ec4b62eca8bdea5825fda2bbdeff455bcb10a4d259fee673e113e40329', 'Rolf', 'Petersen', '', '', '', '', '', ''),
('dbaa0473-d19d-40fc-a073-b31929b84453', '5501', 'c463604862db2ed2b9cd11f41b4777e3d1f229aef163f5edf4c4c61a1673a8c13e6755ca549930d2d8e65db2f0ed061509095204a5dd019d11d5c44195136727', 'Helmut', 'Wiegant', '01763456832', '', '', '', '', 'Getränke Wiegant');

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
('0b86ce49-9011-4f2f-bf69-2ac7ea5bc30a', '', 0, '1420616833558', 'Alarm: Der Termin "sfsdfsdfsdf" wurde nicht wahrgenommen!'),
('28b89839-d409-4112-9685-b2c382cfc589', 'ba1f32bd-7ab1-4148-a7ab-18dcf7397792', 1, '2015-01-07T05:03:27.648Z', 'Alarm: Der Termin "Anlieferung Gemüse" wurde nicht wahrgenommen!'),
('cbb8bdf7-22e1-4c9c-a18e-99e15f785f01', '96d520e2-b4a1-4c0f-8256-880ea47be8d6', 1, '2015-01-07T05:02:45.545Z', 'Alarm: Der Termin "Austausch" wurde nicht wahrgenommen!');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `maerkte`
--

CREATE TABLE IF NOT EXISTS `maerkte` (
  `id` varchar(36) NOT NULL,
  `name` text NOT NULL,
  `telefon` text NOT NULL,
  `sms` tinyint(1) NOT NULL,
  `call` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `maerkte`
--

INSERT INTO `maerkte` (`id`, `name`, `telefon`, `sms`, `call`) VALUES
('0', 'Sindelfingen', '+4917696276213', 0, 0),
('1', 'Leonberg', '414557864', 0, 0);

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
  `StartingMilli` bigint(20) NOT NULL,
  `AllDay` tinyint(1) NOT NULL,
  `Notizen` text NOT NULL,
  `Lieferant` varchar(36) NOT NULL,
  `RepeatDays` int(11) NOT NULL,
  `jobId` varchar(36) NOT NULL,
  `marktId` varchar(36) NOT NULL,
  `alarm` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `termine`
--

INSERT INTO `termine` (`id`, `Title`, `Start`, `StartMilli`, `End`, `EndMilli`, `StartingMilli`, `AllDay`, `Notizen`, `Lieferant`, `RepeatDays`, `jobId`, `marktId`, `alarm`) VALUES
('05a2e295-3676-4af8-90a3-ee795623a93b', 'Unbenannt', '2014-12-11', 1418256000000, '', 0, 0, 1, '', '', 0, '', '0', 0),
('241b6b15-37d8-4e28-a0f3-1bbfbf0a3160', 'Bio', '2015-01-08', 1420675200000, '', 0, 0, 1, '', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 7, '', '0', 0),
('7ee8c559-ba6a-4b47-8e5b-892006fd3a55', 'Anlieferung Gemüse', '2015-01-02', 1420156800000, '', 0, 0, 1, '', 'ba1f32bd-7ab1-4148-a7ab-18dcf7397792', 0, '', '0', 1),
('8282dd45-b18b-475f-8d00-98472b3f425e', 'Getränke  Müller', '2015-01-17T09:00:00+08:00', 1421456400000, '', 0, 0, 0, '', 'ba1f32bd-7ab1-4148-a7ab-18dcf7397792', 15, '', '0', 0),
('a048026c-7581-4f31-8086-20f296135a6c', 'Test', '2014-12-18', 1418860800000, '', 0, 0, 1, '', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 0, '50fac1cd-b2ef-4224-a78a-81cd8cb61fcb', '0', 1),
('b9055f3b-a91d-426f-88ad-4d149d7f409d', 'TestSingle', '2015-01-07T00:00:00+00:00', 1420588800000, '', 0, 0, 1, '', 'dbaa0473-d19d-40fc-a073-b31929b84453', 0, '1984e39c-7074-4f11-ab29-d4b8b5b8b262', '0', 0),
('d6366c15-e3f7-4e74-9d27-379b675e0e38', 'gdfgdgfdg', '2015-01-16T00:00:00+08:00', 1421337600000, '', 0, 0, 0, 'dfgdfgdfg', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 0, '', '', 0),
('e40d6e34-b43b-4160-885d-e7672b67d7ca', 'TestSingle', '2015-01-14', 1421193600000, '', 0, 1420588800000, 1, '', 'dbaa0473-d19d-40fc-a073-b31929b84453', 7, '', '0', 0),
('eb82214d-290f-444f-90ca-e74c632cf245', 'sfsdfsdfsdf', '2015-01-05', 1420416000000, '', 0, 0, 1, '', '', 0, '', '0', 1),
('ff856ed4-0d8c-4fd5-9a79-539f643a639d', 'Austausch', '2015-01-03', 1420243200000, '', 0, 0, 1, '', '96d520e2-b4a1-4c0f-8256-880ea47be8d6', 0, '50fac1cd-b2ef-4224-a78a-81cd8cb61fcb', '1', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
