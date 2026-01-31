INSERT INTO `user` (username, email)
VALUES ('john doe', 'john_doe@example.com');

INSERT INTO `item` (`name`, `price`, `version`, `created_by`, `created_at`, `updated_by`, `updated_at`)
VALUES ('Banana', 200, 1, 'system', NOW(), 'system', NOW());