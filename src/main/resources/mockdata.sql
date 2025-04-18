-- Admins
INSERT INTO users (user_name, user_phone_number, user_email, user_address, user_password, user_role)
VALUES
  ('Alex Admin', '709-555-0001', 'alex.admin@gym.com', '1 Admin Lane', 'admin123', 'ADMIN'),
  ('Jamie Admin', '709-555-0002', 'jamie.admin@gym.com', '2 Admin Blvd', 'admin123', 'ADMIN');

-- Trainers
INSERT INTO users (user_name, user_phone_number, user_email, user_address, user_password, user_role)
VALUES
  ('Tina Trainer', '709-555-1001', 'tina.trainer@gym.com', '10 Trainer Rd', 'trainer123', 'TRAINER'),
  ('Terry Trainer', '709-555-1002', 'terry.trainer@gym.com', '11 Trainer Rd', 'trainer123', 'TRAINER'),
  ('Taylor Trainer', '709-555-1003', 'taylor.trainer@gym.com', '12 Trainer Ave', 'trainer123', 'TRAINER'),
  ('Toby Trainer', '709-555-1004', 'toby.trainer@gym.com', '13 Trainer St', 'trainer123', 'TRAINER');

-- Members (IDs 7–14)
INSERT INTO users (user_name, user_phone_number, user_email, user_address, user_password, user_role)
VALUES
  ('Mia Member', '709-555-2001', 'mia.member@gym.com', '21 Member Ct', 'member123', 'MEMBER'),
  ('Max Member', '709-555-2002', 'max.member@gym.com', '22 Member Ct', 'member123', 'MEMBER'),
  ('Morgan Member', '709-555-2003', 'morgan.member@gym.com', '23 Member Ct', 'member123', 'MEMBER'),
  ('Mel Member', '709-555-2004', 'mel.member@gym.com', '24 Member Ct', 'member123', 'MEMBER'),
  ('Micah Member', '709-555-2005', 'micah.member@gym.com', '25 Member Ct', 'member123', 'MEMBER'),
  ('Marley Member', '709-555-2006', 'marley.member@gym.com', '26 Member Ct', 'member123', 'MEMBER'),
  ('Mason Member', '709-555-2007', 'mason.member@gym.com', '27 Member Ct', 'member123', 'MEMBER'),
  ('Maddie Member', '709-555-2008', 'maddie.member@gym.com', '28 Member Ct', 'member123', 'MEMBER');


INSERT INTO workout_classes (class_name, class_type, class_description, class_status, class_capacity, trainer_id)
VALUES
  ('Morning Yoga', 'Yoga', 'Start your day with a stretch', 'ACTIVE', 20, 3),
  ('Spin Express', 'Spin', 'High intensity cycling', 'ACTIVE', 15, 4),
  ('Strength 101', 'Strength', 'Beginner strength training', 'ACTIVE', 18, 5),
  ('Core Crusher', 'Pilates', 'Ab-focused class', 'INACTIVE', 12, 3),
  ('Evening HIIT', 'HIIT', 'Full-body interval training', 'CANCELLED', 16, 6),
  ('Mobility Flow', 'Stretching', 'Improve your flexibility', 'ACTIVE', 10, 4);


INSERT INTO memberships (membership_type, membership_cost, membership_description, member_id)
VALUES
  ('Basic', 49.99, 'Access to gym and equipment', 7),
  ('Basic', 49.99, 'Access to gym and equipment', 8),
  ('Standard', 59.99, 'Includes group classes', 9),
  ('Standard', 59.99, 'Includes group classes', 10),
  ('Premium', 79.99, 'Classes and personal training', 11),
  ('Basic', 49.99, 'Access to gym and equipment', 12),
  ('Standard', 59.99, 'Includes group classes', 13),
  ('Premium', 79.99, 'All-access membership', 14),
  ('Premium', 79.99, 'Trainer also trains!', 3),
  ('Premium', 79.99, 'Another training trainer', 6);
