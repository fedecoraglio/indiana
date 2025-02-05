CREATE TABLE products (
                          pro_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          pro_code VARCHAR(255) NOT NULL,
                          pro_barcode VARCHAR(255) NOT NULL,
                          pro_name VARCHAR(255) NOT NULL,
                          pro_description TEXT,
                          pro_packed_weight DOUBLE,
                          pro_packed_width DOUBLE,
                          pro_packed_depth DOUBLE,
                          pro_refrigerated BOOLEAN,
                          pro_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          pro_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE locations (
                            loc_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            loc_name VARCHAR(100) NOT NULL,
                            loc_address VARCHAR(200),
                            loc_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            loc_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE warehouses (
                            wah_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            wah_name VARCHAR(200) NOT NULL,
                            wah_loc_id BIGINT NOT NULL,
                            wah_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            wah_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (wah_loc_id) REFERENCES locations(loc_id)
);

CREATE TABLE inventories (
                             inv_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             inv_quantity_available BIGINT DEFAULT 0,
                             inv_minimum_stock_level BIGINT,
                             inv_maximum_stock_level BIGINT,
                             inv_reorder_point INT,
                             inv_prod_id BIGINT NOT NULL,
                             inv_wah_id BIGINT NOT NULL,
                             inv_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             inv_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (inv_prod_id) REFERENCES products(pro_id),
                             FOREIGN KEY (inv_wah_id) REFERENCES warehouses(wah_id)
);
