INSERT INTO user (`id`, `username`, `password`, `algorithm`)
    VALUES ('1', 'vlad', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');

INSERT INTO role (`id`, `name`, `user`)  VALUES ('1', 'read', '1');