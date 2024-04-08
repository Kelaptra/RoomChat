CREATE TABLE `roomchat`.`room` (
  `room_name` VARCHAR(45) NOT NULL,
  `online_members` INT UNSIGNED NOT NULL DEFAULT 1,
  `max_members` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`room_name`));