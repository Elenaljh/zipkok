create database 'ssafyhouse';
use ssafyhouse;

CREATE TABLE `board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `hit` int DEFAULT '0',
  `type` int NOT NULL,
  `member_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `boom` int DEFAULT '0',
  `writer` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`board_id`),
  KEY `fk_member_id` (`member_id`),
  CONSTRAINT `fk_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `type_constraint` CHECK ((`type` in (0,1,2)))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `board_id` int NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `board_id` (`board_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `member` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `name` varchar(30) NOT NULL,
  `registered_at` timestamp NULL DEFAULT NULL,
  `age` int DEFAULT NULL,
  `prefered_place_1` varchar(50) DEFAULT NULL,
  `prefered_place_2` varchar(50) DEFAULT NULL,
  `prefered_place_3` varchar(50) DEFAULT NULL,
  `prefered_type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
