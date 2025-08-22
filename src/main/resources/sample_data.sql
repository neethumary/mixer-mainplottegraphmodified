-- Create database
CREATE DATABASE IF NOT EXISTS mixer;
USE mixer;

-- Create table
CREATE TABLE IF NOT EXISTS batch (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    chem1 VARCHAR(100),
    chem2 VARCHAR(100),
    chem3 VARCHAR(100),
    machine VARCHAR(100),
    size INT,
    comment VARCHAR(255)
);

-- Insert sample data
INSERT INTO batch (name, chem1, chem2, chem3, machine, size, comment) VALUES
('Batch A', 'Sodium', 'Chloride', 'Potassium', 'Mixer 1', 100, 'First batch'),
('Batch B', 'Calcium', 'Magnesium', 'Iron', 'Mixer 2', 200, 'Second batch'),
('Batch C', 'Sulfur', 'Phosphorus', 'Nitrogen', 'Mixer 3', 150, 'Urgent order'),
('Batch D', 'Copper', 'Zinc', 'Lead', 'Mixer 1', 120, 'Routine'),
('Batch E', 'Silver', 'Gold', 'Platinum', 'Mixer 2', 80, 'High value'),
('Batch F', 'Nickel', 'Cobalt', 'Manganese', 'Mixer 3', 170, 'Special mix'),
('Batch G', 'Lithium', 'Boron', 'Fluorine', 'Mixer 1', 90, 'Experimental'),
('Batch H', 'Carbon', 'Oxygen', 'Hydrogen', 'Mixer 2', 110, 'Standard'),
('Batch I', 'Tin', 'Antimony', 'Bismuth', 'Mixer 3', 130, 'Low priority'),
('Batch J', 'Chromium', 'Vanadium', 'Molybdenum', 'Mixer 1', 140, 'For testing'); 