USE shoppingdb;

-- Xoá dữ liệu cũ (nếu có)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE order_lines;
TRUNCATE TABLE orders;
TRUNCATE TABLE comments;
TRUNCATE TABLE products;
TRUNCATE TABLE categories;
TRUNCATE TABLE customers;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS = 1;

-- Inserting data for categories
INSERT INTO categories (name) VALUES
                                  ('Electronics'),
                                  ('Books'),
                                  ('Clothing'),
                                  ('Home Appliances'),
                                  ('Toys');

-- Inserting data for users
INSERT INTO users (name, email, age, role, password) VALUES
                                                   ('John Doe', 'john.doe@example.com', 30, 'customer', '123456'),
                                               ('Jane Smith', 'jane.smith@example.com', 25, 'customer', '123456'),
                                               ('Peter Jones', 'peter.jones@example.com', 45, 'admin', '123456'),
                                               ('Mary Williams', 'mary.williams@example.com', 35, 'customer', '123456'),
                                               ('David Brown', 'david.brown@example.com', 28, 'customer', '123456');

-- Inserting data for customers
-- Assuming the user IDs are 1, 2, 4, 5 for customers
INSERT INTO customers (id) VALUES
                               (1),
                               (2),
                               (4),
                               (5);

-- Inserting data for products
INSERT INTO products (name, price, in_stock, category_id) VALUES
                                                              ('Laptop', 1200.00, 1, 1),
                                                              ('Smartphone', 800.00, 1, 1),
                                                              ('The Great Gatsby', 15.50, 1, 2),
                                                              ('To Kill a Mockingbird', 12.00, 1, 2),
                                                              ('T-Shirt', 25.00, 1, 3),
                                                              ('Jeans', 75.00, 0, 3),
                                                              ('Microwave Oven', 150.00, 1, 4),
                                                              ('Blender', 60.00, 1, 4),
                                                              ('LEGO Star Wars Set', 99.99, 1, 5),
                                                              ('Barbie Doll', 29.99, 0, 5);

-- Inserting data for orders
INSERT INTO orders (date, customer_id) VALUES
                                           ('2023-10-01', 1),
                                           ('2023-10-02', 2),
                                           ('2023-10-03', 1),
                                           ('2023-10-04', 4),
                                           ('2023-10-05', 5);

-- Inserting data for order_lines
INSERT INTO order_lines (order_id, product_id, quantity,price) VALUES
                                                                           (1, 1, 1, 1200.00),
                                                                           (1, 3, 2, 15.50),
                                                                           (2, 2, 1, 800.00),
                                                                           (2, 5, 3, 25.00),
                                                                           (3, 4, 1, 12.00),
                                                                           (3, 7, 1, 150.00),
                                                                           (4, 8, 1, 60.00),
                                                                           (4, 9, 1, 99.99),
                                                                           (5, 1, 1, 1200.00),
                                                                           (5, 6, 2, 75.00);

-- Inserting data for comments
INSERT INTO comments (text, product_id) VALUES
                                            ('This is a great laptop!', 1),
                                            ('I love this book!', 3),
                                            ('The t-shirt is very comfortable.', 5),
                                            ('This microwave is very easy to use.', 7),
                                            ('My kids love this LEGO set!', 9);