CREATE TABLE IF NOT EXISTS `recurring_goals` (
	`id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `name_of_goal` varchar(50),
    `description_of_goal` varchar(200),
    `icon_picture` varchar(100),
    `start_date` DATE,
    `end_date` DATE,
    `starting_dollar_amount` DOUBLE,
    `target_dollar_amount` DOUBLE,
    `current_dollar_amount` DOUBLE,
    `user_id` INTEGER
);