-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema steve
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `steve` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `steve` ;

-- -----------------------------------------------------
-- Table `steve`.`pessoa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`pessoa` ;

CREATE TABLE IF NOT EXISTS `steve`.`pessoa` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `perfil_cliente` BIT(1) NOT NULL DEFAULT b'1' COMMENT 'b\'0\'=Não\nb\'1\'=Sim',
  `perfil_fornecedor` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'b\'0\'=Não\nb\'1\'=Sim',
  `perfil_transportador` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'b\'0\'=Não\nb\'1\'=Sim',
  `perfil_usuario` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'b\'0\'=Não\nb\'1\'=Sim',
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `abertura_nascimento` DATE NULL,
  `cnpj_cpf` VARCHAR(14) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `fantasia_sobrenome` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `ie_rg` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'ISENTO',
  `nome_razao` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `situacao` ENUM('ATIVO', 'INATIVO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'ATIVO',
  `tipo` ENUM('FISICA', 'JURIDICA', 'SISTEMA') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_pessoa_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_pessoa_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `cnpj_cpf_UNIQUE` (`cnpj_cpf` ASC),
  INDEX `fantasia_sobrenome_INDEX` (`fantasia_sobrenome` ASC),
  INDEX `nome_razao_INDEX` (`nome_razao` ASC),
  CONSTRAINT `FK_pessoa_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_pessoa_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`credencial`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`credencial` ;

CREATE TABLE IF NOT EXISTS `steve`.`credencial` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `funcionario` SMALLINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `perfil_administrador` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'b\'0\'=Não\nb\'1\'=Sim',
  `perfil_padrao` BIT(1) NOT NULL DEFAULT b'1' COMMENT 'b\'0\'=Não\nb\'1\'=Sim',
  `perfil_sistema` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'b\'0\'=Não\nb\'1\'=Sim',
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `senha` CHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT '93f4a4e86cf842f2a03cd2eedbcd3c72325d6833fa991b895be40204be651427652c78b9cdbdef7c01f80a0acb58f791c36d49fbaa5738970e83772cea18eba1' COMMENT 'SHA2(\'123mudar\',512)',
  `usuario` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `situacao` ENUM('ATIVO', 'INATIVO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'ATIVO',
  PRIMARY KEY (`id`),
  INDEX `FK_credencial_funcionario_INDEX` (`funcionario` ASC),
  INDEX `FK_credencial_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_credencial_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `funcionario_UNIQUE` (`funcionario` ASC),
  UNIQUE INDEX `usuario_UNIQUE` (`usuario` ASC),
  CONSTRAINT `FK_credencial_funcionario`
    FOREIGN KEY (`funcionario`)
    REFERENCES `steve`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_credencial_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_credencial_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`uf`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`uf` ;

CREATE TABLE IF NOT EXISTS `steve`.`uf` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `ibge` TINYINT(2) UNSIGNED NOT NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `nome` VARCHAR(19) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `sigla` ENUM('AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'EX', 'GO', 'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR', 'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_uf_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_uf_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `ibge_UNIQUE` (`ibge` ASC),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC),
  UNIQUE INDEX `sigla_UNIQUE` (`sigla` ASC),
  CONSTRAINT `FK_uf_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_uf_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`endereco`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`endereco` ;

CREATE TABLE IF NOT EXISTS `steve`.`endereco` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `uf` TINYINT(2) UNSIGNED NOT NULL,
  `pessoa` SMALLINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `ibge` MEDIUMINT(8) UNSIGNED NULL,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `cep` CHAR(8) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `bairro` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `cidade` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `complemento` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `complemento_2` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `logradouro` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `nome_contato` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `numero` VARCHAR(8) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 's.n.º',
  `tipo` ENUM('ENTREGA', 'PESSOAL', 'PRINCIPAL', 'OUTRO', 'TRABALHO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'PRINCIPAL',
  PRIMARY KEY (`id`),
  INDEX `FK_endereco_pessoa_INDEX` (`pessoa` ASC),
  INDEX `FK_endereco_uf_INDEX` (`uf` ASC),
  INDEX `FK_endereco_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_endereco_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `pessoa_tipo_UNIQUE` (`pessoa` ASC, `tipo` ASC),
  INDEX `cidade_INDEX` (`cidade` ASC),
  CONSTRAINT `FK_endereco_pessoa`
    FOREIGN KEY (`pessoa`)
    REFERENCES `steve`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_endereco_uf`
    FOREIGN KEY (`uf`)
    REFERENCES `steve`.`uf` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_endereco_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_endereco_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`telefone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`telefone` ;

CREATE TABLE IF NOT EXISTS `steve`.`telefone` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `pessoa` SMALLINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `nome_contato` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `numero` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `tipo` ENUM('PESSOAL', 'PRINCIPAL', 'OUTRO', 'TRABALHO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'PRINCIPAL',
  PRIMARY KEY (`id`),
  INDEX `FK_telefone_pessoa_INDEX` (`pessoa` ASC),
  INDEX `FK_telefone_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_telefone_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `pessoa_tipo_UNIQUE` (`pessoa` ASC, `tipo` ASC),
  INDEX `numero_INDEX` (`numero` ASC),
  CONSTRAINT `FK_telefone_pessoa`
    FOREIGN KEY (`pessoa`)
    REFERENCES `steve`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_telefone_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_telefone_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`email`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`email` ;

CREATE TABLE IF NOT EXISTS `steve`.`email` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `pessoa` SMALLINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `nome_contato` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `endereco_eletronico` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `tipo` ENUM('NFE', 'PESSOAL', 'PRINCIPAL', 'OUTRO', 'TRABALHO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'PRINCIPAL',
  PRIMARY KEY (`id`),
  INDEX `FK_email_pessoa_INDEX` (`pessoa` ASC),
  INDEX `FK_email_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_email_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `pessoa_tipo_UNIQUE` (`pessoa` ASC, `tipo` ASC),
  INDEX `endereco_eletronico_INDEX` (`endereco_eletronico` ASC),
  CONSTRAINT `FK_email_pessoa`
    FOREIGN KEY (`pessoa`)
    REFERENCES `steve`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_email_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_email_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`servico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`servico` ;

CREATE TABLE IF NOT EXISTS `steve`.`servico` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `descricao` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_servico_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_servico_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC),
  CONSTRAINT `FK_servico_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_servico_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`ordem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`ordem` ;

CREATE TABLE IF NOT EXISTS `steve`.`ordem` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cliente` SMALLINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NOT NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `observacao` VARCHAR(256) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `situacao` ENUM('ABERTO', 'CANCELADO', 'FINALIZADO', 'GERADO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'ABERTO',
  `tipo` ENUM('ORCAMENTO', 'ORDEM_SERVICO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'ORDEM_SERVICO',
  PRIMARY KEY (`id`),
  INDEX `FK_ordem_cliente_INDEX` (`cliente` ASC),
  INDEX `FK_ordem_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_ordem_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  INDEX `situacao_INDEX` (`situacao` ASC),
  CONSTRAINT `FK_ordem_cliente`
    FOREIGN KEY (`cliente`)
    REFERENCES `steve`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ordem_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ordem_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`item_ordem_servico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`item_ordem_servico` ;

CREATE TABLE IF NOT EXISTS `steve`.`item_ordem_servico` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `servico` TINYINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `ordem` BIGINT UNSIGNED NOT NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `valor_orcamento` DECIMAL(9,2) UNSIGNED NOT NULL DEFAULT 0,
  `valor_servico` DECIMAL(9,2) UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_finalizacao_prevista` DATE NULL,
  `descricao` VARCHAR(1024) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `situacao` ENUM('ABERTO', 'CANCELADO', 'FINALIZADO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'ABERTO',
  PRIMARY KEY (`id`),
  INDEX `FK_item_ordem_servico_ordem_INDEX` (`ordem` ASC),
  INDEX `FK_item_ordem_servico_servico_INDEX` (`servico` ASC),
  INDEX `FK_item_ordem_servico_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_item_ordem_servico_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  INDEX `situacao_INDEX` (`situacao` ASC),
  CONSTRAINT `FK_item_ordem_servico_ordem`
    FOREIGN KEY (`ordem`)
    REFERENCES `steve`.`ordem` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_ordem_servico_servico`
    FOREIGN KEY (`servico`)
    REFERENCES `steve`.`servico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_ordem_servico_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_ordem_servico_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`forma_pagamento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`forma_pagamento` ;

CREATE TABLE IF NOT EXISTS `steve`.`forma_pagamento` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `descricao` VARCHAR(32) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_forma_pagamento_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_forma_pagamento_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC),
  CONSTRAINT `FK_forma_pagamento_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_forma_pagamento_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`condicao_pagamento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`condicao_pagamento` ;

CREATE TABLE IF NOT EXISTS `steve`.`condicao_pagamento` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `forma_pagamento` TINYINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `periodo_entre_parcelas` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `quantidade_parcelas` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `descricao` VARCHAR(32) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_condicao_pagamento_forma_pagamento_INDEX` (`forma_pagamento` ASC),
  INDEX `FK_condicao_pagamento_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_condicao_pagamento_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `forma_pagamento_descricao_UNIQUE` (`forma_pagamento` ASC, `descricao` ASC),
  INDEX `descricao_INDEX` (`descricao` ASC),
  CONSTRAINT `FK_condicao_pagamento_forma_pagamento`
    FOREIGN KEY (`forma_pagamento`)
    REFERENCES `steve`.`forma_pagamento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_condicao_pagamento_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_condicao_pagamento_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`conta_receber`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`conta_receber` ;

CREATE TABLE IF NOT EXISTS `steve`.`conta_receber` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `condicao_pagamento` SMALLINT UNSIGNED NOT NULL,
  `sacado` SMALLINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `ordem` BIGINT UNSIGNED NOT NULL,
  `numero_parcela` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `montante_pago` DECIMAL(9,2) UNSIGNED NOT NULL DEFAULT 0,
  `saldo_devedor` DECIMAL(9,2) UNSIGNED NOT NULL,
  `valor` DECIMAL(9,2) UNSIGNED NOT NULL COMMENT '	',
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_vencimento` DATE NULL,
  `observacao` VARCHAR(256) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `situacao` ENUM('ABERTO', 'AMORTIZADO', 'BAIXADO', 'CANCELADO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'ABERTO',
  PRIMARY KEY (`id`),
  INDEX `FK_conta_receber_condicao_pagamento_INDEX` (`condicao_pagamento` ASC),
  INDEX `FK_conta_receber_ordem_INDEX` (`ordem` ASC),
  INDEX `FK_conta_receber_sacado_INDEX` (`sacado` ASC),
  INDEX `FK_conta_receber_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_conta_receber_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `ordem_numero_parcela_UNIQUE` (`ordem` ASC, `numero_parcela` ASC),
  INDEX `data_vencimento_INDEX` (`data_vencimento` ASC),
  INDEX `situacao_INDEX` (`situacao` ASC),
  CONSTRAINT `FK_conta_receber_condicao_pagamento`
    FOREIGN KEY (`condicao_pagamento`)
    REFERENCES `steve`.`condicao_pagamento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_receber_ordem`
    FOREIGN KEY (`ordem`)
    REFERENCES `steve`.`ordem` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_receber_sacado`
    FOREIGN KEY (`sacado`)
    REFERENCES `steve`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_receber_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_receber_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`conta_pagar`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`conta_pagar` ;

CREATE TABLE IF NOT EXISTS `steve`.`conta_pagar` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cedente` SMALLINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `numero_parcela` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
  `montante_pago` DECIMAL(9,2) UNSIGNED NOT NULL DEFAULT 0,
  `saldo_devedor` DECIMAL(9,2) UNSIGNED NOT NULL,
  `valor` DECIMAL(9,2) UNSIGNED NOT NULL,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_emissao` DATE NULL,
  `data_vencimento` DATE NOT NULL,
  `mes_referente` DATE NOT NULL COMMENT 'YYYY-MM',
  `fatura` VARCHAR(32) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  `observacao` VARCHAR(256) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  `situacao` ENUM('ABERTO', 'AMORTIZADO', 'BAIXADO', 'CANCELADO') CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT 'ABERTO',
  PRIMARY KEY (`id`),
  INDEX `FK_conta_pagar_cedente_INDEX` (`cedente` ASC),
  INDEX `FK_conta_pagar_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_conta_pagar_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `fatura_cedente_numero_parcela_UNIQUE` (`fatura` ASC, `cedente` ASC, `numero_parcela` ASC),
  INDEX `data_vencimento_INDEX` (`data_vencimento` ASC),
  INDEX `fatura_INDEX` (`fatura` ASC),
  INDEX `situacao_INDEX` (`situacao` ASC),
  CONSTRAINT `FK_conta_pagar_cedente`
    FOREIGN KEY (`cedente`)
    REFERENCES `steve`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_pagar_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_conta_pagar_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`permissao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`permissao` ;

CREATE TABLE IF NOT EXISTS `steve`.`permissao` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `descricao` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_permissao_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_permissao_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  UNIQUE INDEX `descricao_UNIQUE` (`descricao` ASC),
  CONSTRAINT `FK_permissao_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_permissao_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `steve`.`permissao_credencial`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `steve`.`permissao_credencial` ;

CREATE TABLE IF NOT EXISTS `steve`.`permissao_credencial` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `permissao` TINYINT UNSIGNED NOT NULL,
  `credencial` SMALLINT UNSIGNED NOT NULL,
  `usuario_criacao` SMALLINT UNSIGNED NOT NULL,
  `usuario_ultima_alteracao` SMALLINT UNSIGNED NULL,
  `versao` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_ultima_alteracao` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `FK_permissao_credencial_credencial_INDEX` (`credencial` ASC),
  INDEX `FK_permissao_credencial_permissao_INDEX` (`permissao` ASC),
  INDEX `FK_permissao_credencial_usuario_criacao_INDEX` (`usuario_criacao` ASC),
  INDEX `FK_permissao_credencial_usuario_ultima_alteracao_INDEX` (`usuario_ultima_alteracao` ASC),
  INDEX `credencial_permissao_UNIQUE` (`credencial` ASC, `permissao` ASC),
  CONSTRAINT `FK_permissao_credencial_credencial`
    FOREIGN KEY (`credencial`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_permissao_credencial_permissao`
    FOREIGN KEY (`permissao`)
    REFERENCES `steve`.`permissao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_permissao_credencial_usuario_criacao`
    FOREIGN KEY (`usuario_criacao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_permissao_credencial_usuario_ultima_alteracao`
    FOREIGN KEY (`usuario_ultima_alteracao`)
    REFERENCES `steve`.`credencial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `steve`;

DELIMITER $$

USE `steve`$$
DROP TRIGGER IF EXISTS `steve`.`pessoa_BEFORE_INSERT` $$
USE `steve`$$
CREATE DEFINER = CURRENT_USER TRIGGER `steve`.`pessoa_BEFORE_INSERT` BEFORE INSERT ON `pessoa` FOR EACH ROW BEGIN
	DECLARE ie_rg_non_isento_unique CONDITION FOR SQLSTATE '23000';
    IF(EXISTS(SELECT 1 FROM `pessoa` WHERE NEW.`ie_rg` != 'ISENTO' AND `ie_rg` = NEW.`ie_rg`)) THEN
        SET @conflict_message_text = CONCAT('Duplicate entry ''', NEW.`ie_rg`, ''' for key ''ie_rg_UNIQUE''');
        SIGNAL ie_rg_non_isento_unique SET MESSAGE_TEXT = @conflict_message_text, MYSQL_ERRNO = 1062;
	END IF;
END;$$


USE `steve`$$
DROP TRIGGER IF EXISTS `steve`.`pessoa_BEFORE_UPDATE` $$
USE `steve`$$
CREATE DEFINER = CURRENT_USER TRIGGER `steve`.`pessoa_BEFORE_UPDATE` BEFORE UPDATE ON `pessoa` FOR EACH ROW BEGIN
	DECLARE ie_rg_non_isento_unique CONDITION FOR SQLSTATE '23000';
    IF(EXISTS(SELECT 1 FROM `pessoa` WHERE NEW.`ie_rg` != 'ISENTO' AND `ie_rg` = NEW.`ie_rg`) AND (NEW.`ie_rg` <> OLD.`ie_rg`)) THEN
        SET @conflict_message_text = CONCAT('Duplicate entry ''', NEW.`ie_rg`, ''' for key ''ie_rg_UNIQUE''');
        SIGNAL ie_rg_non_isento_unique SET MESSAGE_TEXT = @conflict_message_text, MYSQL_ERRNO = 1062;
	END IF;
END;$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
