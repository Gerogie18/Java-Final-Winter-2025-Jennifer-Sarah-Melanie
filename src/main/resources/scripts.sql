CREATE TABLE users (
   user_id SERIAL PRIMARY KEY UNIQUE,
   user_name VARCHAR(50) NOT NULL,
<<<<<<< Updated upstream
=======
   user_phone_number NUMERIC(10) NOT NULL,
>>>>>>> Stashed changes
   user_email VARCHAR(100) NOT NULL UNIQUE,
   user_role VARCHAR(10) NOT NULL CHECK(role IN('ADMIN','TRAINER','MEMBER')) CONSTRAINT valid_role_check
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE workout_classes (
    class_id SERIAL PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL,
    class_type VARCHAR(50) NOT NULL,
    class_description TEXT NOT NULL,
    class_status VARCHAR(20) NOT NULL CHECK(status IN ('ACTIVE','CANCELLED','INACTIVE')) CONSTRAINT status_check
    class_capacity INT CHECK (class_capacity >= 0 AND class_capacity <= 100),
    trainer_id INT NOT NULL,
    CONSTRAINT fk_workout_classes_trainer_id
        FOREIGN KEY (trainer_id)
        REFERENCES users(user_id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
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
