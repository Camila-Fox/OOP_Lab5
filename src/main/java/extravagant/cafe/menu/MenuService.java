package extravagant.cafe.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
@Transactional
public class MenuService {
    private final List<String> SUPPORTED_MENU_ITEMS = List.of("Dish", "Drink");
    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }
    
    @CircuitBreaker(name = "database", fallbackMethod = "fallbackDisplay")
    public ArrayList<MenuItem> display() {
        return repository.fetch_all_items();
    }

    public ArrayList fallbackDisplay(Throwable t) {
        System.out.println("Circuit Breaker tripped! Database error: " + t.getMessage());
        return new ArrayList<>();
    }

    public List<String> get_supported_menu_items() {
        return SUPPORTED_MENU_ITEMS;
    }

    public List<String> get_supported_dish_types() {
        return Dish.get_supported_types();
    }

    public List<String> get_dish_ingredient_types(String dtype) {
        return Dish.get_ingredient_types(dtype);
    }

    public void add_drink(String name, double price, Map<String, Integer> composition) {
        if (price < 0) return;
        repository.insert_item(name, "Drink", null, price);
        
        for (var entry : composition.entrySet()) {
            repository.insert_composition(name, entry.getKey(), entry.getValue());
        }
    }

    public void add_dish(String name, double price, Map<String, Integer> composition, String type) {
        if (price < 0) return;
        Dish new_dish = Dish.instantiate(name, price, composition, type);
        if (new_dish == null) return;

        repository.insert_item(name, "Dish", type, price);

        for (var entry : composition.entrySet()) {
            repository.insert_composition(name, entry.getKey(), entry.getValue());
        }
    }

    public void update_component_count(String itemName, String componentName, int quantity) {
        if (quantity < 1) return;
        repository.upsert_component(itemName, componentName, quantity);
    }

    public void remove_component(String itemName, String componentName) {
        repository.delete_component(itemName, componentName);
    }

    public void update_price(String itemName, double newPrice) {
        if (newPrice < 0) return;
        repository.update_price(itemName, newPrice);
    }

    public void remove_item(String itemName) {
        repository.delete_item(itemName);
    }
}