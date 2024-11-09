CREATE TABLE products (
                          pro_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          pro_code VARCHAR(255),
                          pro_barcode VARCHAR(255),
                          pro_name VARCHAR(255),
                          pro_description TEXT,
                          pro_packed_weight DOUBLE,
                          pro_packed_width DOUBLE,
                          pro_packed_depth DOUBLE,
                          pro_refrigerated BOOLEAN
);

CREATE TABLE inventories (
                             inv_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             inv_quantity_available INT,
                             inv_minimum_stock_level INT,
                             inv_maximum_stock_level INT,
                             inv_reorder_point INT,
                             inv_prod_id BIGINT,
                             inv_wah_id BIGINT,
                             FOREIGN KEY (inv_prod_id) REFERENCES products(pro_id)
);
