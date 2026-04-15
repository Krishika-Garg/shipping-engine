INSERT INTO seller (id, latitude, longitude, name)
VALUES (1, 12.97, 77.59, 'Nestle Seller');

INSERT INTO customers (id, name, phone, latitude, longitude)
VALUES (1, 'Shree Kirana Store', '9847000000', 11.232, 23.445495);

INSERT INTO warehouse (id, latitude, longitude, name)
VALUES (1, 12.99999, 77.923273, 'BLR_Warehouse');

INSERT INTO warehouse (id, latitude, longitude, name)
VALUES (2, 19.076, 72.8777, 'MUMB_Warehouse');

INSERT INTO product (id, name, price, weight, length, width, height, seller_id)
VALUES (1, 'Maggie 500g Packet', 10.0, 0.5, 10, 10, 10, 1);