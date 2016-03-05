-- MySQL dump 10.13  Distrib 5.6.26, for Win64 (x86_64)
--
-- Host: localhost    Database: jsmigrar
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miembros`
--

LOCK TABLES `miembros` WRITE;
/*!40000 ALTER TABLE `miembros` DISABLE KEYS */;
INSERT INTO `miembros` VALUES (1,'no','no','no','no','no','no',-235,'2015-08-15 05:52:31','no','fd1286353570c5703799ba76999323b7c7447b06'),(2,'Sin Tarjeta','Cliente','Mostrador','','Sin Domicilio','Sin Telefono',-126,'2015-08-15 05:52:31','generico','0d73694e519057b7743fe69cc925fd8cfae127cb');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Administrador','root','root','root','dc76e9f0c0006e8f919e0c515c66dbba3982f785','administrador');
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

-- Dump completed on 2016-03-05  1:21:35
