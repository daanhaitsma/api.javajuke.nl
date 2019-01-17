-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 16 jan 2019 om 13:11
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
-- Tabelstructuur voor tabel `Album`
--

CREATE TABLE `Album` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `cover_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Playlist`
--

CREATE TABLE `Playlist` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Playlist_Track`
--

CREATE TABLE `Playlist_Track` (
  `playlist_id` int(11) NOT NULL,
  `track_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Track`
--

CREATE TABLE `Track` (
  `id` int(11) NOT NULL,
  `path` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `artist` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `duration` bigint(20) NOT NULL,
  `album_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `User`
--

CREATE TABLE `User` (
  `id` int(11) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `Album`
--
ALTER TABLE `Album`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `Playlist`
--
ALTER TABLE `Playlist`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `Playlist_Track`
--
ALTER TABLE `Playlist_Track`
  ADD PRIMARY KEY (`playlist_id`,`track_id`),
  ADD KEY `IDX_75FFE1E56BBD148` (`playlist_id`),
  ADD KEY `IDX_75FFE1E55ED23C43` (`track_id`);

--
-- Indexen voor tabel `Track`
--
ALTER TABLE `Track`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Album` (`album_id`);

--
-- Indexen voor tabel `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `Album`
--
ALTER TABLE `Album`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `Playlist`
--
ALTER TABLE `Playlist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `Track`
--
ALTER TABLE `Track`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `User`
--
ALTER TABLE `User`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `Playlist_Track`
--
ALTER TABLE `Playlist_Track`
  ADD CONSTRAINT `FK_75FFE1E55ED23C43` FOREIGN KEY (`track_id`) REFERENCES `Track` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_75FFE1E56BBD148` FOREIGN KEY (`playlist_id`) REFERENCES `Playlist` (`id`) ON DELETE CASCADE;

--
-- Beperkingen voor tabel `Track`
--
ALTER TABLE `Track`
  ADD CONSTRAINT `Album FK` FOREIGN KEY (`album_id`) REFERENCES `Album` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
