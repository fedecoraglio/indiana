CREATE TABLE products (
                          prod_id BIGINT NOT NULL AUTO_INCREMENT,
                          pro_code VARCHAR(100) NOT NULL,
                          pro_barcode VARCHAR(100) NOT NULL,
                          pro_name VARCHAR(255) NOT NULL,
                          pro_description TEXT NULL,
                          pro_packed_weight DECIMAL(10,2) NOT NULL DEFAULT 0,
                          pro_packed_width DECIMAL(10,2) NOT NULL DEFAULT 0,
                          pro_packed_depth DECIMAL(10,2) NOT NULL DEFAULT 0,
                          pro_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          pro_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                          PRIMARY KEY (prod_id),
                          UNIQUE KEY uk_products_code (pro_code),
                          UNIQUE KEY uk_products_barcode (pro_barcode),
                          INDEX idx_products_name (pro_name)
);