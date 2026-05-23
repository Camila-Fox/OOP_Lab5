package extravagant.cafe.menu;

import java.util.Map;

public final class CustomDish extends Dish {
    public CustomDish(String name, double price, Map<String, Integer> composition) {
        super(name, price, composition);
        //this.type += "CustomDish";
    }

    @Override
    public void add_ingredient(String name, int count) {
        if (composition.containsKey(name)) {
            System.out.println("Ingredient \"" + name + "\" is already present!");
        }
        else {
            this.composition.put(name, count);
        }
    }
}