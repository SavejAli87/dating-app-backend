CREATE TABLE IF NOT EXISTS subscription_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount INT,
    contact_view VARCHAR(50),
    type VARCHAR(20),
    duration INT,
    discount_amount INT DEFAULT 0,
    description VARCHAR(255),
    remaining_days VARCHAR(50),
    active_plane_name VARCHAR(50)
);

INSERT INTO subscription_details
(amount, contact_view, type, duration, description, active_plane_name)
VALUES
(0, '10', 'FREE', 7, 'Free Plan', 'FREE_PLAN'),
(199, '50', 'GOLD', 30, 'Gold Plan', 'GOLD_PLAN'),
(499, '100', 'PREMIUM', 90, 'Premium Plan', 'PREMIUM_PLAN');