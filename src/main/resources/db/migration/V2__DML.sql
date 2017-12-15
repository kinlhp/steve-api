-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES,NO_AUTO_VALUE_ON_ZERO';

-- -----------------------------------------------------
-- Data for table `steve`.`pessoa`
-- -----------------------------------------------------
START TRANSACTION;
USE `steve`;
INSERT INTO `steve`.`pessoa` (`id`, `usuario_criacao`, `perfil_cliente`, `perfil_usuario`, `cnpj_cpf`, `fantasia_sobrenome`, `ie_rg`, `nome_razao`, `tipo`) VALUES (0, 0, b'0', b'1', '0', 'steve', '0', 'Kin.LHP® Software, Inc.', 'SISTEMA');

COMMIT;


-- -----------------------------------------------------
-- Data for table `steve`.`credencial`
-- -----------------------------------------------------
START TRANSACTION;
USE `steve`;
INSERT INTO `steve`.`credencial` (`id`, `funcionario`, `usuario_criacao`, `perfil_administrador`, `perfil_sistema`, `usuario`) VALUES (0, 0, 0, b'1', b'1', 'steve');

COMMIT;


-- -----------------------------------------------------
-- Data for table `steve`.`uf`
-- -----------------------------------------------------
START TRANSACTION;
USE `steve`;
INSERT INTO `steve`.`uf` (`usuario_criacao`, `ibge`, `nome`, `sigla`) VALUES (0, 00, 'EXTERIOR', 'EX'), (0, 12, 'ACRE', 'AC'), (0, 13, 'AMAZONAS', 'AM'), (0, 11, 'RONDÔNIA', 'RO'), (0, 14, 'RORAIMA', 'RR'), (0, 15, 'PARÁ', 'PA'), (0, 16, 'AMAPÁ', 'AP'), (0, 17, 'TOCANTINS', 'TO'), (0, 21, 'MARANHÃO', 'MA'), (0, 22, 'PIAUÍ', 'PI'), (0, 23, 'CEARÁ', 'CE'), (0, 24, 'RIO GRANDE DO NORTE', 'RN'), (0, 25, 'PARAÍBA', 'PB'), (0, 26, 'PERNAMBUCO', 'PE'), (0, 27, 'ALAGOAS', 'AL'), (0, 28, 'SERGIPE', 'SE'), (0, 29, 'BAHIA', 'BA'), (0, 31, 'MINAS GERAIS', 'MG'), (0, 32, 'ESPÍRITO SANTO', 'ES'), (0, 33, 'RIO DE JANEIRO', 'RJ'), (0, 35, 'SÃO PAULO', 'SP'), (0, 41, 'PARANÁ', 'PR'), (0, 42, 'SANTA CATARINA', 'SC'), (0, 43, 'RIO GRANDE DO SUL', 'RS'), (0, 50, 'MATO GROSSO DO SUL', 'MS'), (0, 51, 'MATO GROSSO', 'MT'), (0, 52, 'GOIÁS', 'GO'), (0, 53, 'DISTRITO FEDERAL', 'DF');

COMMIT;


-- -----------------------------------------------------
-- Data for table `steve`.`forma_pagamento`
-- -----------------------------------------------------
START TRANSACTION;
USE `steve`;
INSERT INTO `steve`.`forma_pagamento` (`usuario_criacao`, `descricao`) VALUES (0, 'CARTÃO DE CRÉDITO'), (0, 'CARTÃO DE DÉBITO'), (0, 'CHEQUE'), (0, 'DINHEIRO'), (0, 'PERMUTA'), (0, 'PROMISSÓRIA');

COMMIT;


-- -----------------------------------------------------
-- Data for table `steve`.`condicao_pagamento`
-- -----------------------------------------------------
START TRANSACTION;
USE `steve`;
INSERT INTO `steve`.`condicao_pagamento` (`forma_pagamento`, `usuario_criacao`, `descricao`) VALUES (4, 0, 'À VISTA');

COMMIT;


-- -----------------------------------------------------
-- Data for table `steve`.`permissao`
-- -----------------------------------------------------
START TRANSACTION;
USE `steve`;
INSERT INTO `steve`.`permissao` (`usuario_criacao`, `descricao`) VALUES (0, 'ADMINISTRADOR'), (0, 'PADRAO'), (0, 'SISTEMA');

COMMIT;


-- -----------------------------------------------------
-- Data for table `steve`.`permissao_credencial`
-- -----------------------------------------------------
START TRANSACTION;
USE `steve`;
INSERT INTO `steve`.`permissao_credencial` (`permissao`, `credencial`, `usuario_criacao`) VALUES (1, 0, 0), (2, 0, 0), (3, 0, 0);

COMMIT;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
