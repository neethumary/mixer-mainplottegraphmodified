-- DB & Table for batch runs
CREATE DATABASE IF NOT EXISTS mixer;
USE mixer;

DROP TABLE IF EXISTS batch_run;
CREATE TABLE batch_run (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    monday INT NOT NULL,
    tuesday INT NOT NULL,
    wednesday INT NOT NULL,
    thursday INT NOT NULL,
    friday INT NOT NULL,
    saturday INT NOT NULL,
    sunday INT NOT NULL
);

-- Sample rows (10)
INSERT INTO batch_run (name, monday, tuesday, wednesday, thursday, friday, saturday, sunday) VALUES
('Batch A', 447,370,408,432, 215,180,220),
('Batch B', 334,153,466,169, 120,260,140),
('Batch C', 300,101,308,334, 180,210,160),
('Batch D', 339,237,314,447, 199,177,188),
('Batch E', 422, 93,241,401, 211,233,162),
('Batch F', 237,385,433,117, 244,175,199),
('Batch G', 139,303,411,215, 204,228,207),
('Batch H', 457,435,133,117, 155,201,199),
('Batch I', 374,338,393,306, 221,156,188),
('Batch J', 232,465,116,267, 210,213,147);
