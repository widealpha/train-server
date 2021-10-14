-- MySQL dump 10.13  Distrib 8.0.23, for Linux (x86_64)
--
-- Host: localhost    Database: train
-- ------------------------------------------------------
-- Server version	8.0.23-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coach`
--

DROP TABLE IF EXISTS `coach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coach` (
  `coach_id` int NOT NULL AUTO_INCREMENT,
  `coach_no` int DEFAULT NULL,
  `train_code` varchar(20) DEFAULT NULL,
  `seat_type_code` varchar(10) DEFAULT NULL,
  `seat` bigint unsigned NOT NULL DEFAULT '18446744073709551615',
  PRIMARY KEY (`coach_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65711 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_form`
--

DROP TABLE IF EXISTS `order_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_form` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `payed` int DEFAULT '0' COMMENT '是否支付过',
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passenger` (
  `passenger_id` int NOT NULL AUTO_INCREMENT,
  `id_card_no` varchar(20) DEFAULT NULL,
  `student` tinyint(1) NOT NULL DEFAULT '0',
  `verified` tinyint(1) DEFAULT '1' COMMENT '验证身份',
  `student_verified` tinyint(1) DEFAULT '1' COMMENT '验证学生身份',
  `name` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`passenger_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `same_station`
--

DROP TABLE IF EXISTS `same_station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `same_station` (
  `origin` varchar(10) NOT NULL,
  `same` varchar(10) NOT NULL,
  KEY `same_station_origin_index` (`origin`),
  KEY `same_station_same_index` (`same`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `seat_type`
--

DROP TABLE IF EXISTS `seat_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_type` (
  `seat_type_code` varchar(10) NOT NULL,
  `seat_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seat_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station` (
  `name` varchar(20) NOT NULL COMMENT '车站名',
  `telecode` varchar(10) NOT NULL COMMENT '电报码',
  `en` varchar(255) NOT NULL COMMENT '车站英文名',
  `abbr` varchar(255) NOT NULL COMMENT '英文(拼音)缩写',
  PRIMARY KEY (`telecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station_price`
--

DROP TABLE IF EXISTS `station_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_price` (
  `start_station_telecode` varchar(10) NOT NULL,
  `end_station_telecode` varchar(10) NOT NULL,
  `seat_type_code` varchar(10) NOT NULL,
  `train_class_code` varchar(10) NOT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`start_station_telecode`,`end_station_telecode`,`seat_type_code`,`train_class_code`),
  KEY `station_price_start_station_end_station_index` (`start_station_telecode`,`end_station_telecode`,`train_class_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station_train`
--

DROP TABLE IF EXISTS `station_train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_train` (
  `train_code` varchar(255) NOT NULL,
  `station_telecode` varchar(255) NOT NULL,
  `arrive_day_diff` int DEFAULT NULL,
  `arrive_time` time DEFAULT NULL,
  `update_arrive_time` time DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `update_start_time` time DEFAULT NULL,
  `start_day_diff` int DEFAULT NULL,
  `station_no` int DEFAULT NULL,
  PRIMARY KEY (`train_code`,`station_telecode`),
  KEY `station_train_station_train_code_index` (`train_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station_way`
--

DROP TABLE IF EXISTS `station_way`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_way` (
  `start_station_telecode` varchar(10) DEFAULT NULL,
  `end_station_telecode` varchar(10) DEFAULT NULL,
  `coach_id` int DEFAULT NULL,
  `seat` bigint unsigned DEFAULT '18446744073709551615',
  `date` date DEFAULT '2021-09-01',
  UNIQUE KEY `station_way_pk` (`start_station_telecode`,`end_station_telecode`,`coach_id`,`date`),
  KEY `station_way_start_station_telecode_end_station_telecode_index` (`start_station_telecode`,`end_station_telecode`),
  KEY `station_way_date_index` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station_way_base`
--

DROP TABLE IF EXISTS `station_way_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_way_base` (
  `start_station_telecode` varchar(10) DEFAULT NULL,
  `end_station_telecode` varchar(10) DEFAULT NULL,
  `coach_id` int DEFAULT NULL,
  `seat` bigint unsigned DEFAULT '18446744073709551615',
  KEY `station_way_base__index` (`start_station_telecode`,`end_station_telecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system`
--

DROP TABLE IF EXISTS `system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start` tinyint(1) NOT NULL DEFAULT '1' COMMENT '系统状态',
  `update_time` time NOT NULL DEFAULT '08:00:00' COMMENT '车票更新时间',
  `max_transfer_calculate` int NOT NULL DEFAULT '20' COMMENT '最大返回的中转车辆数量',
  `has_alipay_key` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `coach_id` int NOT NULL,
  `seat` bigint NOT NULL,
  `train_code` varchar(255) NOT NULL,
  `start_station_telecode` varchar(10) NOT NULL,
  `end_station_telecode` varchar(10) NOT NULL,
  `start_time` timestamp NOT NULL,
  `end_time` timestamp NOT NULL,
  `price` double NOT NULL,
  `passenger_id` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `student` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ticket_id`),
  KEY `ticket_passenger_id_index` (`passenger_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train` (
  `train_code` varchar(255) NOT NULL,
  `train_no` varchar(255) NOT NULL,
  `start_station_telecode` varchar(255) NOT NULL,
  `start_start_time` time NOT NULL,
  `end_station_telecode` varchar(255) DEFAULT NULL,
  `end_arrive_time` time DEFAULT NULL,
  `train_type_code` varchar(10) DEFAULT NULL,
  `train_class_code` varchar(10) DEFAULT NULL,
  `seat_types` varchar(20) DEFAULT NULL,
  `start_date` date NOT NULL,
  `stop_date` date NOT NULL DEFAULT '2050-12-31',
  PRIMARY KEY (`train_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `train_class`
--

DROP TABLE IF EXISTS `train_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train_class` (
  `train_class_code` varchar(10) NOT NULL,
  `train_class_name` varchar(255) NOT NULL,
  PRIMARY KEY (`train_class_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `train_type`
--

DROP TABLE IF EXISTS `train_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train_type` (
  `train_type_code` varchar(10) NOT NULL,
  `train_type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`train_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `user_id` int NOT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `gender` int DEFAULT '0' COMMENT '0是未知,1是男,2是女',
  `phone` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `head_image` varchar(255) DEFAULT NULL,
  `self_passenger_id` int DEFAULT NULL COMMENT '自己的乘客身份',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_passenger`
--

DROP TABLE IF EXISTS `user_passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_passenger` (
  `user_id` int NOT NULL,
  `passenger_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`passenger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-14 20:12:51
