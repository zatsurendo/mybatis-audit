INSERT INTO `user` (username, email)
VALUES ('john doe', 'john_doe@example.com');

INSERT INTO `revinfo` (revtstmp)
VALUES (1770070491000);

INSERT INTO `item` (`name`, `price`, `version`, `created_by`, `created_at`, `updated_by`, `updated_at`)
VALUES
    ('Apple', 200, 1, 'system', NOW(), 'system', NOW()),
    ('Banana', 200, 1, 'system', NOW(), 'system', NOW()),
    ('Orange', 300, 1, 'system', NOW(), 'system', NOW()),
    ('Grapes', 400, 1, 'system', NOW(), 'system', NOW()),
    ('Mango', 500, 1, 'system', NOW(), 'system', NOW()),
    ('Pineapple', 600, 1, 'system', NOW(), 'system', NOW()),
    ('Strawberry', 700, 1, 'system', NOW(), 'system', NOW()),
    ('Blueberry', 800, 1, 'system', NOW(), 'system', NOW()),
    ('Watermelon', 900, 1, 'system', NOW(), 'system', NOW()),
    ('Apple', 200, 1, 'system', NOW(), 'system', NOW());
INSERT INTO `item_log` (`rev`, `revtype`, `id`, `name`, `price`, `version`, `created_by`, `created_at`, `updated_by`, `updated_at`)
VALUES
    (1, 1, 1, 'Apple', 200, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 2, 'Banana', 200, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 3, 'Orange', 300, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 4, 'Grapes', 400, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 5, 'Mango', 500, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 6, 'Pineapple', 600, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 7, 'Strawberry', 700, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 8, 'Blueberry', 800, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 9, 'Watermelon', 900, 1, 'system', NOW(), 'system', NOW()),
    (1, 1, 10, 'Apple', 200, 1, 'system', NOW(), 'system', NOW());