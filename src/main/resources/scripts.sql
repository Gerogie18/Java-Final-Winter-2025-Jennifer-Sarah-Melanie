CREATE TABLE users (
   user_id SERIAL PRIMARY KEY,
   user_name VARCHAR(50) NOT NULL,
   user_phone_number VARCHAR(15) NOT NULL,
   user_email VARCHAR(100) NOT NULL UNIQUE,
   user_address VARCHAR(100) NOT NULL,
   user_role VARCHAR(10) NOT NULL,
   CONSTRAINT valid_role_check CHECK (user_role IN ('ADMIN', 'TRAINER', 'MEMBER'))
);

CREATE TABLE workout_classes (
    class_id SERIAL PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL,
    class_type VARCHAR(50) NOT NULL,
    class_description TEXT NOT NULL,
    class_status VARCHAR(20) NOT NULL,
    class_capacity INT,
    trainer_id INT NOT NULL,
    CONSTRAINT fk_workout_classes_trainer_id
        FOREIGN KEY (trainer_id)
        REFERENCES users(user_id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT status_check
        CHECK(class_status IN ('ACTIVE','CANCELLED','INACTIVE')),
    CONSTRAINT capacity_check
        CHECK (class_capacity >= 0 AND class_capacity <= 100)
);



CREATE TABLE IF NOT EXISTS memberships (
    membership_id SERIAL PRIMARY KEY,
    membership_type VARCHAR(50) NOT NULL,
    membership_cost DECIMAL(10, 2) NOT NULL,
    membership_description TEXT,
    date_purchased DATE DEFAULT CURRENT_DATE,
    member_id INT NOT NULL,
    CONSTRAINT fk_memberships_member_id
        FOREIGN KEY (member_id) REFERENCES users(user_id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

TABLESPACE pg_default;


-- Getting sum for memberships by month example
SELECT
    TO_CHAR(date_purchased, 'YYYY-MM') AS month,
    SUM(membership_price) AS total_revenue
FROM memberships
GROUP BY month
ORDER BY month;
