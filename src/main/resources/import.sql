INSERT INTO `usuarios` (username, password, enable) VALUES('admin', '$2a$10$Ifb0QN2D5nD757dOnQhd9uJ0X8W.nJ2UGSMK2dktsqzuL8ERKB9ii', 1);
INSERT INTO `usuarios` (username, password, enable) VALUES('maty', '$2a$10$S1mR//oZGi00l9zmZppC8e5ENoMNoNcK0fiKiCa0vNUK/3lEGCTPe', 1);
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (nombre) VALUES ('ROLE_CLIENT');
INSERT INTO `roles` (nombre) VALUES ('ROLE_EMPLEADO');

INSERT INTO `usuario_roles` (usuario_id, role_id) VALUES  (1,1);
INSERT INTO `usuario_roles` (usuario_id, role_id) VALUES  (2,2);


INSERT INTO `funcionario` (id_funcionario, apellidom_funcionario, apellidop_funcionario, cargo_funcionario, estado_funcionario, genero_funcionario, nombres_funcionario, rut_funcionario) VALUES (1, 'saavedra', 'chavez', 'Párroco', b'1', 'Masculino', 'matias', '194154003');

INSERT INTO `cementerio` (id_cementerio, capacidad_terrenos, direccion_cementerio, nombre_cementerio, telefono_cementerio) VALUES (1, 22, 'Juan Zuñiga sin numero', 'Parroquial pinto', 987654321);
INSERT INTO `terreno` (id_terreno, capacidad_terreno, estado_terreno, nombre_terreno, id_cementerio) VALUES (1, 12, b'1', 'terreno 1', 1);





INSERT INTO `tipo_tumba` (id_tipo_tumba, capacidad_tipo_tumba, estado_tipo_tumba, nombretipo_tumba) VALUES (1, 3, b'1', 'simple');


INSERT INTO `cliente` (id_cliente, apellidom_cliente, apellidom_familiar, apellidop_cliente, apellidop_familiar, direccion_cliente, estado_cliente, genero_cliente, nombres_cliente, nombres_familiar, rut_cliente, rut_familiar, telefono_cliente, telefono_familiar) VALUES (1, 'cea', 'saavedra', 'rivas', 'chavez', 'asdadsaa', b'1', 'Femenino', 'francisca', 'matias', '187701694', '194154003', 987654321, 876543211);




