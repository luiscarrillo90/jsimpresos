-- MySQL dump 10.13  Distrib 5.6.25, for Win64 (x86_64)
--
-- Host: localhost    Database: jsdb
-- ------------------------------------------------------
-- Server version	5.6.25-log

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
-- Table structure for table `detalleservicios`
--

DROP TABLE IF EXISTS `detalleservicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalleservicios` (
  `idNota` int(11) NOT NULL,
  `servicio` varchar(30) NOT NULL,
  `precio` double NOT NULL,
  `cantidad` int(11) NOT NULL,
  KEY `idNota` (`idNota`),
  CONSTRAINT `detalleservicios_ibfk_1` FOREIGN KEY (`idNota`) REFERENCES `notas` (`idNota`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleservicios`
--

LOCK TABLES `detalleservicios` WRITE;
/*!40000 ALTER TABLE `detalleservicios` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalleservicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documentosdepedido`
--

DROP TABLE IF EXISTS `documentosdepedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documentosdepedido` (
  `idDocumento` int(11) NOT NULL AUTO_INCREMENT,
  `urlDocumento` varchar(40) NOT NULL,
  PRIMARY KEY (`idDocumento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documentosdepedido`
--

LOCK TABLES `documentosdepedido` WRITE;
/*!40000 ALTER TABLE `documentosdepedido` DISABLE KEYS */;
INSERT INTO `documentosdepedido` VALUES (1,'no');
/*!40000 ALTER TABLE `documentosdepedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miembros`
--

DROP TABLE IF EXISTS `miembros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miembros` (
  `idMiembro` int(11) NOT NULL AUTO_INCREMENT,
  `noTarjeta` varchar(20) NOT NULL,
  `nombres` varchar(25) NOT NULL,
  `apPaterno` varchar(20) NOT NULL,
  `apMaterno` varchar(20) DEFAULT NULL,
  `rfc` varchar(15) DEFAULT NULL,
  `domicilio` varchar(40) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `saldo` double NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `nombreUsuario` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`idMiembro`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miembros`
--

LOCK TABLES `miembros` WRITE;
/*!40000 ALTER TABLE `miembros` DISABLE KEYS */;
INSERT INTO `miembros` VALUES (1,'no','no','no','no','no','no','no',0,'2015-07-14 17:07:43','no','no'),(2,'12345','Pedro Antonio','Flores','Gonzalez','AE1234A','Falsa no. 123','52-3-15-15',2000,'2015-07-14 17:09:46','elprimero','pass1'),(3,'43215','Juan Roman','Perez','Diaz','JRM321','Siempreviva no.264','52-3-12-43',4000,'2015-07-14 17:13:04','elsegundo','pass2'),(4,'123456789','Luis Alberto','Chavez','Rodriguez','ESESE122','Barrio Loco No.333','52-2-32-12',1000,'2015-07-16 18:45:52','eltercero','pass'),(5,'434224353','Lorenzo','Lamas','Cabas','ERJAKJ12','Venezuela No.32','52-2-33-12',1500,'2015-07-16 18:47:36','elcuarto','pass'),(6,'434224353','Leonardo','Robles','Carmona','POALAI12','Cerro Balnco No.12','52-2-21-32',500,'2015-07-16 18:48:31','elquinto','pass'),(7,'3212345','Leonor','Herrera','Equiua','LAOA1234','Calle del cerro No.122','52-3-56-74',4500,'2015-07-16 18:50:06','elsexto','pass'),(8,'3212345','Paola','Saenz','Lopez','OAIEK2345','Falsa No.122','52-4-23-12',1200,'2015-07-16 18:51:19','elseptimo','pass'),(9,'12345532','Oscar','Madera','De la Cruz','OJAJA123','Roble No.9','52-2-43-56',1235,'2015-07-16 18:53:35','eloctavio','pass'),(10,'12345532','Octavio','Mendez','Rosas','OAKAUJ1234','Arbol No.34','52-3-56-89',1203,'2015-07-16 18:54:32','elnoveno','pass'),(11,'52536728','Maria','Lopez','Rios','JAJAASA56','Honduras No.123','52-3-56-89',1345,'2015-07-16 18:55:39','eldecimo','pass'),(12,'3456747','Liliana','Lago','Sosa','LIASJA345','Estados Unidos No.123','52-3-56-82',3412,'2015-07-16 18:56:31','elundecimo','pass');
/*!40000 ALTER TABLE `miembros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notas`
--

DROP TABLE IF EXISTS `notas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notas` (
  `idNota` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(25) NOT NULL DEFAULT 'no',
  `apPaterno` varchar(20) NOT NULL DEFAULT 'no',
  `apMaterno` varchar(20) DEFAULT NULL,
  `telefono` varchar(10) NOT NULL DEFAULT 'no',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fechaEntrega` varchar(10) DEFAULT NULL,
  `abonado` double NOT NULL,
  `observaciones` varchar(100) DEFAULT NULL,
  `contabilizado` varchar(3) NOT NULL DEFAULT 'no',
  `idDocumento` int(11) NOT NULL,
  `idMiembro` int(11) NOT NULL,
  PRIMARY KEY (`idNota`),
  KEY `idDocumento` (`idDocumento`),
  KEY `idMiembro` (`idMiembro`),
  CONSTRAINT `notas_ibfk_1` FOREIGN KEY (`idDocumento`) REFERENCES `documentosdepedido` (`idDocumento`),
  CONSTRAINT `notas_ibfk_2` FOREIGN KEY (`idMiembro`) REFERENCES `miembros` (`idMiembro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notas`
--

LOCK TABLES `notas` WRITE;
/*!40000 ALTER TABLE `notas` DISABLE KEYS */;
/*!40000 ALTER TABLE `notas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(30) NOT NULL,
  `apPaterno` varchar(20) NOT NULL,
  `apMaterno` varchar(20) DEFAULT NULL,
  `nombreUsuario` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Luis Federico','Carrillo','Ayala','luisin','pass'),(2,'Luis Raúl','Martínez','Sáenz','Rols','roolspass');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-21  0:40:32
