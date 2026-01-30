INSERT INTO `user` (username, email)
VALUES ('john doe', 'john_doe@example.com');

INSERT INTO `item` (`name`, `price`, `created_by`, `created_at`, `updated_by`, `updated_at`)
VALUES ('Banana', 200, 'system', NOW(), 'system', NOW());