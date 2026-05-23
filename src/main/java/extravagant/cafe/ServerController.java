package extravagant.cafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import extravagant.cafe.menu.MenuItem;
import extravagant.cafe.menu.MenuService;

@RestController
@RequestMapping("/api")
public class ServerController {
    private final MenuService menu_service;
    
    public ServerController(MenuService menu_service) {
        this.menu_service = menu_service;
    }

    @GetMapping("/menu")
    public ArrayList<MenuItem> display_menu() {
        return menu_service.display();
    }

    @GetMapping("/menu/item-types")
    public List<String> get_supported_menu_items() {
        return menu_service.get_supported_menu_items();
    }

    @GetMapping("/menu/dish-types")
    public List<String> get_supported_dish_types() {
        return menu_service.get_supported_dish_types();
    }

    @GetMapping("/menu/dish/ingredient-types")
    public List<String> get_dish_ingredient_types(@RequestParam String dtype) {
        return menu_service.get_dish_ingredient_types(dtype);
    }

    @PostMapping("/menu/drink")
    public void add_drink(@RequestBody MenuItemRequest request) {
        String name = request.name();
        double price = request.price();
        Map<String, Integer> comp = request.composition();

        menu_service.add_drink(name, price, comp);
    }

    @PostMapping("/menu/dish")
    public void add_dish(@RequestBody MenuItemRequest request, @RequestParam String type) {
        String name = request.name();
        double price = request.price();
        Map<String, Integer> comp = request.composition();

        menu_service.add_dish(name, price, comp, type);
    }

    @PutMapping("/menu/{itemName}/composition/{componentName}")
    public void update_component(
            @PathVariable String itemName, 
            @PathVariable String componentName, 
            @RequestParam int quantity) {
            
        menu_service.update_component_count(itemName, componentName, quantity);
    }

    @DeleteMapping("/menu/{itemName}/composition/{componentName}")
    public void remove_component(
            @PathVariable String itemName, 
            @PathVariable String componentName) {
            
        menu_service.remove_component(itemName, componentName);
    }

    @PutMapping("/menu/{itemName}/price")
    public void update_price(
            @PathVariable String itemName,
            @RequestParam double number) {

        menu_service.update_price(itemName, number);
    }

    @DeleteMapping("/menu/{itemName}")
    public void delete_menu_item(@PathVariable String itemName) {
        menu_service.remove_item(itemName);
    }

    @PostMapping("/brew")
    public ResponseEntity<String> brew(@RequestParam String beverage) {
        if ("tea".equalsIgnoreCase(beverage)) {
            return ResponseEntity.ok("Brewed a cup of tea!");
        }
        else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Error 418: I am a teapot!");
        }
    }
}