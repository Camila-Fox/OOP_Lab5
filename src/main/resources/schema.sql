CREATE TABLE IF NOT EXISTS menu_item (
    name VARCHAR(255) PRIMARY KEY,
    base_type VARCHAR(50) NOT NULL,
    specific_type VARCHAR(50),
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS item_composition (
    item_name VARCHAR(255) REFERENCES menu_item(name) ON DELETE CASCADE,
    ingredient_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (item_name, ingredient_name)
);