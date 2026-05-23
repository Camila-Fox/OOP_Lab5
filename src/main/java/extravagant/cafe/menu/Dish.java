package extravagant.cafe.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract public class Dish extends SharedMenuItemImplementation {

    // public final HashSet<String> ALLOWED_INGREDIENTS = new HashSet<String>(Arrays.asList("Pepperoni", "Dough", "Ketchup", "Cheese", "Olives", "Salad", "Eggs", "Sausage", "Salmon", "Tuna", "BBQ Sauce"));
    // public final HashSet<String> ALLOWED_INGREDIENTS = new HashSet<String>(Arrays.asList("Cheese", "Olives", "Salad", "Eggs", "Sausage", "Salmon", "Tuna", "Bread", "Tomatoes", "Cucumbers", "Chicken"));

    protected static final Map<String, List<String>> DISH_INGREDIENTS = Map.of(
        "Pizza", List.of("Pepperoni", "Dough", "Ketchup", "Cheese", "Olives", "Salad", "Eggs", "Sausage", "Salmon", "Tuna", "BBQ Sauce"),
        "Salad", List.of("Cheese", "Olives", "Salad", "Eggs", "Sausage", "Salmon", "Tuna", "Bread", "Tomatoes", "Cucumbers", "Chicken"),
        "CustomDish", List.of()
    );

    @FunctionalInterface
    public interface DishConstructor {
        Dish construct(String name, double price, Map<String, Integer> composition);
    }

    private static final Map<String, DishConstructor> DISH_FACTORY = Map.of(
        "Pizza", Pizza::new,
        "Salad", Salad::new,
        "CustomDish", CustomDish::new
    );

    protected Dish(String name, double price, Map<String, Integer> composition) {
        super(name, price);
        this.composition = composition;
        //this.type = "Dish:";
    }

    public static List<String> get_supported_types() {
        return new ArrayList<>(DISH_INGREDIENTS.keySet());
    }

    public static List<String> get_ingredient_types(String dtype) {
        return DISH_INGREDIENTS.getOrDefault(dtype, new ArrayList<>());
    }

    public static Dish instantiate(String name, double price, Map<String, Integer> composition, String type) {
        DishConstructor constructor = DISH_FACTORY.get(type);
        
        if (constructor == null) {
            System.out.println("Cannot instantiate unknown dish type: " + type);
            return null;
        }
        
        return constructor.construct(name, price, composition);
    }
}