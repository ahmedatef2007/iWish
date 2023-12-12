-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: iwish2
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contributions`
--

DROP TABLE IF EXISTS `contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contributions` (
  `contribution_id` int NOT NULL AUTO_INCREMENT,
  `contributor_id` int NOT NULL,
  `wish_id` int NOT NULL,
  `contribution_amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`contribution_id`),
  KEY `contributor_id` (`contributor_id`),
  KEY `wish_id` (`wish_id`),
  CONSTRAINT `contributions_ibfk_1` FOREIGN KEY (`contributor_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `contributions_ibfk_2` FOREIGN KEY (`wish_id`) REFERENCES `wish_list` (`wish_id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contributions`
--

LOCK TABLES `contributions` WRITE;
/*!40000 ALTER TABLE `contributions` DISABLE KEYS */;
INSERT INTO `contributions` VALUES (29,3,22,500.00),(58,1,2,500.00),(59,1,29,300.00),(60,1,29,100.00),(61,1,29,50.00),(62,1,29,10.00),(130,1,48,49.00),(142,4,35,24.00),(143,4,34,299.00),(144,4,30,100.00),(145,1,45,199.00),(146,4,22,100.00),(147,3,52,100.00),(148,1,53,29.00),(149,3,24,10.00),(150,2,24,9.00),(151,2,30,100.00);
/*!40000 ALTER TABLE `contributions` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `notify_receiver_on_contribution` AFTER INSERT ON `contributions` FOR EACH ROW BEGIN
    DECLARE receiver_email VARCHAR(45);
    DECLARE contributor_name VARCHAR(90);
    DECLARE item_name VARCHAR(255);
    DECLARE contribution_amount DECIMAL(10,2);

    -- Retrieve information about the contribution
    SELECT
        u.email AS receiver_email,
        CONCAT(cu.firstName, ' ', cu.lastName) AS contributor_name,
        i.item_name,
        NEW.contribution_amount
    INTO
        receiver_email,
        contributor_name,
        item_name,
        contribution_amount
    FROM
        wish_list w
    JOIN
        items i ON w.item_id = i.item_id
    JOIN
        users u ON w.user_id = u.user_id
    JOIN
        users cu ON NEW.contributor_id = cu.user_id
    WHERE
        w.wish_id = NEW.wish_id;

    -- Insert a notification for the item receiver
    INSERT INTO notifications (user_email, from_user_id, notification_text, is_notified, wish_id)
    VALUES (
        receiver_email,
        NEW.contributor_id,
        CONCAT(contributor_name, ' contributed ', contribution_amount, ' to buy you the item "', item_name, '" from your wishlist.'),
        FALSE,
        NEW.wish_id
    );
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `friend_request`
--

DROP TABLE IF EXISTS `friend_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `sender_email` varchar(45) NOT NULL,
  `receiver_email` varchar(45) NOT NULL,
  `is_accepted` tinyint(1) DEFAULT NULL,
  `request_date` date DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `sender_email` (`sender_email`),
  KEY `receiver_email` (`receiver_email`),
  CONSTRAINT `friend_request_ibfk_1` FOREIGN KEY (`sender_email`) REFERENCES `users` (`email`),
  CONSTRAINT `friend_request_ibfk_2` FOREIGN KEY (`receiver_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_request`
--

LOCK TABLES `friend_request` WRITE;
/*!40000 ALTER TABLE `friend_request` DISABLE KEYS */;
INSERT INTO `friend_request` VALUES (14,'ahmed@gmail.com','donia@gmail.com',1,'2023-12-07'),(15,'abdo@gmail.com','donia@gmail.com',1,'2023-12-07'),(16,'abdo@gmail.com','omar@gmail.com',1,'2023-12-07'),(17,'donia@gmail.com','omar@gmail.com',1,'2023-12-07'),(18,'abdo@gmail.com','shady@gmail.com',1,'2023-12-08'),(19,'omar@gmail.com','ahmed@gmail.com',1,'2023-12-08'),(20,'omar@gmail.com','shady@gmail.com',1,'2023-12-08'),(21,'ahmed@gmail.com','abdo@gmail.com',1,'2023-12-08'),(22,'test','abdo@gmail.com',1,'2023-12-08'),(24,'abdo@gmail.com','test2',NULL,'2023-12-08'),(25,'test','donia@gmail.com',1,'2023-12-08'),(26,'ahmed@gmail.com','shady@gmail.com',NULL,'2023-12-08'),(27,'ahmed@gmail.com','test',NULL,'2023-12-09'),(28,'israa@gmail.com','ahmed@gmail.com',NULL,'2023-12-10'),(30,'abdo@gmail.com','israa@gmail.com',NULL,'2023-12-10'),(31,'abdoo@gmail.com','ahmed@gmail.com',NULL,'2023-12-10'),(32,'marina@gmail.com','test',1,'2023-12-10'),(33,'marina@gmail.com','ahmed@gmail.com',NULL,'2023-12-10'),(34,'marina@gmail.com','ahmed@gmail.com',NULL,'2023-12-10'),(35,'abdoo@gmail.com','alimagdy@gmail.com',1,'2023-12-10'),(36,'marina@gmail.com','alimagdy@gmail.com',NULL,'2023-12-10'),(37,'donia@gmail.com','alimagdy@gmail.com',1,'2023-12-10'),(38,'donia@gmail.com','marina@gmail.com',NULL,'2023-12-10'),(39,'marina@gmail.com','israa@gmail.com',NULL,'2023-12-10'),(40,'mazen@gmail.com','abdo@gmail.com',NULL,'2023-12-10'),(42,'mazen@gmail.com','ahmed@gmail.com',NULL,'2023-12-10'),(43,'ahmed@gmail.com','salmaahmed@gmail.com',NULL,'2023-12-10');
/*!40000 ALTER TABLE `friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) NOT NULL,
  `item_price` decimal(10,2) NOT NULL,
  `item_category` varchar(255) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'Laptop',3000.00,'Electronics'),(2,'Mobile',499.99,'Electronics'),(3,'Band',29.99,'Fashion'),(4,'Camera',799.99,'Electronics'),(5,'Book',19.99,'Literature'),(6,'Headphones',99.99,'Electronics'),(7,'Watch',199.99,'Fashion'),(8,'Gaming Console',299.99,'Electronics'),(9,'Sunglasses',49.99,'Fashion'),(10,'Coffee Maker',79.99,'Appliances'),(11,'Running Shoes',89.99,'Footwear'),(12,'Backpack',39.99,'Fashion'),(13,'Guitar',299.99,'Music'),(14,'Fitness Tracker',129.99,'Electronics'),(15,'Desk Chair',149.99,'Furniture'),(16,'Board Game',24.99,'Entertainment'),(17,'Smart Home Hub',179.99,'Electronics'),(18,'Dumbbells',34.99,'Fitness'),(19,'Novel',14.99,'Literature'),(20,'Telescope',199.99,'Science'),(21,'Wireless Mouse',29.99,'Electronics'),(22,'Handbag',69.99,'Fashion'),(23,'LED TV',899.99,'Electronics'),(24,'Portable Charger',19.99,'Electronics'),(25,'Bluetooth Speaker',59.99,'Electronics');
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(45) NOT NULL,
  `from_user_id` int NOT NULL,
  `notification_text` varchar(255) NOT NULL,
  `is_notified` tinyint(1) NOT NULL,
  `wish_id` int DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `user_email` (`user_email`),
  KEY `from_user_id` (`from_user_id`),
  KEY `wish_id` (`wish_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_email`) REFERENCES `users` (`email`),
  CONSTRAINT `notifications_ibfk_2` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `notifications_ibfk_3` FOREIGN KEY (`wish_id`) REFERENCES `wish_list` (`wish_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'abdo@gmail.com',4,'test test contributed 24.00 to buy you the item \"Board Game\" from your wishlist.',0,35),(2,'abdo@gmail.com',4,'test test contributed 299.00 to buy you the item \"Gaming Console\" from your wishlist.',0,34),(3,'abdo@gmail.com',4,'test test contributed 100.00 to buy you the item \"Camera\" from your wishlist.',0,30),(4,'test',1,'abdo ashraf contributed 199.00 to buy you the item \"Watch\" from your wishlist.',0,45),(5,'abdo@gmail.com',4,'test test contributed 100.00 to buy you the item \"Laptop\" from your wishlist.',0,22),(6,'israa@gmail.com',3,'donia ayman contributed 100.00 to buy you the item \"Mobile\" from your wishlist.',0,52),(8,'abdo@gmail.com',3,'donia ayman contributed 10.00 to buy you the item \"Book\" from your wishlist.',0,24),(9,'abdo@gmail.com',2,'ahmed atef contributed 9.00 to buy you the item \"Book\" from your wishlist.',0,24),(10,'abdo@gmail.com',2,'ahmed atef contributed 100.00 to buy you the item \"Camera\" from your wishlist.',0,30);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'abdo','123','abdo@gmail.com','abdo','ashraf',865.00),(2,'ahmed','123','ahmed@gmail.com','ahmed','atef',514.00),(3,'doniaa','123','donia@gmail.com','donia','ayman',691.00),(4,'test','test','test','test','test',1235.00),(5,'Shady','123','shady@gmail.com','shady','mohamed',500.00),(6,'omara','123','omar@gmail.com','omar','ashraf',4202.00),(7,'test2','123','test2','test2','test2',100.00),(8,'israa ','123','israa@gmail.com','Israa','ali',0.00),(9,'abdullah','123','abdullah@gmail.com','Abdullah','Ahmed',500.00),(10,'hussien','123','hussien@gmail.com','Hussien','Khaled',800.00),(11,'hady','123','hady@gmail.com','Hady','Ahmed',1500.00),(12,'ahmedsamy','123','ahmedsamy@gmail.com','Ahmed','Samy',900.00),(13,'mohanad','123','mohanad@gmail.com','Mohanad','Mostafa',1200.00),(14,'mohamedelfekky','123','mohamedelfekky@gmail.com','Mohamed','Elfekky',1500.00),(15,'salmaahmed','123','salmaahmed@gmail.com','Salma','Ahmed',800.00),(16,'gomaaahmed','123','gomaaahmed@gmail.com','Gomaa','Ahmed',1100.00),(17,'radwaesmaiel','123','radwaesmaiel@gmail.com','Radwa','Esmaiel',1300.00),(18,'aliali','123','alimagdy@gmail.com','Ali','Magdy',0.00),(19,'abdoo','123','abdoo@gmail.com','Abdelrahman','Ashraf',0.00),(20,'marina','123','marina@gmail.com','marina','fawzy',0.00),(21,'ahmedmazen','123','mazen@gmail.com','Ahmed','Mazen',1000.00);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish_list`
--

DROP TABLE IF EXISTS `wish_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wish_list` (
  `wish_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`wish_id`),
  KEY `user_id` (`user_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `wish_list_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `wish_list_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `wish_list_ibfk_3` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish_list`
--

LOCK TABLES `wish_list` WRITE;
/*!40000 ALTER TABLE `wish_list` DISABLE KEYS */;
INSERT INTO `wish_list` VALUES (2,3,1),(22,1,1),(23,1,2),(24,1,5),(29,3,2),(30,1,4),(34,1,8),(35,1,16),(41,4,23),(42,4,15),(43,4,24),(44,4,20),(45,4,7),(46,4,11),(47,4,22),(48,4,9),(49,4,8),(50,4,3),(51,8,9),(52,8,2),(53,2,21),(54,3,4),(56,20,3),(57,20,7),(58,20,4);
/*!40000 ALTER TABLE `wish_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-12  9:12:47
