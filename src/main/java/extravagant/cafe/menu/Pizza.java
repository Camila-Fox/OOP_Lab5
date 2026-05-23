package extravagant.cafe.menu;

import java.util.List;
import java.util.Map;

public final class Pizza extends Dish {
    List<String> ALLOWED_INGREDIENTS = Dish.DISH_INGREDIENTS.get("Pizza");

    public Pizza(String name, double price, Map<String, Integer> composition) {
        super(name, price, composition);
        //this.type += "Pizza";
    }

    @Override
    public void add_ingredient(String name, int count) {
        if (!ALLOWED_INGREDIENTS.contains(name)) {
            System.out.println("Ingredient \"" + name + "\" is not allowed for this dish!");
        }
        else if (composition.containsKey(name)) {
            System.out.println("Ingredient \"" + name + "\" is already present!");
        }
        else {
            this.composition.put(name, count);
        }
    }
}