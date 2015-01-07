-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 05. Jan 2015 um 15:44
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
('6cd04a06-ce88-409d-b833-2e1fffca14ab', 'e395386c-26cb-4a47-9584-e6df105655c7', 1),
('154942d1-e13e-48dd-a99a-17a28edbea7c', '1b95513e-447e-422d-9d82-0d97b1e168b6', 0),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', '87440edd-63d7-4cd1-93ee-18cb0008b47c', 1),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', '96eb1186-d77d-441a-9858-5027666d3086', 1),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', 'bddabe1d-20bf-44c6-bde5-ddc8eb2a23b0', 1),
('dbaa0473-d19d-40fc-a073-b31929b84453', '6b89f82f-3e77-4f98-81fc-179de6659d4d', 0),
('4bcd5f66-9fa9-4052-93b9-9ba084507031', '1f8e3465-3155-450e-80ca-474052dacb40', 0),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', '017a7ddb-51e9-4ca5-9d14-ea057215edf9', 1),
('4bcd5f66-9fa9-4052-93b9-9ba084507031', 'c3b91323-e737-46e0-b20c-3855814b85b5', 1),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', 'c3b91323-e737-46e0-b20c-3855814b85b5', 1),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', '028e3ef4-ae3f-42b2-96c5-c3707bd740f3', 1),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', 'ce82df19-6b13-4cad-8ae5-5ae346ce2dfc', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `adminnachrichtgesendet`
--

CREATE TABLE IF NOT EXISTS `adminnachrichtgesendet` (
  `id` varchar(36) NOT NULL,
  `datum` text NOT NULL,
  `nachricht` text NOT NULL,
  `maerkte` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `adminnachrichtgesendet`
--

INSERT INTO `adminnachrichtgesendet` (`id`, `datum`, `nachricht`, `maerkte`) VALUES
('017a7ddb-51e9-4ca5-9d14-ea057215edf9', '1419623547448', 'Hallo', '["0","1"]'),
('028e3ef4-ae3f-42b2-96c5-c3707bd740f3', '1419869303263', 'Test', '["0","1"]'),
('1b95513e-447e-422d-9d82-0d97b1e168b6', '1418568473246', 'Test', ''),
('1f8e3465-3155-450e-80ca-474052dacb40', '1419622262545', 'Das ist ein Max Mustermann test!', '["0","1"]'),
('6b89f82f-3e77-4f98-81fc-179de6659d4d', '1419537719000', 'erete3t', '["0","1"]'),
('87440edd-63d7-4cd1-93ee-18cb0008b47c', '1418568481467', 'sdfsdf', ''),
('96eb1186-d77d-441a-9858-5027666d3086', '1419536961817', 'dgdgdfg', ''),
('bddabe1d-20bf-44c6-bde5-ddc8eb2a23b0', '1419537029457', 'dfgdgfdg', ''),
('c3b91323-e737-46e0-b20c-3855814b85b5', '1419623648111', 'test', '["0","1"]'),
('ce82df19-6b13-4cad-8ae5-5ae346ce2dfc', '1419869465165', 'Nächster Termin am 01.02.2014 um 14:00', '["0","1"]'),
('e395386c-26cb-4a47-9584-e6df105655c7', '1418336152755', 'h e gergfg egergegr ööö', '');

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
  `gespraechspartner` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `jobs`
--

INSERT INTO `jobs` (`id`, `lieferanten_id`, `markt_id`, `timestamp_start`, `timestamp_end`, `fixtermin`, `pending`, `finished`, `checked_out`, `besuch`, `cb_auftrag_getaetigt`, `bestellung`, `verraeumung`, `austausch`, `t_ziel`, `t_grund`, `t_thematik`, `cb_mhd`, `cb_ruecknahme`, `cb_reklamation`, `cb_warenaufbau`, `cb_umbau`, `cb_info_gespraech`, `cb_nr_abgabe`, `t_vk_euro_abgabe`, `t_warengruppe`, `cb_verkostung`, `cb_sortimentsinfo`, `cb_aktionsabsprache`, `cb_bemusterung`, `cb_verlosung`, `t_notizen`, `template_name`, `gespraechspartner`) VALUES
('012a5a6d-532e-4c12-b384-e14ca018ce56', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 'Leonberg', 1420338380751, 1420339211743, 0, 0, 1, 1, 0, 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', ''),
('5e84ff4e-8970-45ea-86b3-371d3f0335b9', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 'Leonberg', 1420341881158, 1420341979470, 0, 0, 1, 1, 1, 0, 1, 1, 0, '', 'df', '', 0, 0, 1, 0, 0, 1, 0, 0, '', 0, 0, 0, 0, 0, '', '', ''),
('b389c113-237a-40bc-9014-b53478a1ad1a', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 'Sindelfingen', 1419944587214, 1419944668404, 0, 0, 1, 1, 0, 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', ''),
('c381d971-b22d-4ac7-8062-aad0a815506c', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 'Leonberg', 1420336651265, 1420336686504, 0, 0, 1, 1, 0, 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', 'sdfsdf'),
('dbde38c0-a21d-4d17-a7cc-770386fe05e0', '6cd04a06-ce88-409d-b833-2e1fffca14ab', 'Sindelfingen', 1419944244182, 1419944258344, 0, 0, 1, 1, 0, 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', '');

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
  `Firma` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Daten für Tabelle `lieferanten`
--

INSERT INTO `lieferanten` (`id`, `Pin`, `PinSHA`, `Vorname`, `Name`, `Telefon`, `Email`, `Adresse`, `Aufgaben`, `Notizen`, `Firma`) VALUES
('08af7639-7c29-4664-b130-dee39c3d333a', '0666', '17759c88ac7b8a673211c595ed185d3483a59e200791545521f8eff5611336d032909de737b725eecbc9e017a60432da535a86a13705a3f344a61a742bbd8aab', 'Unbenannt', 'Unbenannt', '', '', '', '', '', ''),
('154942d1-e13e-48dd-a99a-17a28edbea7c', '4837', 'c39292751ce6c0a7a0dc3f82d59f0c7260f2ad0250b5b00d3e27bf767c2600157ab5471cd154d61852450e22e82a89af316782ca24ea396dea684b11fefb9691', 'Unbenannt', 'Unbenannt', '', '', '', '', '', 'Test'),
('394788b9-41ff-458d-bb8e-8d1de37fc3f0', '0168', '695afa5628c76f99a9db33636f13df6c664788ef2b4e693ede190e4ab5d9c6181e72e4afe92774aaa6798ed54bc741b68661f4f5d9e954fde629aa1aecc9c3b9', 'Unbenannt', 'Unbenannt', 'Telefon', 'asdasd', 'Adresse', '', 'Notizen', 'Firma'),
('4bcd5f66-9fa9-4052-93b9-9ba084507031', '6584', 'e2f16ca521bc2c1b27ec9df6966acad00ab519046d39073bf4caea320261769c1be86ad4948282850defb26ab0a191f8d80c560b13aa0f928ceeef8fb47f9548', 'Max', 'Musterman', '', '', '', '', '', ''),
('6cd04a06-ce88-409d-b833-2e1fffca14ab', '8698', '3cad0f0150e97428421e5701d3d2ad66c5c7e7047c28e797cbe7fede90b992120a7917c2131fc677c9d839f03e6258a8e0174440089a2420e4d21ca5f2bb590b', 'Fritz', 'Müller', '', '', '', '', '', ''),
('ba1f32bd-7ab1-4148-a7ab-18dcf7397792', '2663', '172cfc7ab6a05b9de09c43c0769ab49d1a7fbb70512210f1b4b7d6450538ea9f707d32ec4b62eca8bdea5825fda2bbdeff455bcb10a4d259fee673e113e40329', 'Unbenannt', 'Unbenannt', '', '', '', '', '', ''),
('c7bd3019-222f-457d-99f9-b56a5cc1825f', '3467', '372d6bdc3c149e643de1dd4fd6e3a9db394f9c55a8d74b2e54bbf7b3899f352fe2f8e5ef4905f31f5fcda43ffb027545e338bab2cd65803e09add4f3721cadd9', 'Unbenannt', 'Unbenannt', '', '', '', '', '', ''),
('dbaa0473-d19d-40fc-a073-b31929b84453', '5501', 'c463604862db2ed2b9cd11f41b4777e3d1f229aef163f5edf4c4c61a1673a8c13e6755ca549930d2d8e65db2f0ed061509095204a5dd019d11d5c44195136727', 'Unbenannt', 'Unbenannt', '', '', '', '', '', ''),
('f91f6bd1-a52b-454f-bf66-f773be6b223b', '5077', '074830826c1a8a72da9e66261b451ea51da5e5b84a05091a8452d92f442e2f1d926c072127097a2babed8c27d7f24f24b3fd607975bc1957319d89441b8b3129', 'Unbenannt', 'Unbenannt', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lieferantnachrichtgesendet`
--

CREATE TABLE IF NOT EXISTS `lieferantnachrichtgesendet` (
  `id` varchar(36) NOT NULL,
  `lieferantid` varchar(36) NOT NULL,
  `read` tinyint(1) NOT NULL,
  `datum` text NOT NULL,
  `nachricht` text NOT NULL
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
  `telefon` text NOT NULL,
  `sms` tinyint(1) NOT NULL,
  `call` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `maerkte`
--

INSERT INTO `maerkte` (`id`, `name`, `telefon`, `sms`, `call`) VALUES
('0', 'Sindelfingen', '+4917696276213', 1, 1),
('1', 'Leonberg', '+4917696276213', 1, 1);

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

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adminnachrichtgesendet`
--
ALTER TABLE `adminnachrichtgesendet`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobs`
--
ALTER TABLE `jobs`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lieferanten`
--
ALTER TABLE `lieferanten`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lieferantnachrichtgesendet`
--
ALTER TABLE `lieferantnachrichtgesendet`
 ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
