CREATE TABLE transaction (
                               id INT,
                               description VARCHAR(50),
                               order_id VARCHAR(50),
                               session_id VARCHAR(50),
                               amount INT,
                               currency_number INT,
                               status VARCHAR(50),
                               kapital_status VARCHAR(50),
                               created_on DATE,
                               updated_on DATE
);


-- INSERT INTO transaction(description, order_id, session_id, amount, currency_number, status, kapital_status, created_on, updated_on) VALUES
-- (
--     'payment',
--     'qwerty',
--     'qwertyuiop',
--     700,
--     944,
--     'SUCCESS',
--     'SUCCESS',
--     '2030-03-23 13:10:33.000000',
--     '2040-04-23 13:10:33.000000',
-- );