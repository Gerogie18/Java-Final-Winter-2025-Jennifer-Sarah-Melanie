package GymApp.setup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import GymApp.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Seeds database to have demo material
 *
 */

public class DemoDatabaseSeeder {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false); // ⛑ Start transaction
            System.out.println("[✓] Connected to database.");

            // Drop and create tables
            stmt.executeUpdate("""
                DROP TABLE IF EXISTS memberships, workout_classes, users CASCADE;

                CREATE TABLE users (
                    user_id SERIAL PRIMARY KEY,
                    user_name VARCHAR(50) NOT NULL,
                    user_phone_number VARCHAR(15) NOT NULL,
                    user_email VARCHAR(100) NOT NULL UNIQUE,
                    user_address VARCHAR(100) NOT NULL,
                    user_password VARCHAR(255) NOT NULL,
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
                    CONSTRAINT fk_workout_classes_trainer_id FOREIGN KEY (trainer_id)
                        REFERENCES users(user_id) ON UPDATE NO ACTION ON DELETE CASCADE,
                    CONSTRAINT status_check CHECK(class_status IN ('ACTIVE','CANCELLED','INACTIVE')),
                    CONSTRAINT capacity_check CHECK (class_capacity >= 0 AND class_capacity <= 100)
                );

                CREATE TABLE memberships (
                    membership_id SERIAL PRIMARY KEY,
                    membership_type VARCHAR(50) NOT NULL,
                    membership_cost DECIMAL(10, 2) NOT NULL,
                    membership_description TEXT,
                    date_purchased DATE DEFAULT CURRENT_DATE,
                    member_id INT NOT NULL,
                    CONSTRAINT fk_memberships_member_id FOREIGN KEY (member_id)
                        REFERENCES users(user_id) ON UPDATE NO ACTION ON DELETE CASCADE
                );
            """);
            System.out.println("[✓] Tables created.");

            // Insert users
            String insertUserSQL = """
    INSERT INTO users (user_name, user_phone_number, user_email, user_address, user_password, user_role)
    VALUES (?, ?, ?, ?, ?, ?);
""";

            PreparedStatement userStmt = conn.prepareStatement(insertUserSQL);

            String[][] users = {
                    {"Alex Admin", "709-555-0001", "alex.admin@gym.com", "1 Admin Lane", "admin123", "ADMIN"},
                    {"Jamie Admin", "709-555-0002", "jamie.admin@gym.com", "2 Admin Blvd", "admin123", "ADMIN"},
                    {"Tina Trainer", "709-555-1001", "tina.trainer@gym.com", "10 Trainer Rd", "trainer123", "TRAINER"},
                    {"Terry Trainer", "709-555-1002", "terry.trainer@gym.com", "11 Trainer Rd", "trainer123", "TRAINER"},
                    {"Taylor Trainer", "709-555-1003", "taylor.trainer@gym.com", "12 Trainer Ave", "trainer123", "TRAINER"},
                    {"Toby Trainer", "709-555-1004", "toby.trainer@gym.com", "13 Trainer St", "trainer123", "TRAINER"},
                    {"Mia Member", "709-555-2001", "mia.member@gym.com", "21 Member Ct", "member123", "MEMBER"},
                    {"Max Member", "709-555-2002", "max.member@gym.com", "22 Member Ct", "member123", "MEMBER"},
                    {"Morgan Member", "709-555-2003", "morgan.member@gym.com", "23 Member Ct", "member123", "MEMBER"},
                    {"Mel Member", "709-555-2004", "mel.member@gym.com", "24 Member Ct", "member123", "MEMBER"},
                    {"Micah Member", "709-555-2005", "micah.member@gym.com", "25 Member Ct", "member123", "MEMBER"},
                    {"Marley Member", "709-555-2006", "marley.member@gym.com", "26 Member Ct", "member123", "MEMBER"},
                    {"Mason Member", "709-555-2007", "mason.member@gym.com", "27 Member Ct", "member123", "MEMBER"},
                    {"Maddie Member", "709-555-2008", "maddie.member@gym.com", "28 Member Ct", "member123", "MEMBER"}
            };

            for (String[] user : users) {
                String hashedPassword = BCrypt.hashpw(user[4], BCrypt.gensalt());

                userStmt.setString(1, user[0]); // name
                userStmt.setString(2, user[1]); // phone
                userStmt.setString(3, user[2]); // email
                userStmt.setString(4, user[3]); // address
                userStmt.setString(5, hashedPassword); // hashed password
                userStmt.setString(6, user[5]); // role
                userStmt.addBatch();
            }

            userStmt.executeBatch();
            System.out.println("[✓] Users inserted.");


            // Insert workout classes
            stmt.executeUpdate("""
                INSERT INTO workout_classes (class_name, class_type, class_description, class_status, class_capacity, trainer_id)
                VALUES
                  ('Morning Yoga', 'Yoga', 'Start your day with a stretch', 'ACTIVE', 20, 3),
                  ('Spin Express', 'Spin', 'High intensity cycling', 'ACTIVE', 15, 4),
                  ('Strength 101', 'Strength', 'Beginner strength training', 'ACTIVE', 18, 5),
                  ('Core Crusher', 'Pilates', 'Ab-focused class', 'INACTIVE', 12, 3),
                  ('Evening HIT', 'HIT', 'Full-body interval training', 'CANCELLED', 16, 6),
                  ('Mobility Flow', 'Stretching', 'Improve your flexibility', 'ACTIVE', 10, 4);
            """);
            System.out.println("[✓] Workout classes inserted.");

            // Insert memberships
            stmt.executeUpdate("""
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
            """);
            System.out.println("[✓] Memberships inserted.");

            conn.commit(); // ✅ Commit if everything succeeded
            System.out.println("\n🎉 Demo database seeded successfully!");

        } catch (Exception e) {
            System.out.println("\n❌ Error during database seeding! Rolling back changes.");
            e.printStackTrace();
        }
    }
}
