INSERT INTO `user`(`id`, `email`, `icon`, `nickname`, `password`, `username`) VALUES (2, 'test@test.com', NULL, 'test', '$2a$10$x0OcJ57ZnK0s/dUsy2.uOONjeKuPr0rJLqLqHPE62y9b6Y.4FkrJK', 'test');
INSERT INTO `user`(`id`, `email`, `icon`, `nickname`, `password`, `username`) VALUES (1, 'renchengxian@qq.com', NULL, 'Voldy', '$2a$10$84VUD.KGqJgrLKD0vLotUetmhEQb8/n3afkUd6fD4grqeC5.mjZWm', 'renqiangii');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
