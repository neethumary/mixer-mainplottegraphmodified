-- DB & table
CREATE DATABASE IF NOT EXISTS mixer;
USE mixer;

CREATE TABLE IF NOT EXISTS batch_run (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  monday INT NOT NULL,
  tuesday INT NOT NULL,
  wednesday INT NOT NULL,
  thursday INT NOT NULL,
  friday INT NOT NULL,
  saturday INT NOT NULL,
  sunday INT NOT NULL,
  comment VARCHAR(255)
);

-- Sample rows; durations 100..500 sec
INSERT INTO batch_run (name, monday, tuesday, wednesday, thursday, friday, saturday, sunday, comment)
VALUES
('Batch A', FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, 'Auto sample A'),
('Batch B', FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, 'Auto sample B'),
('Batch C', FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, FLOOR(RAND()*401)+100, 'Auto sample C');
