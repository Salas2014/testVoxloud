CREATE TABLE IF NOT EXISTS user(
    `id` int not null auto_increment,
    `username` varchar(45) not null,
    `password` varchar(2000) not null,
    `algorithm` VARCHAR(45) NOT NULL,
PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS role (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `user` INT NOT NULL,
PRIMARY KEY (`id`));


