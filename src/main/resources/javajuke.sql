-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 09 jan 2019 om 12:51
-- Serverversie: 10.1.26-MariaDB
-- PHP-versie: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `javajuke`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Playlist`
--

CREATE TABLE `Playlist` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Gegevens worden geëxporteerd voor tabel `Playlist`
--

INSERT INTO `Playlist` (`id`, `user_id`, `name`) VALUES
(1, 1, 'Playlist 1'),
(2, 1, 'Playlist 2');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `playlist_track`
--

CREATE TABLE `Playlist_track` (
  `playlist_id` int(11) NOT NULL,
  `track_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Gegevens worden geëxporteerd voor tabel `Playlist_track`
--

INSERT INTO `Playlist_track` (`playlist_id`, `track_id`) VALUES
(1, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `track`
--

CREATE TABLE `Track` (
  `id` int(11) NOT NULL,
  `path` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `artist` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `duration` bigint(20) NOT NULL,
  `album` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Gegevens worden geëxporteerd voor tabel `Track`
--

INSERT INTO `Track` (`id`, `path`, `title`, `artist`, `duration`, `album`) VALUES
(1, 'example/path/1', NULL, NULL, 0, NULL),
(2, 'example/path/2', NULL, NULL, 0, NULL),
(3, 'example/path/3', NULL, NULL, 0, NULL),
(4, 'example/path/4', NULL, NULL, 0, NULL),
(5, 'example/path/5', NULL, NULL, 0, NULL),
(6, 'example/path/6', NULL, NULL, 0, NULL),
(7, 'example/path/7', NULL, NULL, 0, NULL),
(8, 'example/path/8', NULL, NULL, 0, NULL),
(9, 'example/path/9', NULL, NULL, 0, NULL),
(10, 'example/path/10', NULL, NULL, 0, NULL);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `User`
--

CREATE TABLE `User` (
  `id` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexen voor tabel `Playlist`
--
ALTER TABLE `Playlist`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `playlist_track`
--
ALTER TABLE `Playlist_track`
  ADD PRIMARY KEY (`playlist_id`,`track_id`),
  ADD KEY `IDX_75FFE1E56BBD148` (`playlist_id`),
  ADD KEY `IDX_75FFE1E55ED23C43` (`track_id`);

--
-- Indexen voor tabel `track`
--
ALTER TABLE `Track`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `user`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `playlist`
--
ALTER TABLE `Playlist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT voor een tabel `track`
--
ALTER TABLE `Track`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT voor een tabel `user`
--
ALTER TABLE `User`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `Playlist_track`
--
ALTER TABLE `Playlist_track`
  ADD CONSTRAINT `FK_75FFE1E55ED23C43` FOREIGN KEY (`track_id`) REFERENCES `track` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_75FFE1E56BBD148` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

ALTER TABLE `User` CHANGE `token` `token` VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL;
