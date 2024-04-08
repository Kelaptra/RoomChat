CREATE TABLE `roomchat`.`user` (
  `username` VARCHAR(45) NOT NULL,
  `room_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`),
  INDEX `fk_idx` (`room_name` ASC) VISIBLE,
  CONSTRAINT `fk`
    FOREIGN KEY (`room_name`)
    REFERENCES `roomchat`.`room` (`room_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);