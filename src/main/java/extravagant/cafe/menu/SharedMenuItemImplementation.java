package extravagant.cafe.menu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class SharedMenuItemImplementation implements MenuItem {
    // protected String type;
    public String name;
    public double price;

    public Map<String, Integer> composition = new HashMap<>();

    protected SharedMenuItemImplementation(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String get_name() {
        return this.name;
    }

    public final double get_price() {
        return this.price;
    }

    public final void set_price(double p) {
        this.price = p;
    }

    public final Map<String, Integer> get_composition() {
        return Collections.unmodifiableMap(composition);
    }

    public final void update_ingredient_count(String name, int count) {
        if (!composition.containsKey(name)) {
            composition.put(name, count);
        }
        else {
            composition.put(name, count);
        }
    }

    public void remove_ingredient(String name) {
        composition.remove(name);
    }

    abstract public void add_ingredient(String name, int count);
}