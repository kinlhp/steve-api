USE `steve`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: steve
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `condicao_pagamento`
--

LOCK TABLES `condicao_pagamento` WRITE;
/*!40000 ALTER TABLE `condicao_pagamento` DISABLE KEYS */;
INSERT INTO `condicao_pagamento` (`forma_pagamento`,`usuario_criacao`,`descricao`) VALUES (4,0,'À VISTA');
/*!40000 ALTER TABLE `condicao_pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `credencial`
--

LOCK TABLES `credencial` WRITE;
/*!40000 ALTER TABLE `credencial` DISABLE KEYS */;
INSERT INTO `credencial` (`id`,`funcionario`,`usuario_criacao`,`perfil_administrador`,`perfil_sistema`,`usuario`) VALUES (0,0,0,'','','steve');
/*!40000 ALTER TABLE `credencial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `forma_pagamento`
--

LOCK TABLES `forma_pagamento` WRITE;
/*!40000 ALTER TABLE `forma_pagamento` DISABLE KEYS */;
INSERT INTO `forma_pagamento` (`usuario_criacao`,`descricao`) VALUES (0,'CARTÃO DE CRÉDITO'),(0,'CARTÃO DE DÉBITO'),(0,'CHEQUE'),(0,'DINHEIRO'),(0,'PERMUTA'),(0,'PROMISSÓRIA');
/*!40000 ALTER TABLE `forma_pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` (`id`,`usuario_criacao`,`perfil_cliente`,`perfil_usuario`,`cnpj_cpf`,`fantasia_sobrenome`,`ie_rg`,`nome_razao`,`tipo`) VALUES (0,0,'\0','','0','steve','0','Kin.LHP® Software, Inc.','SISTEMA');
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `uf`
--

LOCK TABLES `uf` WRITE;
/*!40000 ALTER TABLE `uf` DISABLE KEYS */;
INSERT INTO `uf` (`ibge`,`usuario_criacao`,`nome`,`sigla`) VALUES (0,0,'EXTERIOR','EX'),(11,0,'RONDÔNIA','RO'),(12,0,'ACRE','AC'),(13,0,'AMAZONAS','AM'),(14,0,'RORAIMA','RR'),(15,0,'PARÁ','PA'),(16,0,'AMAPÁ','AP'),(17,0,'TOCANTINS','TO'),(21,0,'MARANHÃO','MA'),(22,0,'PIAUÍ','PI'),(23,0,'CEARÁ','CE'),(24,0,'RIO GRANDE DO NORTE','RN'),(25,0,'PARAÍBA','PB'),(26,0,'PERNAMBUCO','PE'),(27,0,'ALAGOAS','AL'),(28,0,'SERGIPE','SE'),(29,0,'BAHIA','BA'),(31,0,'MINAS GERAIS','MG'),(32,0,'ESPÍRITO SANTO','ES'),(33,0,'RIO DE JANEIRO','RJ'),(35,0,'SÃO PAULO','SP'),(41,0,'PARANÁ','PR'),(42,0,'SANTA CATARINA','SC'),(43,0,'RIO GRANDE DO SUL','RS'),(50,0,'MATO GROSSO DO SUL','MS'),(51,0,'MATO GROSSO','MT'),(52,0,'GOIÁS','GO'),(53,0,'DISTRITO FEDERAL','DF');
/*!40000 ALTER TABLE `uf` ENABLE KEYS */;
UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-26 21:28:52
