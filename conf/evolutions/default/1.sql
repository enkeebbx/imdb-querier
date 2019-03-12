# schema
# --- !Ups

CREATE TABLE `title_akas` (
  `ordering` int(11) DEFAULT NULL,
  `isOriginalTitle` tinyint(1) DEFAULT NULL,
  `titleId` int(11) DEFAULT NULL,
  `types` varchar(31) DEFAULT NULL,
  `title` text,
  `t_soundex` varchar(5) DEFAULT NULL,
  `attributes` varchar(127) DEFAULT NULL,
  `region` varchar(5) DEFAULT NULL,
  `language` varchar(5) DEFAULT NULL,
  KEY `ix_title_akas_t_soundex` (`t_soundex`),
  KEY `ix_title_akas_types` (`types`),
  KEY `ix_title_akas_isOriginalTitle` (`isOriginalTitle`),
  KEY `ix_title_akas_region` (`region`),
  KEY `ix_title_akas_language` (`language`),
  KEY `ix_title_akas_titleId` (`titleId`)
);

CREATE TABLE `title_basics` (
  `genres` text,
  `titleType` varchar(16) DEFAULT NULL,
  `t_soundex` varchar(5) DEFAULT NULL,
  `originalTitle` text,
  `startYear` int(11) DEFAULT NULL,
  `runtimeMinutes` int(11) DEFAULT NULL,
  `primaryTitle` text,
  `isAdult` tinyint(1) DEFAULT NULL,
  `endYear` int(11) DEFAULT NULL,
  `tconst` int(11) DEFAULT NULL,
  KEY `ix_title_basics_t_soundex` (`t_soundex`),
  KEY `ix_title_basics_isAdult` (`isAdult`),
  KEY `ix_title_basics_startYear` (`startYear`),
  KEY `ix_title_basics_runtimeMinutes` (`runtimeMinutes`),
  KEY `ix_title_basics_titleType` (`titleType`),
  KEY `ix_title_basics_tconst` (`tconst`)
);

CREATE TABLE `genres` (
  `id` int(11),
  `genre` text
);

CREATE TABLE `genre_titles` (
  `id` int(11),
  `genre_id` int(11),
  `tconst` int(11),
  KEY `ix_genre_id_tconst` (`genre_id`),
  KEY `ix_genre_titles_tconst` (`tconst`)
);

CREATE TABLE `title_crew` (
  `directors` text,
  `writers` text,
  `tconst` int(11) DEFAULT NULL,
  KEY `ix_title_crew_tconst` (`tconst`)
);

CREATE TABLE `title_episode` (
  `episodeNumber` int(11) DEFAULT NULL,
  `tconst` int(11) DEFAULT NULL,
  `parentTconst` int(11) DEFAULT NULL,
  `seasonNumber` int(11) DEFAULT NULL,
  KEY `ix_title_episode_tconst` (`tconst`),
  KEY `ix_title_episode_parentTconst` (`parentTconst`)
);

CREATE TABLE `title_principals` (
  `category` text,
  `principalCast` text,
  `ordering` text,
  `tconst` int(11) DEFAULT NULL,
  `characters` text,
  `job` text,
  `nconst` varchar(10),
  KEY `ix_title_principals_tconst` (`tconst`),
  KEY `ix_title_principals_nconst` (`nconst`)
);

CREATE TABLE `title_actors` (
  `id` int(11),
  `tconst` int(11),
  `nconst` text
);

CREATE TABLE `title_ratings` (
  `numVotes` int(11) DEFAULT NULL,
  `tconst` int(11) DEFAULT NULL,
  `averageRating` float DEFAULT NULL,
  KEY `ix_title_ratings_numVotes` (`numVotes`),
  KEY `ix_title_ratings_tconst` (`tconst`),
  KEY `ix_title_ratings_averageRating` (`averageRating`)
);

CREATE TABLE `name_basics` (
  `primaryProfession` text,
  `deathYear` int(11) DEFAULT NULL,
  `knownForTitles` text,
  `ns_soundex` varchar(5) DEFAULT NULL,
  `nconst` int(11) DEFAULT NULL,
  `sn_soundex` varchar(5) DEFAULT NULL,
  `primaryName` text,
  `birthYear` int(11) DEFAULT NULL,
  `s_soundex` varchar(5) DEFAULT NULL,
  KEY `ix_name_basics_s_soundex` (`s_soundex`),
  KEY `ix_name_basics_sn_soundex` (`sn_soundex`),
  KEY `ix_name_basics_nconst` (`nconst`),
  KEY `ix_name_basics_deathYear` (`deathYear`),
  KEY `ix_name_basics_birthYear` (`birthYear`),
  KEY `ix_name_basics_ns_soundex` (`ns_soundex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# --- !Downs
DROP TABLE `title_akas`;
DROP TABLE `title_basics`;
DROP TABLE `title_crew`;
DROP TABLE `title_episode`;
DROP TABLE `title_principals`;
DROP TABLE `title_ratings`;
DROP TABLE `genres`;
DROP TABLE `genre_titles`;
DROP TABLE `title_actors`;
