package extravagant.cafe.menu;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MenuRepository {
    private final JdbcTemplate jdbc;

    public MenuRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insert_item(String name, String baseType, String specificType, double price) {
        String sql = "INSERT INTO menu_item (name, base_type, specific_type, price) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, name, baseType, specificType, price);
    }

    public void insert_composition(String itemName, String ingredientName, int quantity) {
        String sql = "INSERT INTO item_composition (item_name, ingredient_name, quantity) VALUES (?, ?, ?)";
        jdbc.update(sql, itemName, ingredientName, quantity);
    }

    public void update_price(String itemName, double newPrice) {
        String sql = "UPDATE menu_item SET price = ? WHERE name = ?";
        jdbc.update(sql, newPrice, itemName);
    }

    public void delete_item(String itemName) {
        String sql = "DELETE FROM menu_item WHERE name = ?";
        jdbc.update(sql, itemName);
    }

    public void upsert_component(String itemName, String componentName, int quantity) {
        String sql = "INSERT INTO item_composition (item_name, ingredient_name, quantity) " +
                     "VALUES (?, ?, ?) " +
                     "ON CONFLICT (item_name, ingredient_name) " +
                     "DO UPDATE SET quantity = EXCLUDED.quantity";
        jdbc.update(sql, itemName, componentName, quantity);
    }

    public void delete_component(String itemName, String componentName) {
        String sql = "DELETE FROM item_composition WHERE item_name = ? AND ingredient_name = ?";
        jdbc.update(sql, itemName, componentName);
    }

    public ArrayList<MenuItem> fetch_all_items() {
        ArrayList<MenuItem> fullMenu = new ArrayList<>();
        
        String itemsSql = "SELECT name, base_type, specific_type, price FROM menu_item";
        
        jdbc.query(itemsSql, rs -> {
            String name = rs.getString("name");
            String baseType = rs.getString("base_type");
            String specificType = rs.getString("specific_type");
            double price = rs.getDouble("price");

            String compSql = "SELECT ingredient_name, quantity FROM item_composition WHERE item_name = ?";
            java.util.Map<String, Integer> comp = new java.util.HashMap<>();
            
            jdbc.query(compSql, compRs -> {
                comp.put(compRs.getString("ingredient_name"), compRs.getInt("quantity"));
            }, name);

            if ("Drink".equals(baseType)) {
                fullMenu.add(new Drink(name, price, comp));
            } else if ("Dish".equals(baseType)) {
                fullMenu.add(Dish.instantiate(name, price, comp, specificType));
            }
        });

        return fullMenu;
    }
}