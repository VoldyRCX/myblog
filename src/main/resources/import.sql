INSERT INTO user (id, username, password, nickname, email) VALUES (1, 'admin', 'admin', 'admin', 'renchengxin@vip.qq.com');
INSERT INTO user (id, username, password, nickname, email)  VALUES (2, 'renchengxian', 'renchengxian', 'Voldy', '413616799@qq.com');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
