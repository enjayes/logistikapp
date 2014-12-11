

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+01:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `logistikdb`
--
CREATE DATABASE IF NOT EXISTS `logistikdb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `logistikdb`;


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
