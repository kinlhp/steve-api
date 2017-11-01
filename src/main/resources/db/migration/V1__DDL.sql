CREATE SCHEMA IF NOT EXISTS `steve` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */;
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

DROP TABLE IF EXISTS `condicao_pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condicao_pagamento` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `forma_pagamento` tinyint(3) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `periodo_entre_parcelas` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `quantidade_parcelas` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `descricao` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `forma_pagamento_descricao_UNIQUE` (`forma_pagamento`,`descricao`),
  KEY `FK_condicao_pagamento_forma_pagamento_INDEX` (`forma_pagamento`),
  KEY `FK_condicao_pagamento_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_condicao_pagamento_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `descricao_INDEX` (`descricao`),
  CONSTRAINT `FK_condicao_pagamento_forma_pagamento` FOREIGN KEY (`forma_pagamento`) REFERENCES `forma_pagamento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_condicao_pagamento_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_condicao_pagamento_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `conta_pagar`
--

DROP TABLE IF EXISTS `conta_pagar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conta_pagar` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cedente` smallint(5) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `numero_parcela` smallint(5) unsigned NOT NULL DEFAULT '0',
  `montante_pago` decimal(9,2) unsigned NOT NULL DEFAULT '0.00',
  `saldo_devedor` decimal(9,2) unsigned NOT NULL,
  `valor` decimal(9,2) unsigned NOT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_emissao` date DEFAULT NULL,
  `data_vencimento` date NOT NULL,
  `mes_referente` date NOT NULL COMMENT 'YYYY-MM',
  `fatura` varchar(32) NOT NULL,
  `observacao` varchar(256) DEFAULT NULL,
  `situacao` enum('ABERTO','AMORTIZADO','BAIXADO','CANCELADO') NOT NULL DEFAULT 'ABERTO',
  PRIMARY KEY (`id`),
  UNIQUE KEY `fatura_cedente_numero_parcela_UNIQUE` (`fatura`,`cedente`,`numero_parcela`),
  KEY `FK_conta_pagar_cedente_INDEX` (`cedente`),
  KEY `FK_conta_pagar_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_conta_pagar_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `data_vencimento_INDEX` (`data_vencimento`),
  KEY `fatura_INDEX` (`fatura`),
  KEY `situacao_INDEX` (`situacao`),
  CONSTRAINT `FK_conta_pagar_cedente` FOREIGN KEY (`cedente`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_pagar_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_pagar_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `conta_receber`
--

DROP TABLE IF EXISTS `conta_receber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conta_receber` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `condicao_pagamento` smallint(5) unsigned NOT NULL,
  `sacado` smallint(5) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `ordem` bigint(20) unsigned NOT NULL,
  `numero_parcela` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `montante_pago` decimal(9,2) unsigned NOT NULL DEFAULT '0.00',
  `saldo_devedor` decimal(9,2) unsigned NOT NULL,
  `valor` decimal(9,2) unsigned NOT NULL COMMENT '	',
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_vencimento` date DEFAULT NULL,
  `observacao` varchar(256) DEFAULT NULL,
  `situacao` enum('ABERTO','AMORTIZADO','BAIXADO','CANCELADO') NOT NULL DEFAULT 'ABERTO',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ordem_numero_parcela_UNIQUE` (`ordem`,`numero_parcela`),
  KEY `FK_conta_receber_condicao_pagamento_INDEX` (`condicao_pagamento`),
  KEY `FK_conta_receber_ordem_INDEX` (`ordem`),
  KEY `FK_conta_receber_sacado_INDEX` (`sacado`),
  KEY `FK_conta_receber_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_conta_receber_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `data_vencimento_INDEX` (`data_vencimento`),
  KEY `situacao_INDEX` (`situacao`),
  CONSTRAINT `FK_conta_receber_condicao_pagamento` FOREIGN KEY (`condicao_pagamento`) REFERENCES `condicao_pagamento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_receber_ordem` FOREIGN KEY (`ordem`) REFERENCES `ordem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_receber_sacado` FOREIGN KEY (`sacado`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_receber_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_receber_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credencial`
--

DROP TABLE IF EXISTS `credencial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credencial` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `funcionario` smallint(5) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `perfil_administrador` bit(1) NOT NULL DEFAULT b'0' COMMENT 'b''0''=Não\nb''1''=Sim',
  `perfil_padrao` bit(1) NOT NULL DEFAULT b'1' COMMENT 'b''0''=Não\nb''1''=Sim',
  `perfil_sistema` bit(1) NOT NULL DEFAULT b'0' COMMENT 'b''0''=Não\nb''1''=Sim',
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `senha` char(128) NOT NULL DEFAULT '93f4a4e86cf842f2a03cd2eedbcd3c72325d6833fa991b895be40204be651427652c78b9cdbdef7c01f80a0acb58f791c36d49fbaa5738970e83772cea18eba1' COMMENT 'SHA2(''123mudar'',512)',
  `usuario` varchar(16) NOT NULL,
  `situacao` enum('ATIVO','INATIVO') NOT NULL DEFAULT 'ATIVO',
  PRIMARY KEY (`id`),
  UNIQUE KEY `funcionario_UNIQUE` (`funcionario`),
  UNIQUE KEY `usuario_UNIQUE` (`usuario`),
  KEY `FK_credencial_funcionario_INDEX` (`funcionario`),
  KEY `FK_credencial_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_credencial_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  CONSTRAINT `FK_credencial_funcionario` FOREIGN KEY (`funcionario`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_credencial_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_credencial_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pessoa` smallint(5) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `nome_contato` varchar(16) DEFAULT NULL,
  `endereco_eletronico` varchar(64) NOT NULL,
  `tipo` enum('NFE','PESSOAL','PRINCIPAL','OUTRO','TRABALHO') NOT NULL DEFAULT 'PRINCIPAL',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pessoa_tipo_UNIQUE` (`pessoa`,`tipo`),
  KEY `FK_email_pessoa_INDEX` (`pessoa`),
  KEY `FK_email_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_email_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `endereco_eletronico_INDEX` (`endereco_eletronico`),
  CONSTRAINT `FK_email_pessoa` FOREIGN KEY (`pessoa`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_email_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_email_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uf` tinyint(2) unsigned NOT NULL,
  `pessoa` smallint(5) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `ibge` mediumint(8) unsigned DEFAULT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `cep` char(8) NOT NULL,
  `bairro` varchar(64) NOT NULL,
  `cidade` varchar(64) NOT NULL,
  `complemento` varchar(64) DEFAULT NULL,
  `complemento_2` varchar(64) DEFAULT NULL,
  `logradouro` varchar(64) NOT NULL,
  `nome_contato` varchar(16) DEFAULT NULL,
  `numero` varchar(8) NOT NULL DEFAULT 's.n.º',
  `tipo` enum('ENTREGA','PESSOAL','PRINCIPAL','OUTRO','TRABALHO') NOT NULL DEFAULT 'PRINCIPAL',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pessoa_tipo_UNIQUE` (`pessoa`,`tipo`),
  KEY `FK_endereco_pessoa_INDEX` (`pessoa`),
  KEY `FK_endereco_uf_INDEX` (`uf`),
  KEY `FK_endereco_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_endereco_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `cidade_INDEX` (`cidade`),
  CONSTRAINT `FK_endereco_pessoa` FOREIGN KEY (`pessoa`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_endereco_uf` FOREIGN KEY (`uf`) REFERENCES `uf` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_endereco_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_endereco_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forma_pagamento`
--

DROP TABLE IF EXISTS `forma_pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forma_pagamento` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `descricao` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `descricao_UNIQUE` (`descricao`),
  KEY `FK_forma_pagamento_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_forma_pagamento_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  CONSTRAINT `FK_forma_pagamento_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_forma_pagamento_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_ordem_servico`
--

DROP TABLE IF EXISTS `item_ordem_servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_ordem_servico` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `servico` tinyint(3) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `ordem` bigint(20) unsigned NOT NULL,
  `valor_orcamento` decimal(9,2) unsigned NOT NULL DEFAULT '0.00',
  `valor_servico` decimal(9,2) unsigned NOT NULL DEFAULT '0.00',
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_finalizacao_prevista` date DEFAULT NULL,
  `descricao` varchar(1024) DEFAULT NULL,
  `situacao` enum('ABERTO','CANCELADO','FINALIZADO') NOT NULL DEFAULT 'ABERTO',
  PRIMARY KEY (`id`),
  KEY `FK_item_ordem_servico_ordem_INDEX` (`ordem`),
  KEY `FK_item_ordem_servico_servico_INDEX` (`servico`),
  KEY `FK_item_ordem_servico_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_item_ordem_servico_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `situacao_INDEX` (`situacao`),
  CONSTRAINT `FK_item_ordem_servico_ordem` FOREIGN KEY (`ordem`) REFERENCES `ordem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_ordem_servico_servico` FOREIGN KEY (`servico`) REFERENCES `servico` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_ordem_servico_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_ordem_servico_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordem`
--

DROP TABLE IF EXISTS `ordem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordem` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cliente` smallint(5) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `observacao` varchar(256) DEFAULT NULL,
  `situacao` enum('ABERTO','CANCELADO','FINALIZADO','GERADO') NOT NULL DEFAULT 'ABERTO',
  `tipo` enum('ORCAMENTO','ORDEM_SERVICO') NOT NULL DEFAULT 'ORDEM_SERVICO',
  PRIMARY KEY (`id`),
  KEY `FK_ordem_cliente_INDEX` (`cliente`),
  KEY `FK_ordem_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_ordem_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `situacao_INDEX` (`situacao`),
  CONSTRAINT `FK_ordem_cliente` FOREIGN KEY (`cliente`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ordem_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ordem_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pessoa` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `perfil_cliente` bit(1) NOT NULL DEFAULT b'1' COMMENT 'b''0''=Não\nb''1''=Sim',
  `perfil_fornecedor` bit(1) NOT NULL DEFAULT b'0' COMMENT 'b''0''=Não\nb''1''=Sim',
  `perfil_transportador` bit(1) NOT NULL DEFAULT b'0' COMMENT 'b''0''=Não\nb''1''=Sim',
  `perfil_usuario` bit(1) NOT NULL DEFAULT b'0' COMMENT 'b''0''=Não\nb''1''=Sim',
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `abertura_nascimento` date DEFAULT NULL,
  `cnpj_cpf` varchar(14) NOT NULL,
  `fantasia_sobrenome` varchar(64) DEFAULT NULL,
  `ie_rg` varchar(16) NOT NULL DEFAULT 'ISENTO',
  `nome_razao` varchar(128) NOT NULL,
  `situacao` enum('ATIVO','INATIVO') NOT NULL DEFAULT 'ATIVO',
  `tipo` enum('FISICA','JURIDICA','SISTEMA') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cnpj_cpf_UNIQUE` (`cnpj_cpf`),
  KEY `FK_pessoa_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_pessoa_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `fantasia_sobrenome_INDEX` (`fantasia_sobrenome`),
  KEY `nome_razao_INDEX` (`nome_razao`),
  CONSTRAINT `FK_pessoa_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_pessoa_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `steve`.`pessoa_BEFORE_INSERT` BEFORE INSERT ON `pessoa` FOR EACH ROW BEGIN
	DECLARE ie_rg_non_isento_unique CONDITION FOR SQLSTATE '23000';
    IF(EXISTS(SELECT 1 FROM `pessoa` WHERE NEW.`ie_rg` != 'ISENTO' AND `ie_rg` = NEW.`ie_rg`)) THEN
        SET @conflict_message_text = CONCAT('Duplicate entry ''', NEW.`ie_rg`, ''' for key ''ie_rg_UNIQUE''');
        SIGNAL ie_rg_non_isento_unique SET MESSAGE_TEXT = @conflict_message_text, MYSQL_ERRNO = 1062;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `steve`.`pessoa_BEFORE_UPDATE` BEFORE UPDATE ON `pessoa` FOR EACH ROW BEGIN
	DECLARE ie_rg_non_isento_unique CONDITION FOR SQLSTATE '23000';
    IF(EXISTS(SELECT 1 FROM `pessoa` WHERE NEW.`ie_rg` != 'ISENTO' AND `ie_rg` = NEW.`ie_rg`) AND (NEW.`ie_rg` <> OLD.`ie_rg`)) THEN
        SET @conflict_message_text = CONCAT('Duplicate entry ''', NEW.`ie_rg`, ''' for key ''ie_rg_UNIQUE''');
        SIGNAL ie_rg_non_isento_unique SET MESSAGE_TEXT = @conflict_message_text, MYSQL_ERRNO = 1062;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `servico`
--

DROP TABLE IF EXISTS `servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servico` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `descricao` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `descricao_UNIQUE` (`descricao`),
  KEY `FK_servico_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_servico_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  CONSTRAINT `FK_servico_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_servico_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `telefone`
--

DROP TABLE IF EXISTS `telefone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `telefone` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pessoa` smallint(5) unsigned NOT NULL,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `nome_contato` varchar(16) DEFAULT NULL,
  `numero` varchar(16) NOT NULL,
  `tipo` enum('PESSOAL','PRINCIPAL','OUTRO','TRABALHO') NOT NULL DEFAULT 'PRINCIPAL',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pessoa_tipo_UNIQUE` (`pessoa`,`tipo`),
  KEY `FK_telefone_pessoa_INDEX` (`pessoa`),
  KEY `FK_telefone_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_telefone_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  KEY `numero_INDEX` (`numero`),
  CONSTRAINT `FK_telefone_pessoa` FOREIGN KEY (`pessoa`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_telefone_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_telefone_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `uf`
--

DROP TABLE IF EXISTS `uf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uf` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `usuario_criacao` smallint(5) unsigned NOT NULL,
  `usuario_ultima_alteracao` smallint(5) unsigned DEFAULT NULL,
  `ibge` tinyint(2) unsigned NOT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `nome` varchar(19) NOT NULL,
  `sigla` enum('AC','AL','AM','AP','BA','CE','DF','ES','EX','GO','MA','MG','MS','MT','PA','PB','PE','PI','PR','RJ','RN','RO','RR','RS','SC','SE','SP','TO') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ibge_UNIQUE` (`ibge`),
  UNIQUE KEY `nome_UNIQUE` (`nome`),
  UNIQUE KEY `sigla_UNIQUE` (`sigla`),
  KEY `FK_uf_usuario_criacao_INDEX` (`usuario_criacao`),
  KEY `FK_uf_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao`),
  CONSTRAINT `FK_uf_usuario_criacao` FOREIGN KEY (`usuario_criacao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_uf_usuario_ultima_alteracao` FOREIGN KEY (`usuario_ultima_alteracao`) REFERENCES `credencial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-01  0:29:16
