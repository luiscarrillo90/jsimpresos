-- MySQL dump 10.13  Distrib 5.6.26, for Win64 (x86_64)
--
-- Host: localhost    Database: jsdbluis
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cortesdecaja`
--

LOCK TABLES `cortesdecaja` WRITE;
/*!40000 ALTER TABLE `cortesdecaja` DISABLE KEYS */;
INSERT INTO `cortesdecaja` VALUES (1,'2015-08-06 17:44:50');
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
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
INSERT INTO `detalleabono` VALUES (1,50,'Tarjeta','no','2015-08-06 17:50:15',1),(2,6,'Efectivo','no','2015-08-06 17:50:15',1),(3,5,'Efectivo','no','2015-08-06 17:50:15',1),(4,10,'Efectivo','no','2015-08-06 17:50:15',1),(5,25,'Efectivo','no','2015-08-06 17:50:15',1),(6,50,'Efectivo','no','2015-08-06 17:50:15',1),(7,10,'Efectivo','no','2015-08-06 17:50:15',1),(8,6,'Efectivo','no','2015-08-06 17:50:15',1),(9,20,'Efectivo','no','2015-08-06 17:50:15',1),(10,30,'Efectivo','no','2015-08-06 17:50:15',1),(11,100,'Efectivo','no','2015-08-06 17:50:15',1),(12,100,'Efectivo','no','2015-08-06 17:50:15',1),(13,100,'Efectivo','no','2015-08-06 17:50:15',1),(14,10,'Efectivo','no','2015-08-06 17:50:15',1),(15,10,'Efectivo','no','2015-08-06 17:50:15',1),(16,10,'Efectivo','no','2015-08-06 17:50:15',1),(17,10,'Efectivo','no','2015-08-06 17:50:15',1),(18,10,'Efectivo','no','2015-08-06 17:50:15',1),(19,50,'Efectivo','no','2015-08-06 17:50:15',1),(20,50,'Efectivo','no','2015-08-06 17:50:15',1),(21,20,'Efectivo','no','2015-08-06 17:50:15',1),(11,66,'Tarjeta','no','2015-08-06 17:50:15',1),(4,8,'Efectivo','no','2015-08-06 17:50:15',1),(20,30,'Tarjeta','no','2015-08-06 17:50:15',1),(7,4,'Tarjeta','no','2015-08-06 17:50:15',1),(10,4,'Tarjeta','no','2015-08-06 17:50:15',1),(15,5,'Tarjeta','no','2015-08-06 17:50:15',1),(6,65.5,'Efectivo','no','2015-08-06 17:50:15',1),(22,20,'Tarjeta','no','2015-08-06 17:50:15',1),(23,100,'Efectivo','no','2015-08-06 17:50:15',1),(25,5,'Efectivo','no','2015-08-06 18:26:09',1),(26,5,'Efectivo','no','2015-08-06 18:30:31',1);
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
INSERT INTO `detalleservicios` VALUES (1,'impresiones',0.5,200),(2,'impresiones',0.5,12),(3,'impresiones',0.5,12),(3,'engargolado',1,1),(4,'impresiones',0.5,12),(4,'engargolado',12,1),(5,'impresiones',0.5,12),(5,'engargolado',12,2),(6,'impresiones',0.5,15),(6,'engargolado',12,9),(7,'impresiones',0.5,12),(7,'otro',1,12),(8,'impresiones',0.5,12),(9,'impresiones',0.5,12),(9,'otro',14,1),(10,'impresiones',0.5,45),(10,'otro',12,1),(11,'impresiones',0.5,45),(11,'otro',12,12),(12,'impresiones',0.5,12),(12,'otro',12,7),(12,'otro más ',15,1),(13,'impresiones',0.5,12),(13,'otro',12,7),(13,'otro más ',15,2),(14,'impresiones',0.5,12),(14,'otro',12,1),(15,'impresiones',0.5,12),(15,'otro',10,1),(16,'impresiones',12,1),(17,'otro',12,1),(18,'otro',12,1),(19,'impresiones',0.5,120),(20,'otro',1,100),(21,'otro',1,50),(22,'impresiones',0.5,100),(23,'Impresiones',0.5,200),(24,'impresiones',0.5,15),(25,'otro',1,10),(26,'impresiones',0.5,12);
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
  `domicilio` varchar(40) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `saldo` double NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `nombreUsuario` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`idMiembro`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miembros`
--

LOCK TABLES `miembros` WRITE;
/*!40000 ALTER TABLE `miembros` DISABLE KEYS */;
INSERT INTO `miembros` VALUES (1,'no','no','no','no','no','no',-109,'2015-07-30 17:42:54','no','no'),(2,'12345','Pedro Antonio','Flores','Gonzalez','Falsa no. 123','52-3-15-15',1891,'2015-07-30 17:42:54','elprimero','pass1'),(3,'43215','Juan Roman','Perez','Diaz','Siempreviva no.264','52-3-12-43',3841,'2015-07-30 17:42:54','elsegundo','pass2'),(4,'123456789','Luis Alberto','Chavez','Rodriguez','Barrio Loco No.333','52-2-32-12',891,'2015-07-30 17:42:54','eltercero','pass'),(5,'434224353','Lorenzo','Lamas','Cabas','Venezuela No.32','52-2-33-12',1391,'2015-07-30 17:42:54','elcuarto','pass'),(6,'434224353','Leonardo','Robles','Carmona','Cerro Balnco No.12','52-2-21-32',391,'2015-07-30 17:42:54','elquinto','pass'),(7,'3212345','Leonor','Herrera','Equiua','Calle del cerro No.122','52-3-56-74',4391,'2015-07-30 17:42:54','elsexto','pass'),(8,'3212345','Paola','Saenz','Lopez','Falsa No.122','52-4-23-12',1091,'2015-07-30 17:42:54','elseptimo','pass'),(9,'12345532','Oscar','Madera','De la Cruz','Roble No.9','52-2-43-56',1126,'2015-07-30 17:42:54','eloctavio','pass'),(10,'12345532','Octavio','Mendez','Rosas','Arbol No.34','52-3-56-89',1094,'2015-07-30 17:42:54','elnoveno','pass'),(11,'52536728','Maria','Lopez','Rios','Honduras No.123','52-3-56-89',1236,'2015-07-30 17:42:54','eldecimo','pass'),(12,'3456747','Liliana','Lago','Sosa','Estados Unidos No.123','52-3-56-82',3301,'2015-07-30 17:42:54','elundecimo','pass'),(13,'1231212','Andrea','Palma','Palma','Arrayan no. 12','52-3-12-23',180,'2015-08-03 17:42:26','andreita','pass'),(15,'3456747','Liliana Leonela','Lago','Sosa','Estados Unidos No.123','52-3-56-82',200,'2015-08-02 20:27:10','elundecimo','pass');
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
  `domicilio` varchar(30) NOT NULL,
  PRIMARY KEY (`idNota`),
  KEY `idDocumento` (`idDocumento`),
  KEY `idMiembro` (`idMiembro`),
  CONSTRAINT `notas_ibfk_1` FOREIGN KEY (`idDocumento`) REFERENCES `documentosdepedido` (`idDocumento`),
  CONSTRAINT `notas_ibfk_2` FOREIGN KEY (`idMiembro`) REFERENCES `miembros` (`idMiembro`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notas`
--

LOCK TABLES `notas` WRITE;
/*!40000 ALTER TABLE `notas` DISABLE KEYS */;
INSERT INTO `notas` VALUES (1,'no','no','no','52-3-12-43','2015-07-27 18:15:23','12-jul-2015','',1,3,'si',''),(2,'Artemio','Lozano','Rivas','627-12-3-12-78','2015-07-27 18:15:23','03-jul-2015','',1,1,'si',''),(3,'Luis Roberto','Perez','Loya','785-65-45-78','2015-07-27 18:15:23',NULL,'',1,1,'si',''),(4,'Luis Yolo','Perez','Torres','627-52-8-47-51','2015-07-27 18:15:23',NULL,'',1,1,'si',''),(5,'Luis Federicoo','Carrillo','Ayala','6271137668','2015-08-02 19:59:40',NULL,'El archivo está guardado en la carpeta c://Mis documentos/tareas/index.html',1,1,'si',''),(6,'Luis Rogelio','Pérez','Chavez','627-12-87-95','2015-07-30 19:14:15',NULL,'',1,1,'si',''),(7,'no','no','no','52-2-33-12','2015-07-25 03:41:42','05-jul-2015','',1,5,'no',''),(8,'no','no','no','52-3-12-43','2015-07-25 03:49:05','05-jul-2015','',1,3,'no',''),(9,'Karla','Gonzalez','','35125485','2015-08-02 20:01:22',NULL,'',1,1,'si',''),(10,'no','no','no','52-3-56-74','2015-07-25 03:55:16',NULL,'',1,7,'no',''),(11,'no','no','no','52-3-56-82','2015-07-25 03:57:03',NULL,'',1,12,'no',''),(12,'no','no','no','52-2-33-12','2015-07-25 04:00:11',NULL,'',1,5,'no',''),(13,'no','no','no','52-3-56-89','2015-07-25 04:01:15',NULL,'',1,11,'no',''),(14,'no','no','no','52-3-56-89','2015-07-25 04:12:48',NULL,'',1,11,'no',''),(15,'no','no','no','52-2-32-12','2015-07-25 04:14:21',NULL,'',1,4,'no',''),(16,'no','no','no','52-3-56-82','2015-07-25 04:16:46',NULL,'',1,12,'no',''),(17,'no','no','no','52-3-56-74','2015-07-25 04:17:35',NULL,'',1,7,'no',''),(18,'no','no','no','52-3-56-89','2015-07-25 04:18:58',NULL,'',1,11,'no',''),(19,'no','no','no','52-3-56-89','2015-07-25 04:20:03',NULL,'',1,11,'no',''),(20,'no','no','no','52-3-56-89','2015-07-25 04:21:13',NULL,'',1,10,'no',''),(21,'no','no','no','52-2-43-56','2015-07-25 04:23:46',NULL,'',1,9,'no',''),(22,'no','no','no','52-3-12-23','2015-08-03 17:42:26',NULL,'',1,13,'no',''),(23,'Alberto','Hinojos','','666','2015-08-03 17:45:18',NULL,'No hay observaciones',1,1,'no',''),(24,'no','no','no','52-2-33-12','2015-08-06 18:24:44',NULL,'',1,5,'no',''),(25,'no','no','no','52-3-56-74','2015-08-06 18:26:08',NULL,'',1,7,'no',''),(26,'no','no','no','52-2-21-32','2015-08-06 18:30:30',NULL,'',1,6,'no','');
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
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Luis Federico','Carrillo','Ayala','luisin','pass'),(2,'Luis Raúl','Martínez','Sáenz','Rols','rolspass');
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

-- Dump completed on 2015-08-12  0:30:12
