package extravagant.cafe;

import java.util.Map;

public record MenuItemRequest(String name, double price, Map<String, Integer> composition) {}