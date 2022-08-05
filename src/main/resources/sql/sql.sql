create database `spyglass`;

use `spyglass`;

create table if not exists `users`(
	`id` varchar(50) primary key,
    `first_name` varchar(50),
    `last_name` varchar(50),
    `email` varchar(50)
);

create table if not exists `contributions` (
	`id` Integer primary key auto_increment,
    `contribution_amount` DOUBLE,
    `contribution_date` DATE,
    `goal_id` Integer
);

create table if not exists `goals`(
	`id` Integer primary key auto_increment,
    `goal_type` varchar(50),
    `name_of_goal` varchar(50),
    `description_of_goal` varchar(200),
    `icon_picture` varchar(100),
    `start_date` DATE,
    `end_date` DATE,
    `starting_dollar_amount` DOUBLE,
    `target_dollar_amount` DOUBLE,
    `current_dollar_amount` DOUBLE,
    `contribution_recommendation` DOUBLE,
    `contribution_frequency` varchar(10),
    `user_id` varchar(50)
);