-- WARNING: this will delete all rows in these tables when the SQL import runs.
-- Truncate tables and reset sequences (PostgreSQL):
TRUNCATE TABLE reviews, checkins, pools, users RESTART IDENTITY CASCADE;

-- Insert sample data (IDs will start at 1)
INSERT INTO users (name, password, email, is_admin) VALUES
    ('Anton', 'antonadmin123', 'anton@example.com', true),
    ('Arnar', 'arnaradmin123', 'arnar@example.com', true),
    ('Mikael', 'mikaeladmin123', 'mikael@example.com', true),
    ('Sævar', 'saevaradmin123', 'saevar@example.com', true),
    ('Alice', 'secret123', 'alice@example.com', false),
    ('Bob',   'bobpass',   'bob@example.com',   false);

INSERT INTO pools (name, address) VALUES ('Árbæjarlaug', 'Fylkisvegur 9, 110 Reykjavík');
INSERT INTO pools (name, address) VALUES ('Breiðholtslaug', 'Austurberg 5, 111 Reykjavík');
INSERT INTO pools (name, address) VALUES ('Dalslaug', 'Úlfarsbraut 122, 113 Reykjavík');
INSERT INTO pools (name, address) VALUES ('Grafarvogslaug', 'Dalhús 2, 112 Reykjavík');
INSERT INTO pools (name, address) VALUES ('Klébergslaug', 'Kjalarnesi, 116 Reykjavík');
INSERT INTO pools (name, address) VALUES ('Laugardalslaug', 'Sundlaugavegur 30, 105 Reykjavík');
INSERT INTO pools (name, address) VALUES ('Sundhöll Reykjavíkur', 'Barónsstígur 45a, 101 Reykjavík');
INSERT INTO pools (name, address) VALUES ('Vesturbæjarlaug', 'Hofsvallagata, 107 Reykjavík');

-- Example inserts for reviews/checkins for all users
INSERT INTO reviews (user_id, pool_id, rating, comment) VALUES
    (1, 1, 5, 'Excellent!'),
    (2, 2, 4, 'Very good pool.'),
    (3, 3, 3, 'Average experience.'),
    (4, 4, 5, 'Loved it!'),
    (5, 5, 4, 'Nice and clean.'),
    (6, 6, 2, 'Could be better.');

INSERT INTO checkins (user_id, pool_id, visited_at) VALUES
    (1, 1, now()),
    (2, 2, now()),
    (3, 3, now()),
    (4, 4, now()),
    (5, 5, now()),
    (6, 6, now());