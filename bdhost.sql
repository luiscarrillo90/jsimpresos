-- MySQL dump 10.16  Distrib 10.1.9-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: jsdb2
-- ------------------------------------------------------
-- Server version	10.1.9-MariaDB

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
-- Table structure for table `mascotas`
--

DROP TABLE IF EXISTS `mascotas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mascotas` (
  `idMascota` int(11) NOT NULL AUTO_INCREMENT,
  `idDueno` int(11) NOT NULL,
  `idTipoMascota` int(11) NOT NULL,
  `idRaza` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `sexo` varchar(10) NOT NULL,
  `edadRegistro` varchar(20) NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `colorPelaje` varchar(30) NOT NULL,
  `urlFoto` varchar(100) NOT NULL,
  `urlCodigoQr` varchar(100) NOT NULL,
  PRIMARY KEY (`idMascota`),
  KEY `idDueno` (`idDueno`),
  KEY `idTipoMascota` (`idTipoMascota`),
  KEY `idRaza` (`idRaza`),
  CONSTRAINT `mascotas_ibfk_1` FOREIGN KEY (`idDueno`) REFERENCES `usuariosmascotas` (`idUsuario`),
  CONSTRAINT `mascotas_ibfk_2` FOREIGN KEY (`idTipoMascota`) REFERENCES `tipomascota` (`idTipoMascota`),
  CONSTRAINT `mascotas_ibfk_3` FOREIGN KEY (`idRaza`) REFERENCES `razas` (`idRaza`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mascotas`
--

LOCK TABLES `mascotas` WRITE;
/*!40000 ALTER TABLE `mascotas` DISABLE KEYS */;
/*!40000 ALTER TABLE `mascotas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `razas`
--

DROP TABLE IF EXISTS `razas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `razas` (
  `idRaza` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoMascota` int(11) NOT NULL,
  `nombreRaza` varchar(30) NOT NULL,
  PRIMARY KEY (`idRaza`),
  KEY `idTipoMascota` (`idTipoMascota`),
  CONSTRAINT `razas_ibfk_1` FOREIGN KEY (`idTipoMascota`) REFERENCES `tipomascota` (`idTipoMascota`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `razas`
--

LOCK TABLES `razas` WRITE;
/*!40000 ALTER TABLE `razas` DISABLE KEYS */;
/*!40000 ALTER TABLE `razas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipomascota`
--

DROP TABLE IF EXISTS `tipomascota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipomascota` (
  `idTipoMascota` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(30) NOT NULL,
  PRIMARY KEY (`idTipoMascota`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipomascota`
--

LOCK TABLES `tipomascota` WRITE;
/*!40000 ALTER TABLE `tipomascota` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipomascota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuariosmascotas`
--

DROP TABLE IF EXISTS `usuariosmascotas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuariosmascotas` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(30) NOT NULL,
  `apPaterno` varchar(30) NOT NULL,
  `apMaterno` varchar(30) DEFAULT NULL,
  `nombreUsuario` varchar(30) NOT NULL,
  `password` text NOT NULL,
  `calle` varchar(30) NOT NULL,
  `numero` int(11) NOT NULL,
  `colonia` varchar(30) NOT NULL,
  `localidad` varchar(30) NOT NULL,
  `estado` varchar(20) NOT NULL,
  `telefono` varchar(30) DEFAULT NULL,
  `celular` varchar(30) DEFAULT NULL,
  `correo` varchar(30) DEFAULT NULL,
  `restricciones` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuariosmascotas`
--

LOCK TABLES `usuariosmascotas` WRITE;
/*!40000 ALTER TABLE `usuariosmascotas` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuariosmascotas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-27 10:05:01
