INSERT INTO `usuarios` (username, password, enable) VALUES('admin', '$2a$10$Ifb0QN2D5nD757dOnQhd9uJ0X8W.nJ2UGSMK2dktsqzuL8ERKB9ii', 1);
INSERT INTO `usuarios` (username, password, enable) VALUES('maty', '$2a$10$S1mR//oZGi00l9zmZppC8e5ENoMNoNcK0fiKiCa0vNUK/3lEGCTPe', 1);
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (nombre) VALUES ('ROLE_CLIENT');

INSERT INTO `usuario_roles` (usuario_id, role_id) VALUES  (1,1);
INSERT INTO `usuario_roles` (usuario_id, role_id) VALUES  (2,2);
