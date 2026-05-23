package extravagant.cafe.menu;

import java.util.Map;

public interface MenuItem {
    String get_name();
    double get_price();
    void set_price(double p);
    Map<String, Integer> get_composition();
    void update_ingredient_count(String name, int count);
    void remove_ingredient(String name);
    void add_ingredient(String name, int count);
}