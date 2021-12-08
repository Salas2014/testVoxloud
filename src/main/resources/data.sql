INSERT INTO user (`id`, `username`, `password`, `algorithm`)
    VALUES ('1', 'vlad', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');
INSERT INTO user (`id`, `username`, `password`, `algorithm`)
    VALUES ('2', 'karim', '$2a$12$LvGOJ17yCnF4JPj.WblFf.9brTxyGss0emkaZwJol7mTRY0dYNlHa', 'BCRYPT');
INSERT INTO user (`id`, `username`, `password`, `algorithm`)
    VALUES ('3', 'gaga', '$2a$12$bdXn3yYI83taqSwgrOxqu.5vLdScD7rDWejAFjdDa.ke8VlTQ2KDi', 'BCRYPT');

INSERT INTO role (`id`, `name`, `user`)  VALUES ('1', 'read', '1');
INSERT INTO role (`id`, `name`, `user`)  VALUES ('2', 'read', '2');
INSERT INTO role (`id`, `name`, `user`)  VALUES ('3', 'read', '3');