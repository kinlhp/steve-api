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
-- Table structure for table `condicao_pagamento`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `condicao_pagamento`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `quantidade_parcelas`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `conta_pagar`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `conta_pagar`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `usuario_ultima_alteracao`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `conta_receber`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `conta_receber`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `numero_parcela`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credencial`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `credencial`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `perfil_sistema`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `email`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `usuario_ultima_alteracao`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `endereco`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `endereco`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `usuario_ultima_alteracao`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forma_pagamento`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `forma_pagamento`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `usuario_ultima_alteracao`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_ordem_servico`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `item_ordem_servico`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `ordem`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `ordem`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `usuario_ultima_alteracao`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pessoa`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `pessoa`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `perfil_usuario`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servico`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `servico`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `usuario_ultima_alteracao`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `telefone`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `telefone`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `usuario_ultima_alteracao`;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `uf`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE `uf`
ADD COLUMN `versao` tinyint(3) unsigned NOT NULL DEFAULT '0' AFTER `ibge`;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-10  19:58:05
