-- MySQL dump 10.13  Distrib 5.6.26, for Win64 (x86_64)
--
-- Host: localhost    Database: jsdb
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `clientecautivo`
--

DROP TABLE IF EXISTS `clientecautivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientecautivo` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(25) NOT NULL,
  `apparterno` varchar(20) NOT NULL,
  `appmaterno` varchar(20) DEFAULT NULL,
  `domicilio` varchar(70) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientecautivo`
--

LOCK TABLES `clientecautivo` WRITE;
/*!40000 ALTER TABLE `clientecautivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientecautivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `copiasexpress`
--

DROP TABLE IF EXISTS `copiasexpress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copiasexpress` (
  `precio` double NOT NULL,
  `cantidad` int(11) NOT NULL,
  `idcorte` int(11) NOT NULL,
  KEY `idcorte` (`idcorte`),
  CONSTRAINT `copiasexpress_ibfk_1` FOREIGN KEY (`idcorte`) REFERENCES `cortesdecaja` (`idcorte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `copiasexpress`
--

LOCK TABLES `copiasexpress` WRITE;
/*!40000 ALTER TABLE `copiasexpress` DISABLE KEYS */;
/*!40000 ALTER TABLE `copiasexpress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cortesdecaja`
--

DROP TABLE IF EXISTS `cortesdecaja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cortesdecaja` (
  `idcorte` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcorte`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cortesdecaja`
--

LOCK TABLES `cortesdecaja` WRITE;
/*!40000 ALTER TABLE `cortesdecaja` DISABLE KEYS */;
INSERT INTO `cortesdecaja` VALUES (1,'2015-08-06 17:44:50'),(2,'2015-08-17 07:02:49'),(3,'2015-08-17 08:11:41'),(4,'2015-08-17 08:21:36'),(5,'2015-08-18 04:49:19'),(6,'2015-08-18 05:05:06'),(7,'2015-08-18 17:11:36'),(8,'2015-08-22 07:56:35');
/*!40000 ALTER TABLE `cortesdecaja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalleabono`
--

DROP TABLE IF EXISTS `detalleabono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalleabono` (
  `idNota` int(11) NOT NULL,
  `monto` double NOT NULL,
  `tipoPago` varchar(10) DEFAULT 'Efectivo',
  `contabilizado` varchar(4) DEFAULT 'no',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idcorte` int(11) NOT NULL DEFAULT '1',
  KEY `idNota` (`idNota`),
  KEY `idcorte` (`idcorte`),
  CONSTRAINT `detalleabono_ibfk_1` FOREIGN KEY (`idNota`) REFERENCES `notas` (`idNota`),
  CONSTRAINT `detalleabono_ibfk_2` FOREIGN KEY (`idcorte`) REFERENCES `cortesdecaja` (`idcorte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleabono`
--

LOCK TABLES `detalleabono` WRITE;
/*!40000 ALTER TABLE `detalleabono` DISABLE KEYS */;
INSERT INTO `detalleabono` VALUES (1,50,'Tarjeta','no','2015-08-06 17:50:15',2),(2,6,'Efectivo','no','2015-08-06 17:50:15',2),(3,5,'Efectivo','no','2015-08-06 17:50:15',2),(4,10,'Efectivo','no','2015-08-06 17:50:15',2),(5,25,'Efectivo','no','2015-08-06 17:50:15',2),(6,50,'Efectivo','no','2015-08-06 17:50:15',2),(7,10,'Efectivo','no','2015-08-06 17:50:15',2),(8,6,'Efectivo','no','2015-08-06 17:50:15',2),(9,20,'Efectivo','no','2015-08-06 17:50:15',2),(10,30,'Efectivo','no','2015-08-06 17:50:15',2),(11,100,'Efectivo','no','2015-08-06 17:50:15',2),(12,100,'Efectivo','no','2015-08-06 17:50:15',2),(13,100,'Efectivo','no','2015-08-06 17:50:15',2),(14,10,'Efectivo','no','2015-08-06 17:50:15',2),(15,10,'Efectivo','no','2015-08-06 17:50:15',2),(16,10,'Efectivo','no','2015-08-06 17:50:15',2),(17,10,'Efectivo','no','2015-08-06 17:50:15',2),(18,10,'Efectivo','no','2015-08-06 17:50:15',2),(19,50,'Efectivo','no','2015-08-06 17:50:15',2),(20,50,'Efectivo','no','2015-08-06 17:50:15',2),(21,20,'Efectivo','no','2015-08-06 17:50:15',2),(11,66,'Tarjeta','no','2015-08-06 17:50:15',2),(4,8,'Efectivo','no','2015-08-06 17:50:15',2),(20,30,'Tarjeta','no','2015-08-06 17:50:15',2),(7,4,'Tarjeta','no','2015-08-06 17:50:15',2),(10,4,'Tarjeta','no','2015-08-06 17:50:15',2),(15,5,'Tarjeta','no','2015-08-06 17:50:15',2),(6,65.5,'Efectivo','no','2015-08-06 17:50:15',2),(22,20,'Tarjeta','no','2015-08-06 17:50:15',2),(23,100,'Efectivo','no','2015-08-06 17:50:15',2),(25,5,'Efectivo','no','2015-08-06 18:26:09',2),(26,5,'Efectivo','no','2015-08-06 18:30:31',2),(27,50,'Efectivo','no','2015-08-13 19:14:25',2),(28,20,'Efectivo','no','2015-08-13 19:30:46',2),(29,20,'Efectivo','no','2015-08-13 19:33:11',2),(30,50,'Efectivo','no','2015-08-13 19:42:56',2),(31,3,'Efectivo','no','2015-08-13 20:13:45',2),(32,1,'Efectivo','no','2015-08-14 04:26:24',2),(33,1,'Efectivo','no','2015-08-14 05:10:14',2),(34,1,'Efectivo','no','2015-08-14 05:11:52',2),(35,1,'Efectivo','no','2015-08-14 05:15:16',2),(36,1,'Efectivo','no','2015-08-14 05:17:45',2),(37,1,'Efectivo','no','2015-08-14 05:19:03',2),(38,1,'Efectivo','no','2015-08-14 05:19:21',2),(39,2,'Efectivo','no','2015-08-14 05:25:19',2),(40,1,'Efectivo','no','2015-08-14 05:31:57',2),(41,1,'Efectivo','no','2015-08-14 05:33:11',2),(42,1,'Efectivo','no','2015-08-14 05:39:47',2),(43,1,'Efectivo','no','2015-08-14 19:54:51',2),(44,50,'Efectivo','no','2015-08-15 02:01:21',2),(44,25,'Efectivo','no','2015-08-15 02:14:53',2),(44,25,'Tarjeta','no','2015-08-15 02:15:06',2),(39,1,'Efectivo','no','2015-08-15 03:07:01',2),(39,2,'Efectivo','no','2015-08-15 03:07:23',2),(24,7,'Efectivo','no','2015-08-15 03:11:30',2),(38,2,'Efectivo','no','2015-08-15 03:19:17',2),(38,2,'Efectivo','no','2015-08-15 03:19:30',2),(5,5,'Efectivo','no','2015-08-15 04:09:51',2),(1,50,'Tarjeta','no','2015-08-15 04:11:38',2),(45,20,'Efectivo','no','2015-08-15 05:51:25',2),(45,80,'Efectivo','no','2015-08-15 05:52:11',2),(45,1,'Tarjeta','no','2015-08-15 05:52:31',2),(46,25,'Tarjeta','no','2015-08-15 05:56:25',2),(47,1,'Efectivo','no','2015-08-15 05:58:49',2),(48,1,'Efectivo','no','2015-08-15 06:00:04',2),(49,2,'Efectivo','no','2015-08-15 06:00:47',2),(50,1,'Efectivo','no','2015-08-15 06:50:53',2),(51,50,'Tarjeta','no','2015-08-15 07:24:38',2),(52,1,'Efectivo','no','2015-08-18 04:55:33',6),(53,1,'Efectivo','no','2015-08-18 04:59:21',6),(54,1,'Tarjeta','no','2015-08-18 04:59:44',6),(55,1,'Efectivo','no','2015-08-18 05:00:20',6),(51,50,'Tarjeta','no','2015-08-18 05:03:44',6),(56,50,'Efectivo','no','2015-08-18 17:11:04',7),(56,25,'Efectivo','no','2015-08-18 17:13:35',8),(56,25,'Efectivo','no','2015-08-18 17:13:47',8),(57,1,'Efectivo','no','2015-08-20 18:31:59',8),(7,2,'Efectivo','no','2015-08-22 07:38:40',8),(3,2,'Efectivo','no','2015-08-22 07:41:08',8),(28,1,'Efectivo','no','2015-08-22 07:45:10',8),(30,10,'Efectivo','no','2015-08-22 07:50:23',8),(58,1,'Efectivo','no','2015-08-22 07:50:49',8),(59,1,'Efectivo','no','2015-08-25 17:43:16',1),(60,0.5,'Efectivo','no','2015-08-25 17:53:46',1),(61,1,'Efectivo','no','2015-08-25 17:54:22',1),(62,5,'Efectivo','no','2015-08-25 17:55:29',1),(63,1,'Efectivo','no','2015-08-28 03:38:08',1),(64,1,'Efectivo','no','2015-08-28 03:38:57',1),(65,1,'Efectivo','no','2015-08-28 03:39:53',1),(67,1,'Efectivo','no','2015-09-02 18:25:02',1);
/*!40000 ALTER TABLE `detalleabono` ENABLE KEYS */;
UNLOCK TABLES;

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
  `idtipodeservicios` int(11) NOT NULL,
  KEY `idNota` (`idNota`),
  KEY `idtipodeservicios` (`idtipodeservicios`),
  CONSTRAINT `detalleservicios_ibfk_1` FOREIGN KEY (`idNota`) REFERENCES `notas` (`idNota`),
  CONSTRAINT `detalleservicios_ibfk_2` FOREIGN KEY (`idtipodeservicios`) REFERENCES `tiposdeservicios` (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleservicios`
--

LOCK TABLES `detalleservicios` WRITE;
/*!40000 ALTER TABLE `detalleservicios` DISABLE KEYS */;
INSERT INTO `detalleservicios` VALUES (1,'impresiones',0.5,200,1),(2,'impresiones',0.5,12,1),(3,'impresiones',0.5,12,1),(3,'engargolado',1,1,1),(4,'impresiones',0.5,12,1),(4,'engargolado',12,1,1),(5,'impresiones',0.5,12,1),(5,'engargolado',12,2,1),(6,'impresiones',0.5,15,1),(6,'engargolado',12,9,1),(7,'impresiones',0.5,12,1),(7,'otro',1,12,1),(8,'impresiones',0.5,12,1),(9,'impresiones',0.5,12,1),(9,'otro',14,1,1),(10,'impresiones',0.5,45,1),(10,'otro',12,1,1),(11,'impresiones',0.5,45,1),(11,'otro',12,12,1),(12,'impresiones',0.5,12,1),(12,'otro',12,7,1),(12,'otro más ',15,1,1),(13,'impresiones',0.5,12,1),(13,'otro',12,7,1),(13,'otro más ',15,2,1),(14,'impresiones',0.5,12,1),(14,'otro',12,1,1),(15,'impresiones',0.5,12,1),(15,'otro',10,1,1),(16,'impresiones',12,1,1),(17,'otro',12,1,1),(18,'otro',12,1,1),(19,'impresiones',0.5,120,1),(20,'otro',1,100,1),(21,'otro',1,50,1),(22,'impresiones',0.5,100,1),(23,'Impresiones',0.5,200,1),(24,'impresiones',0.5,15,1),(25,'otro',1,10,1),(26,'impresiones',0.5,12,1),(27,'impresiones',1,100,1),(28,'imp',5,5,1),(29,'impr',5,5,1),(30,'impr',1,100,1),(31,'impresiones',1,3,1),(32,'impresion',0.5,2,1),(33,'asd',1,1,1),(34,'as',1,1,1),(35,'Impresión',1,1,1),(36,'asd',1,1,1),(37,'asd',1,1,1),(38,'asd',1,1,1),(38,'ok',2,2,1),(39,'asd',2,2,1),(40,'donde sea',1,1,1),(41,'nuevo',1,1,1),(42,'aw',1,1,1),(43,'impresion',1,1,1),(44,'Vida',100,1,1),(45,'sosa',1,1,1),(45,'otro',100,1,1),(46,'impresiones',0.5,100,1),(47,'asd',1,1,1),(48,'simon',1,1,1),(49,'otro',1,1,1),(49,'simon',1,1,1),(50,'today',1,1,1),(51,'disco',100,1,1),(52,'lolquesea',1,1,1),(53,'asd',1,1,1),(54,'sdf',1,1,1),(55,'asd',1,1,1),(56,'impresion',0.5,200,1),(57,'loquesea',1,1,1),(58,'impr',1,1,1),(59,'Impresionasdasd',0.5,3,1),(59,'asdasd',4,4,1),(60,'impresion',0.5,1,1),(61,'asd',1,1,1),(62,'impresion',1,10,1),(63,'asd',1,2,1),(64,'asdq',1,2,1),(65,'asd',1,3,1),(67,'asd',1,1,1);
/*!40000 ALTER TABLE `detalleservicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalletipodeservicios`
--

DROP TABLE IF EXISTS `detalletipodeservicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalletipodeservicios` (
  `idTipo` int(11) NOT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `precio` double NOT NULL,
  KEY `idTipo` (`idTipo`),
  CONSTRAINT `detalletipodeservicios_ibfk_1` FOREIGN KEY (`idTipo`) REFERENCES `tiposdeservicios` (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalletipodeservicios`
--

LOCK TABLES `detalletipodeservicios` WRITE;
/*!40000 ALTER TABLE `detalletipodeservicios` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalletipodeservicios` ENABLE KEYS */;
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
-- Table structure for table `impresionesexpress`
--

DROP TABLE IF EXISTS `impresionesexpress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impresionesexpress` (
  `precio` double NOT NULL,
  `cantidad` int(11) NOT NULL,
  `idcorte` int(11) NOT NULL,
  KEY `idcorte` (`idcorte`),
  CONSTRAINT `impresionesexpress_ibfk_1` FOREIGN KEY (`idcorte`) REFERENCES `cortesdecaja` (`idcorte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impresionesexpress`
--

LOCK TABLES `impresionesexpress` WRITE;
/*!40000 ALTER TABLE `impresionesexpress` DISABLE KEYS */;
/*!40000 ALTER TABLE `impresionesexpress` ENABLE KEYS */;
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
  `domicilio` varchar(70) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `saldo` double NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nombreUsuario` varchar(20) NOT NULL,
  `password` varchar(40) NOT NULL,
  PRIMARY KEY (`idMiembro`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miembros`
--

LOCK TABLES `miembros` WRITE;
/*!40000 ALTER TABLE `miembros` DISABLE KEYS */;
INSERT INTO `miembros` VALUES (1,'no','no','no','no','no','no',-235,'2015-08-15 05:52:31','no','fd1286353570c5703799ba76999323b7c7447b06'),(2,'Sin Tarjeta','Cliente','Mostrador','','Sin Domicilio','Sin Telefono',-126,'2015-08-15 05:52:31','generico','0d73694e519057b7743fe69cc925fd8cfae127cb'),(3,'43215','Juan Roman','Perez','Diaz','Siempreviva no.264','52-3-12-43',5785.5,'2015-08-15 05:52:31','elsegundo','8be52126a6fde450a7162a3651d589bb51e9579d'),(4,'123456789','Luis Alberto','Spinneta','Rodriguez','Barrio Loco No.333','52-2-32-12',765,'2015-08-15 05:52:31','eltercero','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(5,'434224353','Lorenzo','Lamas','Cabas','Venezuela No.32','52-2-33-12',1265,'2015-08-15 05:52:31','elcuarto','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(6,'434224353','Leonardo','Robles','Carmona','Cerro Balnco No.12','52-2-21-32',265,'2015-08-15 05:52:31','elquinto','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(7,'3212345','Leonor','Herrera','Equiua','Calle del cerro No.122','52-3-56-74',4265,'2015-08-15 05:52:31','elsexto','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(8,'3212345','Paola','Saenz','Lopez','Falsa No.122','52-4-23-12',965,'2015-08-15 05:52:31','elseptimo','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(9,'12345532','Oscar','Madera','De la Cruz','Roble No.9','52-2-43-56',1000,'2015-08-15 05:52:31','eloctavio','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(10,'12345532','Octavio','Mendez','Rosas','Arbol No.34','52-3-56-89',968,'2015-08-15 05:52:31','elnoveno','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(11,'52536728','Maria','Lopez','Rios','Honduras No.123','52-3-56-89',1110,'2015-08-15 05:52:31','eldecimo','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(12,'3456747','Liliana','Lago','Sosa','Estados Unidos No.123','52-3-56-82',3174,'2015-08-15 05:52:31','elundecimo','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(13,'1231212','Andrea','Palma','Palma','Arrayan no. 12','52-3-12-23',54,'2015-08-15 05:52:31','andreita','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(15,'3456747','Liliana Leonela','Lago','Sosa','Estados Unidos No.123','52-3-56-82',74,'2015-08-15 05:52:31','elundecimo','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(16,'10410572','Luis Raúl','Martinez','Saenz','Rio Tamesis','23',24,'2015-08-15 07:24:38','rols.mart','c0e827297f9a62f1bdb475d8a63cc054a1e96c6f'),(17,'102938','Gustavo','Ceratí','','Calle azul','123234',149,'2015-08-15 05:56:25','electrico','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(19,'3456','New','Subject','','sd','34',150,'2015-08-17 07:00:44','nuevo','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(20,'3459','New','Subject','','asd','34',150,'2015-08-17 07:02:18','nuevo','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(21,'345','asd','asd','','asd','23',250,'2015-08-18 04:37:04','asd','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),(22,'9','1','98','98','98','98',200,'2015-08-24 18:03:13','98','f10e2821bbbea527ea02200352313bc059445190');
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
  `telefono` varchar(30) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fechaentrega` varchar(30) DEFAULT NULL,
  `observaciones` varchar(400) DEFAULT NULL,
  `idDocumento` int(11) NOT NULL,
  `idMiembro` int(11) NOT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'En Espera...',
  `domicilio` varchar(70) NOT NULL,
  `descuento` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  PRIMARY KEY (`idNota`),
  KEY `idDocumento` (`idDocumento`),
  KEY `idMiembro` (`idMiembro`),
  CONSTRAINT `notas_ibfk_1` FOREIGN KEY (`idDocumento`) REFERENCES `documentosdepedido` (`idDocumento`),
  CONSTRAINT `notas_ibfk_2` FOREIGN KEY (`idMiembro`) REFERENCES `miembros` (`idMiembro`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notas`
--

LOCK TABLES `notas` WRITE;
/*!40000 ALTER TABLE `notas` DISABLE KEYS */;
INSERT INTO `notas` VALUES (1,'no','no','no','52-3-12-43','2015-07-27 18:15:23','12-jul-2015','',1,3,'En Espera...','',0,0),(2,'Artemio','Lozano','Rivas','627-12-3-12-78','2015-07-27 18:15:23','03-jul-2015','',1,1,'En Espera...','',0,0),(3,'Luis Roberto','Perez','Loya','785-65-45-78','2015-07-27 18:15:23',NULL,'',1,1,'En Espera...','',0,0),(4,'Luis Yolo','Perez','Torres','627-52-8-47-51','2015-07-27 18:15:23',NULL,'',1,1,'En Espera...','',0,0),(5,'Luis Federicoo','Carrillo','Ayala','6271137668','2015-08-02 19:59:40',NULL,'El archivo está guardado en la carpeta c://Mis documentos/tareas/index.html',1,1,'En Espera...','',0,0),(6,'Luis Rogelio','Pérez','Chavez','627-12-87-95','2015-07-30 19:14:15',NULL,'',1,1,'En Espera...','',0,0),(7,'no','no','no','52-2-33-12','2015-07-25 03:41:42','05-jul-2015','',1,5,'En Espera...','',0,0),(8,'no','no','no','52-3-12-43','2015-07-25 03:49:05','05-jul-2015','',1,3,'En Espera...','',0,0),(9,'Karla','Gonzalez','','35125485','2015-08-02 20:01:22',NULL,'',1,1,'En Espera...','',0,0),(10,'no','no','no','52-3-56-74','2015-07-25 03:55:16',NULL,'',1,7,'En Espera...','',0,0),(11,'no','no','no','52-3-56-82','2015-07-25 03:57:03',NULL,'',1,12,'En Espera...','',0,0),(12,'no','no','no','52-2-33-12','2015-07-25 04:00:11',NULL,'',1,5,'En Espera...','',0,0),(13,'no','no','no','52-3-56-89','2015-07-25 04:01:15',NULL,'',1,11,'En Espera...','',0,0),(14,'no','no','no','52-3-56-89','2015-07-25 04:12:48',NULL,'',1,11,'En Espera...','',0,0),(15,'no','no','no','52-2-32-12','2015-07-25 04:14:21',NULL,'',1,4,'En Espera...','',0,0),(16,'no','no','no','52-3-56-82','2015-07-25 04:16:46',NULL,'',1,12,'En Espera...','',0,0),(17,'no','no','no','52-3-56-74','2015-07-25 04:17:35',NULL,'',1,7,'En Espera...','',0,0),(18,'no','no','no','52-3-56-89','2015-07-25 04:18:58',NULL,'',1,11,'En Espera...','',0,0),(19,'no','no','no','52-3-56-89','2015-07-25 04:20:03',NULL,'',1,11,'En Espera...','',0,0),(20,'no','no','no','52-3-56-89','2015-07-25 04:21:13',NULL,'',1,10,'En Espera...','',0,0),(21,'no','no','no','52-2-43-56','2015-07-25 04:23:46',NULL,'',1,9,'En Espera...','',0,0),(22,'no','no','no','52-3-12-23','2015-08-03 17:42:26',NULL,'',1,13,'En Espera...','',0,0),(23,'Alberto','Hinojos','','666','2015-08-03 17:45:18',NULL,'No hay observaciones',1,1,'En Espera...','',0,0),(24,'no','no','no','52-2-33-12','2015-08-06 18:24:44',NULL,'',1,5,'En Espera...','',0,0),(25,'no','no','no','52-3-56-74','2015-08-06 18:26:08',NULL,'',1,7,'En Espera...','',0,0),(26,'no','no','no','52-2-21-32','2015-08-06 18:30:30',NULL,'',1,6,'En Espera...','',0,0),(27,'no','no','no','23','2015-08-13 19:14:24','13/08/2015','mandar wp',1,16,'En Espera...','cualquier calle',0,0),(28,'no','no','no','23','2015-08-13 19:30:46','14/08/2015','asd',1,16,'En Espera...','cualquier calle',0,0),(29,'cualquier','cliente','para probar','666','2015-08-13 19:33:11','14/08/2015','dfgh',1,1,'En Espera...','cualquier calle',0,0),(30,'no','no','no','23','2015-08-13 19:42:56','14/08/2015','zxds',1,16,'En Espera...','cualquier calle',0,0),(31,'no','no','no','52-2-32-12','2015-08-13 20:13:45','13/08/2015','asd',1,4,'En Espera...','cualquier calle',0,0),(32,'no','no','no','Sin Telefono','2015-08-14 04:26:23','13/08/2015','mandar wp',1,2,'En Espera...','cualquier calle',0,0),(33,'no','no','no','23','2015-08-14 05:10:14','13/08/2015','',1,16,'En Espera...','etc',0,0),(34,'no','no','no','23','2015-08-14 05:11:51','14/08/2015','',1,16,'En Espera...','etc',0,0),(35,'no','no','no','23','2015-08-14 05:15:16','13/08/2015','',1,16,'En Espera...','Rio Tamesis',0,0),(36,'no','no','no','Sin Telefono','2015-08-14 05:17:45','13/08/2015','generico',1,2,'En Espera...','Sin Domicilio',0,0),(37,'no','no','no','Sin Telefono','2015-08-14 05:19:03','13/08/2015','',1,2,'En Espera...','Sin Domicilio',0,0),(38,'no','no','no','Sin Telefono','2015-08-14 05:19:21','13/08/2015','',1,2,'En Espera...','Sin Domicilio',0,0),(39,'Nuevo','Prueba','','no tiene','2015-08-14 05:25:19','13/08/2015','',1,1,'En Espera...','algo',0,0),(40,'no sé','asd','','42','2015-08-14 05:31:57','13/08/2015','nuevo',1,1,'En Espera...','asd',0,0),(41,'otro','cabron','','3','2015-08-14 05:33:11','13/08/2015','',1,1,'En Espera...','calle',0,0),(42,'prueba','ultima','','23','2015-08-14 05:39:47','13/08/2015','',1,1,'En Espera...','funciona',0,0),(43,'no','no','no','123234','2015-08-14 19:54:51','14/08/2015','idea',1,17,'En Espera...','Calle azul',0,0),(44,'no','no','no','123234','2015-08-15 02:01:21','15/08/2015','',1,17,'En Espera...','Calle azul',0,0),(45,'no','no','no','52-3-56-82','2015-08-15 05:51:25','15/08/2015','veinte',1,12,'En Espera...','Estados Unidos No.123',0,0),(46,'no','no','no','123234','2015-08-15 05:56:25','15/08/2015','',1,17,'En Espera...','Calle azul',0,0),(47,'no','no','no','52-2-33-12','2015-08-15 05:58:49','15/08/2015','',1,5,'En Espera...','Venezuela No.32',0,0),(48,'no','no','no','52-2-32-12','2015-08-15 06:00:04',NULL,'',1,4,'En Espera...','Barrio Loco No.333',0,0),(49,'no','no','no','23','2015-08-15 06:00:47','16/08/2015','ad',1,16,'En Espera','Rio Tamesis',0,0),(50,'no','no','no','52-2-33-12','2015-08-15 06:50:53','15/08/2015','today',1,5,'En Espera...','Venezuela No.32',0,0),(51,'no','no','no','23','2015-08-15 07:24:38','15/08/2015','',1,16,'Terminada','Rio Tamesis',0,0),(52,'no','no','no','Sin Telefono','2015-08-18 04:55:33','17/08/2015','asd',1,2,'En Espera...','Sin Domicilio',0,0),(53,'no','no','no','Sin Telefono','2015-08-18 04:59:21','17/08/2015','sdf',1,2,'En Espera...','Sin Domicilio',0,0),(54,'no','no','no','52-3-56-82','2015-08-18 04:59:44','17/08/2015','sdf',1,12,'En Espera...','Estados Unidos No.123',0,0),(55,'simn','bolivar','','233','2015-08-18 05:00:20','17/08/2015','dfg',1,1,'Terminada','asd',0,0),(56,'no','no','no','Sin Telefono','2015-08-18 17:11:04','19/08/2015','mandar wp',1,2,'En Espera...','Sin Domicilio',0,0),(57,'no','no','no','Sin Telefono','2015-08-20 18:31:59','20/08/2015','asd',1,2,'En Proceso','Sin Domicilio',0,0),(58,'no','no','no','Sin Telefono','2015-08-22 07:50:48','22/08/2015','asd',1,2,'En Proceso','Sin Domicilio',0,0),(59,'no','no','no','Sin Telefono','2015-08-25 17:43:16','25/08/2015','mandar wp',1,2,'En Espera...','Sin Domicilio',0,0),(60,'no','no','no','Sin Telefono','2015-08-25 17:53:46','25/08/2015','asd',1,2,'En Espera...','Sin Domicilio',0,0),(61,'no','no','no','Sin Telefono','2015-08-25 17:54:22','25/08/2015','asd',1,2,'En Espera...','Sin Domicilio',0,0),(62,'no','no','no','Sin Telefono','2015-08-25 17:55:29','25/08/2015','mandar',1,2,'En Espera...','Sin Domicilio',0,0),(63,'no','no','no','Sin Telefono','2015-08-28 03:38:08','27/08/2015','',1,2,'En Espera...','Sin Domicilio',0,0),(64,'no','no','no','Sin Telefono','2015-08-28 03:38:57','27/08/2015','',1,2,'En Proceso','Sin Domicilio',0,0),(65,'no','no','no','Sin Telefono','2015-08-28 03:39:53','27/08/2015','',1,2,'En Espera...','Sin Domicilio',0,0),(67,'no','no','no','Sin Telefono','2015-09-02 18:25:02','2/09/2015','a',1,2,'Terminada','Sin Domicilio',0,3);
/*!40000 ALTER TABLE `notas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salidasdedinero`
--

DROP TABLE IF EXISTS `salidasdedinero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salidasdedinero` (
  `precio` double NOT NULL,
  `concepto` varchar(60) NOT NULL,
  `idcorte` int(11) NOT NULL,
  KEY `idcorte` (`idcorte`),
  CONSTRAINT `salidasdedinero_ibfk_1` FOREIGN KEY (`idcorte`) REFERENCES `cortesdecaja` (`idcorte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salidasdedinero`
--

LOCK TABLES `salidasdedinero` WRITE;
/*!40000 ALTER TABLE `salidasdedinero` DISABLE KEYS */;
/*!40000 ALTER TABLE `salidasdedinero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposdeservicios`
--

DROP TABLE IF EXISTS `tiposdeservicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposdeservicios` (
  `idTipo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposdeservicios`
--

LOCK TABLES `tiposdeservicios` WRITE;
/*!40000 ALTER TABLE `tiposdeservicios` DISABLE KEYS */;
INSERT INTO `tiposdeservicios` VALUES (1,'otro');
/*!40000 ALTER TABLE `tiposdeservicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaccionesmiembro`
--

DROP TABLE IF EXISTS `transaccionesmiembro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaccionesmiembro` (
  `idmiembro` int(11) NOT NULL,
  `movimiento` varchar(15) NOT NULL,
  `cantidad` double NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idcorte` int(11) NOT NULL,
  KEY `idcorte` (`idcorte`),
  CONSTRAINT `transaccionesmiembro_ibfk_1` FOREIGN KEY (`idcorte`) REFERENCES `cortesdecaja` (`idcorte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaccionesmiembro`
--

LOCK TABLES `transaccionesmiembro` WRITE;
/*!40000 ALTER TABLE `transaccionesmiembro` DISABLE KEYS */;
INSERT INTO `transaccionesmiembro` VALUES (20,'Registro',200,'2015-08-17 07:02:18',2),(3,'Recarga',1000,'2015-08-17 08:11:19',3),(21,'Registro',200,'2015-08-18 04:37:04',5),(21,'Recarga',100,'2015-08-18 04:45:09',5),(17,'Recarga',100,'2015-08-18 17:12:59',8),(22,'Registro',200,'2015-08-24 18:03:14',1),(23,'Registro',200,'2015-09-04 20:36:28',1);
/*!40000 ALTER TABLE `transaccionesmiembro` ENABLE KEYS */;
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
  `password` varchar(40) NOT NULL,
  `tipoUsuario` varchar(20) NOT NULL DEFAULT 'empleado',
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Luis Federico','Carrillo','Ayala','luisin','81b6f50734d17c2cfc160cfc07ec31b9bcd2a91e','empleado'),(2,'Luis Raúl','Martínez','Sáenz','Rols','0ad5381903e2e0f359c69a6883ae9fd3dd2f2859','empleado'),(3,'admin','root','asd','root','dc76e9f0c0006e8f919e0c515c66dbba3982f785','administrador'),(4,'nuevo5','usuario','','new','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684','empleado'),(5,'Otros','Usuario','Nuevo','nuevon','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684','empleado'),(7,'wer4','wer','wer','wer','e3eaee01f47f98216f4160658179420ff5e30f50','empleado');
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

-- Dump completed on 2015-10-01 23:48:37
