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
-- Table structure for table `cortesdecaja`
--

DROP TABLE IF EXISTS `cortesdecaja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cortesdecaja` (
  `idcorte` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcorte`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cortesdecaja`
--

LOCK TABLES `cortesdecaja` WRITE;
/*!40000 ALTER TABLE `cortesdecaja` DISABLE KEYS */;
INSERT INTO `cortesdecaja` VALUES (1,'2015-08-06 17:44:50'),(2,'2015-08-17 07:02:49'),(3,'2015-08-17 08:11:41'),(4,'2015-08-17 08:21:36'),(5,'2015-08-18 04:49:19'),(6,'2015-08-18 05:05:06'),(7,'2015-08-18 17:11:36');
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
INSERT INTO `detalleabono` VALUES (1,50,'Tarjeta','no','2015-08-06 17:50:15',2),(2,6,'Efectivo','no','2015-08-06 17:50:15',2),(3,5,'Efectivo','no','2015-08-06 17:50:15',2),(4,10,'Efectivo','no','2015-08-06 17:50:15',2),(5,25,'Efectivo','no','2015-08-06 17:50:15',2),(6,50,'Efectivo','no','2015-08-06 17:50:15',2),(7,10,'Efectivo','no','2015-08-06 17:50:15',2),(8,6,'Efectivo','no','2015-08-06 17:50:15',2),(9,20,'Efectivo','no','2015-08-06 17:50:15',2),(10,30,'Efectivo','no','2015-08-06 17:50:15',2),(11,100,'Efectivo','no','2015-08-06 17:50:15',2),(12,100,'Efectivo','no','2015-08-06 17:50:15',2),(13,100,'Efectivo','no','2015-08-06 17:50:15',2),(14,10,'Efectivo','no','2015-08-06 17:50:15',2),(15,10,'Efectivo','no','2015-08-06 17:50:15',2),(16,10,'Efectivo','no','2015-08-06 17:50:15',2),(17,10,'Efectivo','no','2015-08-06 17:50:15',2),(18,10,'Efectivo','no','2015-08-06 17:50:15',2),(19,50,'Efectivo','no','2015-08-06 17:50:15',2),(20,50,'Efectivo','no','2015-08-06 17:50:15',2),(21,20,'Efectivo','no','2015-08-06 17:50:15',2),(11,66,'Tarjeta','no','2015-08-06 17:50:15',2),(4,8,'Efectivo','no','2015-08-06 17:50:15',2),(20,30,'Tarjeta','no','2015-08-06 17:50:15',2),(7,4,'Tarjeta','no','2015-08-06 17:50:15',2),(10,4,'Tarjeta','no','2015-08-06 17:50:15',2),(15,5,'Tarjeta','no','2015-08-06 17:50:15',2),(6,65.5,'Efectivo','no','2015-08-06 17:50:15',2),(22,20,'Tarjeta','no','2015-08-06 17:50:15',2),(23,100,'Efectivo','no','2015-08-06 17:50:15',2),(25,5,'Efectivo','no','2015-08-06 18:26:09',2),(26,5,'Efectivo','no','2015-08-06 18:30:31',2),(27,50,'Efectivo','no','2015-08-13 19:14:25',2),(28,20,'Efectivo','no','2015-08-13 19:30:46',2),(29,20,'Efectivo','no','2015-08-13 19:33:11',2),(30,50,'Efectivo','no','2015-08-13 19:42:56',2),(31,3,'Efectivo','no','2015-08-13 20:13:45',2),(32,1,'Efectivo','no','2015-08-14 04:26:24',2),(33,1,'Efectivo','no','2015-08-14 05:10:14',2),(34,1,'Efectivo','no','2015-08-14 05:11:52',2),(35,1,'Efectivo','no','2015-08-14 05:15:16',2),(36,1,'Efectivo','no','2015-08-14 05:17:45',2),(37,1,'Efectivo','no','2015-08-14 05:19:03',2),(38,1,'Efectivo','no','2015-08-14 05:19:21',2),(39,2,'Efectivo','no','2015-08-14 05:25:19',2),(40,1,'Efectivo','no','2015-08-14 05:31:57',2),(41,1,'Efectivo','no','2015-08-14 05:33:11',2),(42,1,'Efectivo','no','2015-08-14 05:39:47',2),(43,1,'Efectivo','no','2015-08-14 19:54:51',2),(44,50,'Efectivo','no','2015-08-15 02:01:21',2),(44,25,'Efectivo','no','2015-08-15 02:14:53',2),(44,25,'Tarjeta','no','2015-08-15 02:15:06',2),(39,1,'Efectivo','no','2015-08-15 03:07:01',2),(39,2,'Efectivo','no','2015-08-15 03:07:23',2),(24,7,'Efectivo','no','2015-08-15 03:11:30',2),(38,2,'Efectivo','no','2015-08-15 03:19:17',2),(38,2,'Efectivo','no','2015-08-15 03:19:30',2),(5,5,'Efectivo','no','2015-08-15 04:09:51',2),(1,50,'Tarjeta','no','2015-08-15 04:11:38',2),(45,20,'Efectivo','no','2015-08-15 05:51:25',2),(45,80,'Efectivo','no','2015-08-15 05:52:11',2),(45,1,'Tarjeta','no','2015-08-15 05:52:31',2),(46,25,'Tarjeta','no','2015-08-15 05:56:25',2),(47,1,'Efectivo','no','2015-08-15 05:58:49',2),(48,1,'Efectivo','no','2015-08-15 06:00:04',2),(49,2,'Efectivo','no','2015-08-15 06:00:47',2),(50,1,'Efectivo','no','2015-08-15 06:50:53',2),(51,50,'Tarjeta','no','2015-08-15 07:24:38',2),(52,1,'Efectivo','no','2015-08-18 04:55:33',6),(53,1,'Efectivo','no','2015-08-18 04:59:21',6),(54,1,'Tarjeta','no','2015-08-18 04:59:44',6),(55,1,'Efectivo','no','2015-08-18 05:00:20',6),(51,50,'Tarjeta','no','2015-08-18 05:03:44',6),(56,50,'Efectivo','no','2015-08-18 17:11:04',7),(56,25,'Efectivo','no','2015-08-18 17:13:35',1),(56,25,'Efectivo','no','2015-08-18 17:13:47',1),(57,1,'Efectivo','no','2015-08-20 18:31:59',1);
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
  KEY `idNota` (`idNota`),
  CONSTRAINT `detalleservicios_ibfk_1` FOREIGN KEY (`idNota`) REFERENCES `notas` (`idNota`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleservicios`
--

LOCK TABLES `detalleservicios` WRITE;
/*!40000 ALTER TABLE `detalleservicios` DISABLE KEYS */;
INSERT INTO `detalleservicios` VALUES (1,'impresiones',0.5,200),(2,'impresiones',0.5,12),(3,'impresiones',0.5,12),(3,'engargolado',1,1),(4,'impresiones',0.5,12),(4,'engargolado',12,1),(5,'impresiones',0.5,12),(5,'engargolado',12,2),(6,'impresiones',0.5,15),(6,'engargolado',12,9),(7,'impresiones',0.5,12),(7,'otro',1,12),(8,'impresiones',0.5,12),(9,'impresiones',0.5,12),(9,'otro',14,1),(10,'impresiones',0.5,45),(10,'otro',12,1),(11,'impresiones',0.5,45),(11,'otro',12,12),(12,'impresiones',0.5,12),(12,'otro',12,7),(12,'otro más ',15,1),(13,'impresiones',0.5,12),(13,'otro',12,7),(13,'otro más ',15,2),(14,'impresiones',0.5,12),(14,'otro',12,1),(15,'impresiones',0.5,12),(15,'otro',10,1),(16,'impresiones',12,1),(17,'otro',12,1),(18,'otro',12,1),(19,'impresiones',0.5,120),(20,'otro',1,100),(21,'otro',1,50),(22,'impresiones',0.5,100),(23,'Impresiones',0.5,200),(24,'impresiones',0.5,15),(25,'otro',1,10),(26,'impresiones',0.5,12),(27,'impresiones',1,100),(28,'imp',5,5),(29,'impr',5,5),(30,'impr',1,100),(31,'impresiones',1,3),(32,'impresion',0.5,2),(33,'asd',1,1),(34,'as',1,1),(35,'Impresión',1,1),(36,'asd',1,1),(37,'asd',1,1),(38,'asd',1,1),(38,'ok',2,2),(39,'asd',2,2),(40,'donde sea',1,1),(41,'nuevo',1,1),(42,'aw',1,1),(43,'impresion',1,1),(44,'Vida',100,1),(45,'sosa',1,1),(45,'otro',100,1),(46,'impresiones',0.5,100),(47,'asd',1,1),(48,'simon',1,1),(49,'otro',1,1),(49,'simon',1,1),(50,'today',1,1),(51,'disco',100,1),(52,'lolquesea',1,1),(53,'asd',1,1),(54,'sdf',1,1),(55,'asd',1,1),(56,'impresion',0.5,200),(57,'loquesea',1,1);
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
  `domicilio` varchar(70) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `saldo` double NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nombreUsuario` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`idMiembro`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miembros`
--

LOCK TABLES `miembros` WRITE;
/*!40000 ALTER TABLE `miembros` DISABLE KEYS */;
INSERT INTO `miembros` VALUES (1,'no','no','no','no','no','no',-235,'2015-08-15 05:52:31','no','no'),(2,'Sin Tarjeta','Cliente','Mostrador','','Sin Domicilio','Sin Telefono',-126,'2015-08-15 05:52:31','generico','generico'),(3,'43215','Juan Roman','Perez','Diaz','Siempreviva no.264','52-3-12-43',5785.5,'2015-08-15 05:52:31','elsegundo','pass2'),(4,'123456789','Luis Alberto','Spinneta','Rodriguez','Barrio Loco No.333','52-2-32-12',765,'2015-08-15 05:52:31','eltercero','pass'),(5,'434224353','Lorenzo','Lamas','Cabas','Venezuela No.32','52-2-33-12',1265,'2015-08-15 05:52:31','elcuarto','pass'),(6,'434224353','Leonardo','Robles','Carmona','Cerro Balnco No.12','52-2-21-32',265,'2015-08-15 05:52:31','elquinto','pass'),(7,'3212345','Leonor','Herrera','Equiua','Calle del cerro No.122','52-3-56-74',4265,'2015-08-15 05:52:31','elsexto','pass'),(8,'3212345','Paola','Saenz','Lopez','Falsa No.122','52-4-23-12',965,'2015-08-15 05:52:31','elseptimo','pass'),(9,'12345532','Oscar','Madera','De la Cruz','Roble No.9','52-2-43-56',1000,'2015-08-15 05:52:31','eloctavio','pass'),(10,'12345532','Octavio','Mendez','Rosas','Arbol No.34','52-3-56-89',968,'2015-08-15 05:52:31','elnoveno','pass'),(11,'52536728','Maria','Lopez','Rios','Honduras No.123','52-3-56-89',1110,'2015-08-15 05:52:31','eldecimo','pass'),(12,'3456747','Liliana','Lago','Sosa','Estados Unidos No.123','52-3-56-82',3174,'2015-08-15 05:52:31','elundecimo','pass'),(13,'1231212','Andrea','Palma','Palma','Arrayan no. 12','52-3-12-23',54,'2015-08-15 05:52:31','andreita','pass'),(15,'3456747','Liliana Leonela','Lago','Sosa','Estados Unidos No.123','52-3-56-82',74,'2015-08-15 05:52:31','elundecimo','pass'),(16,'10410572','Luis Raúl','Martinez','Saenz','Rio Tamesis','23',24,'2015-08-15 07:24:38','rols.mart','rolspass'),(17,'102938','Gustavo','Ceratí','','Calle azul','123234',149,'2015-08-15 05:56:25','electrico','pass'),(19,'3456','New','Subject','','sd','34',150,'2015-08-17 07:00:44','nuevo','pass'),(20,'3459','New','Subject','','asd','34',150,'2015-08-17 07:02:18','nuevo','pass'),(21,'345','asd','asd','','asd','23',250,'2015-08-18 04:37:04','asd','pass');
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
  `observaciones` varchar(200) DEFAULT NULL,
  `idDocumento` int(11) NOT NULL,
  `idMiembro` int(11) NOT NULL,
  `terminado` varchar(4) DEFAULT 'no',
  `domicilio` varchar(70) NOT NULL,
  PRIMARY KEY (`idNota`),
  KEY `idDocumento` (`idDocumento`),
  KEY `idMiembro` (`idMiembro`),
  CONSTRAINT `notas_ibfk_1` FOREIGN KEY (`idDocumento`) REFERENCES `documentosdepedido` (`idDocumento`),
  CONSTRAINT `notas_ibfk_2` FOREIGN KEY (`idMiembro`) REFERENCES `miembros` (`idMiembro`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notas`
--

LOCK TABLES `notas` WRITE;
/*!40000 ALTER TABLE `notas` DISABLE KEYS */;
INSERT INTO `notas` VALUES (1,'no','no','no','52-3-12-43','2015-07-27 18:15:23','12-jul-2015','',1,3,'si',''),(2,'Artemio','Lozano','Rivas','627-12-3-12-78','2015-07-27 18:15:23','03-jul-2015','',1,1,'si',''),(3,'Luis Roberto','Perez','Loya','785-65-45-78','2015-07-27 18:15:23',NULL,'',1,1,'si',''),(4,'Luis Yolo','Perez','Torres','627-52-8-47-51','2015-07-27 18:15:23',NULL,'',1,1,'si',''),(5,'Luis Federicoo','Carrillo','Ayala','6271137668','2015-08-02 19:59:40',NULL,'El archivo está guardado en la carpeta c://Mis documentos/tareas/index.html',1,1,'si',''),(6,'Luis Rogelio','Pérez','Chavez','627-12-87-95','2015-07-30 19:14:15',NULL,'',1,1,'si',''),(7,'no','no','no','52-2-33-12','2015-07-25 03:41:42','05-jul-2015','',1,5,'no',''),(8,'no','no','no','52-3-12-43','2015-07-25 03:49:05','05-jul-2015','',1,3,'no',''),(9,'Karla','Gonzalez','','35125485','2015-08-02 20:01:22',NULL,'',1,1,'si',''),(10,'no','no','no','52-3-56-74','2015-07-25 03:55:16',NULL,'',1,7,'no',''),(11,'no','no','no','52-3-56-82','2015-07-25 03:57:03',NULL,'',1,12,'no',''),(12,'no','no','no','52-2-33-12','2015-07-25 04:00:11',NULL,'',1,5,'no',''),(13,'no','no','no','52-3-56-89','2015-07-25 04:01:15',NULL,'',1,11,'no',''),(14,'no','no','no','52-3-56-89','2015-07-25 04:12:48',NULL,'',1,11,'no',''),(15,'no','no','no','52-2-32-12','2015-07-25 04:14:21',NULL,'',1,4,'no',''),(16,'no','no','no','52-3-56-82','2015-07-25 04:16:46',NULL,'',1,12,'no',''),(17,'no','no','no','52-3-56-74','2015-07-25 04:17:35',NULL,'',1,7,'no',''),(18,'no','no','no','52-3-56-89','2015-07-25 04:18:58',NULL,'',1,11,'no',''),(19,'no','no','no','52-3-56-89','2015-07-25 04:20:03',NULL,'',1,11,'no',''),(20,'no','no','no','52-3-56-89','2015-07-25 04:21:13',NULL,'',1,10,'no',''),(21,'no','no','no','52-2-43-56','2015-07-25 04:23:46',NULL,'',1,9,'no',''),(22,'no','no','no','52-3-12-23','2015-08-03 17:42:26',NULL,'',1,13,'no',''),(23,'Alberto','Hinojos','','666','2015-08-03 17:45:18',NULL,'No hay observaciones',1,1,'no',''),(24,'no','no','no','52-2-33-12','2015-08-06 18:24:44',NULL,'',1,5,'no',''),(25,'no','no','no','52-3-56-74','2015-08-06 18:26:08',NULL,'',1,7,'no',''),(26,'no','no','no','52-2-21-32','2015-08-06 18:30:30',NULL,'',1,6,'no',''),(27,'no','no','no','23','2015-08-13 19:14:24','13/08/2015','mandar wp',1,16,'no','cualquier calle'),(28,'no','no','no','23','2015-08-13 19:30:46','14/08/2015','asd',1,16,'no','cualquier calle'),(29,'cualquier','cliente','para probar','666','2015-08-13 19:33:11','14/08/2015','dfgh',1,1,'no','cualquier calle'),(30,'no','no','no','23','2015-08-13 19:42:56','14/08/2015','zxds',1,16,'no','cualquier calle'),(31,'no','no','no','52-2-32-12','2015-08-13 20:13:45','13/08/2015','asd',1,4,'no','cualquier calle'),(32,'no','no','no','Sin Telefono','2015-08-14 04:26:23','13/08/2015','mandar wp',1,2,'si','cualquier calle'),(33,'no','no','no','23','2015-08-14 05:10:14','13/08/2015','',1,16,'si','etc'),(34,'no','no','no','23','2015-08-14 05:11:51','14/08/2015','',1,16,'si','etc'),(35,'no','no','no','23','2015-08-14 05:15:16','13/08/2015','',1,16,'si','Rio Tamesis'),(36,'no','no','no','Sin Telefono','2015-08-14 05:17:45','13/08/2015','generico',1,2,'si','Sin Domicilio'),(37,'no','no','no','Sin Telefono','2015-08-14 05:19:03','13/08/2015','',1,2,'si','Sin Domicilio'),(38,'no','no','no','Sin Telefono','2015-08-14 05:19:21','13/08/2015','',1,2,'si','Sin Domicilio'),(39,'Nuevo','Prueba','','no tiene','2015-08-14 05:25:19','13/08/2015','',1,1,'si','algo'),(40,'no sé','asd','','42','2015-08-14 05:31:57','13/08/2015','nuevo',1,1,'si','asd'),(41,'otro','cabron','','3','2015-08-14 05:33:11','13/08/2015','',1,1,'si','calle'),(42,'prueba','ultima','','23','2015-08-14 05:39:47','13/08/2015','',1,1,'si','funciona'),(43,'no','no','no','123234','2015-08-14 19:54:51','14/08/2015','idea',1,17,'si','Calle azul'),(44,'no','no','no','123234','2015-08-15 02:01:21','15/08/2015','',1,17,'si','Calle azul'),(45,'no','no','no','52-3-56-82','2015-08-15 05:51:25','15/08/2015','veinte',1,12,'si','Estados Unidos No.123'),(46,'no','no','no','123234','2015-08-15 05:56:25','15/08/2015','',1,17,'no','Calle azul'),(47,'no','no','no','52-2-33-12','2015-08-15 05:58:49','15/08/2015','',1,5,'no','Venezuela No.32'),(48,'no','no','no','52-2-32-12','2015-08-15 06:00:04',NULL,'',1,4,'no','Barrio Loco No.333'),(49,'no','no','no','23','2015-08-15 06:00:47','16/08/2015','ad',1,16,'no','Rio Tamesis'),(50,'no','no','no','52-2-33-12','2015-08-15 06:50:53','15/08/2015','today',1,5,'no','Venezuela No.32'),(51,'no','no','no','23','2015-08-15 07:24:38','15/08/2015','',1,16,'no','Rio Tamesis'),(52,'no','no','no','Sin Telefono','2015-08-18 04:55:33','17/08/2015','asd',1,2,'si','Sin Domicilio'),(53,'no','no','no','Sin Telefono','2015-08-18 04:59:21','17/08/2015','sdf',1,2,'no','Sin Domicilio'),(54,'no','no','no','52-3-56-82','2015-08-18 04:59:44','17/08/2015','sdf',1,12,'no','Estados Unidos No.123'),(55,'simn','bolivar','','233','2015-08-18 05:00:20','17/08/2015','dfg',1,1,'no','asd'),(56,'no','no','no','Sin Telefono','2015-08-18 17:11:04','19/08/2015','mandar wp',1,2,'no','Sin Domicilio'),(57,'no','no','no','Sin Telefono','2015-08-20 18:31:59','20/08/2015','asd',1,2,'no','Sin Domicilio');
/*!40000 ALTER TABLE `notas` ENABLE KEYS */;
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
INSERT INTO `transaccionesmiembro` VALUES (20,'Registro',200,'2015-08-17 07:02:18',2),(3,'Recarga',1000,'2015-08-17 08:11:19',3),(21,'Registro',200,'2015-08-18 04:37:04',5),(21,'Recarga',100,'2015-08-18 04:45:09',5),(17,'Recarga',100,'2015-08-18 17:12:59',1);
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
  `password` varchar(20) NOT NULL,
  `tipoUsuario` varchar(20) NOT NULL DEFAULT 'empleado',
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Luis Federico','Carrillo','Ayala','luisin','pass','empleado'),(2,'Luis Raúl','Martínez','Sáenz','Rols','rolspass','empleado'),(3,'admin','root','','root','root','Administrador');
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

-- Dump completed on 2015-08-20 12:50:52
