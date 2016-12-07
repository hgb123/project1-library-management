CREATE DATABASE  IF NOT EXISTS `baolibrary` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `baolibrary`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: baolibrary
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `docgia`
--

DROP TABLE IF EXISTS `docgia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docgia` (
  `maDocGia` int(11) NOT NULL AUTO_INCREMENT,
  `tenTaiKhoan` varchar(45) NOT NULL,
  `matKhau` varchar(45) NOT NULL,
  `hoTen` varchar(30) NOT NULL,
  `gioiTinh` varchar(10) NOT NULL,
  `ngaySinh` varchar(10) NOT NULL,
  `diaChi` varchar(100) NOT NULL,
  `soDienThoai` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  PRIMARY KEY (`maDocGia`),
  UNIQUE KEY `username_UNIQUE` (`tenTaiKhoan`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docgia`
--

LOCK TABLES `docgia` WRITE;
/*!40000 ALTER TABLE `docgia` DISABLE KEYS */;
INSERT INTO `docgia` VALUES (1,'docgia1','123abc','Ho Gia Bao','Nam','14-10-1996','A','0123456789','pippi.blockhead@gmail.com'),(2,'docgia2','123abc','Luu Trung Hieu','Nam','22-12-1996','A','0124536475','hieu@gmail.com'),(3,'docgia3','cba321','Duong Thi Cuong','Nu','29-02-1996','B','0329536475','cuong@gmail.com'),(4,'docgia4','123abc','Nguyen Trung Son','Nu','29-02-1996','B','0924539475','son@gmail.com'),(5,'docgia5','123abc','Vu Duc Viet','Nam','29-02-1996','C','0124575457','viet@gmail.com'),(6,'docgia6','123abc','Dam Le Quoc Phong','Nam','06-10-1996','C','0123458751','phong@gmail.com');
/*!40000 ALTER TABLE `docgia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `muontra`
--

DROP TABLE IF EXISTS `muontra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `muontra` (
  `maMuon` int(11) NOT NULL AUTO_INCREMENT,
  `maDocGia` int(11) NOT NULL,
  `ngayMuon` date NOT NULL,
  `hanTra` date NOT NULL,
  `ngayTra` date DEFAULT NULL,
  `tienDatCoc` varchar(11) NOT NULL,
  `tienPhat` varchar(11) NOT NULL DEFAULT '0',
  `ghiChu` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`maMuon`),
  KEY `fk_maDocGia_idx` (`maDocGia`),
  CONSTRAINT `fk_maDocGia` FOREIGN KEY (`maDocGia`) REFERENCES `docgia` (`maDocGia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `muontra`
--

LOCK TABLES `muontra` WRITE;
/*!40000 ALTER TABLE `muontra` DISABLE KEYS */;
INSERT INTO `muontra` VALUES (1,1,'2016-10-10','2016-11-11',NULL,'50000','0',''),(2,2,'2016-10-11','2016-11-12',NULL,'60000','0',''),(3,6,'2016-10-12','2016-11-13',NULL,'70000','0',''),(4,3,'2016-10-13','2016-11-14',NULL,'85000','0',''),(5,4,'2016-10-15','2016-11-16','2016-11-16','75000','0','da tra'),(6,5,'2016-10-20','2016-12-10','2016-12-17','40000','7000','tra muon'),(7,1,'2016-11-02','2016-12-30','2017-01-06','25000','7000','tra muon'),(8,1,'2016-11-05','2016-12-23','2017-01-01','50000','22500','tra muon');
/*!40000 ALTER TABLE `muontra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `muontra_sach`
--

DROP TABLE IF EXISTS `muontra_sach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `muontra_sach` (
  `maMuon` int(11) NOT NULL,
  `maSach` int(11) NOT NULL,
  PRIMARY KEY (`maMuon`,`maSach`),
  KEY `fk_maSach` (`maSach`),
  CONSTRAINT `fk_maMuon` FOREIGN KEY (`maMuon`) REFERENCES `muontra` (`maMuon`),
  CONSTRAINT `fk_maSach` FOREIGN KEY (`maSach`) REFERENCES `sach` (`maSach`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `muontra_sach`
--

LOCK TABLES `muontra_sach` WRITE;
/*!40000 ALTER TABLE `muontra_sach` DISABLE KEYS */;
INSERT INTO `muontra_sach` VALUES (1,1),(4,1),(8,1),(1,2),(3,2),(4,2),(8,2),(1,3),(5,3),(8,3),(1,4),(2,4),(3,4),(5,4),(8,4),(2,5),(6,5),(7,5),(8,5),(3,6),(6,6),(7,6),(5,7),(1,13),(2,13);
/*!40000 ALTER TABLE `muontra_sach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sach`
--

DROP TABLE IF EXISTS `sach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sach` (
  `maSach` int(11) NOT NULL AUTO_INCREMENT,
  `tenSach` varchar(50) NOT NULL,
  `tacGia` varchar(30) NOT NULL,
  `nxb` varchar(20) NOT NULL,
  `theLoai` varchar(20) NOT NULL,
  `namXB` varchar(10) NOT NULL,
  `soLuong` int(11) NOT NULL,
  PRIMARY KEY (`maSach`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sach`
--

LOCK TABLES `sach` WRITE;
/*!40000 ALTER TABLE `sach` DISABLE KEYS */;
INSERT INTO `sach` VALUES (1,'Co mot pho vua di qua pho','Dinh Vu Hoang Nguyen ','Nha Nam','Tap van','2013',20),(2,'Doi lua xung doi','Nam Cao','Nha Nam','Truyen ngan','2015',20),(3,'Mat biec','Nguyen Nhat Anh','Tre','Truyen ngan','2015',20),(4,'Nhung ngay tho au','Nguyen Hong','Kim Dong','Truyen ngan','2015',20),(5,'De men phieu luu ki ','To Hoai','Kim Dong','Truyen thieu nhi','2009',20),(6,'Nobita va chu khung long lac loai','Fujiko F Fujio','Kim Dong','Truyen tranh','1996',20),(7,'Bi Vo','Nguyen Hong','Nha Nam','Tieu thuyet','2014',20),(9,'asda','sdasdasd','Tre','dasdas','2001',5),(10,'asd','asdad','assd','asda','2000',10),(11,'asd','asdad','assd','asda','2000',10),(13,'Thuong nho thang 12','Vu Bang','Nha Nam','Truyen ngan','2005',15),(14,'asd','asdad','assd','asda','2000',10),(16,'chó','mot chu chim','Tre','sdasda','1969',5),(17,'mot chu cho','mot chu meo','Tre','trinh tham','2015',10),(18,'asdasd','asdasdasd','Kim Dong','Truyen ngan','2014',5);
/*!40000 ALTER TABLE `sach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `thing` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thuthu`
--

DROP TABLE IF EXISTS `thuthu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thuthu` (
  `maThuThu` int(11) NOT NULL AUTO_INCREMENT,
  `tenTaiKhoan` varchar(30) NOT NULL,
  `matKhau` varchar(30) NOT NULL,
  `tenThuThu` varchar(45) NOT NULL,
  PRIMARY KEY (`maThuThu`),
  UNIQUE KEY `tenTaiKhoan_UNIQUE` (`tenTaiKhoan`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuthu`
--

LOCK TABLES `thuthu` WRITE;
/*!40000 ALTER TABLE `thuthu` DISABLE KEYS */;
INSERT INTO `thuthu` VALUES (1,'thuthu1','123abc','Chuon Chuon'),(2,'thuthu2','123abc','Kim Kim');
/*!40000 ALTER TABLE `thuthu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-28 22:14:14
