package extravagant.cafe.menu;

import java.util.Map;

public final class Drink extends SharedMenuItemImplementation {
    public Drink(String name, double price, Map<String, Integer> composition) {
        super(name, price);
        this.composition = composition;
        // this.type = "Drink";
    }

    @Override
    public final void add_ingredient(String name, int count) {
        if (composition.containsKey(name)) {
            System.out.println("Ingredient \"" + name + "\" is already present!");
        }
        else {
            this.composition.put(name, count);
        }
    }
}