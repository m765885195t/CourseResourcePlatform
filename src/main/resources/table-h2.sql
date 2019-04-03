CREATE TABLE `crp_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key auto increment',
  `student_id` VARCHAR(128) NOT NULL  DEFAULT '',
--   `properties` VARCHAR (2048) DEFAULT NULL COMMENT 'properties',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_uk` (`student_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;