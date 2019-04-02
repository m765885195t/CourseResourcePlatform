CREATE TABLE `crp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key auto increment',
  `user_id` VARCHAR(128) NOT NULL  DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_uk` (`user_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;