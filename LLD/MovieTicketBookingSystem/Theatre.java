import java.util.*;

class Theatre {
    private final String name;
    private final String address;
    private final Map<String, Screen> screens;

    public Theatre(String name, String address) {
        this.name = name;
        this.address = address;
        screens = new HashMap<>();
    }

    public void addScreen(String screenId, int rows, int cols) {
        Screen screen = new Screen(screenId, this, rows, cols);
        screens.put(screenId, screen);
    }

    public Screen getScreen(String screenId) {
        return screens.get(screenId);
    }

    public String getName() {
        return name;
    }
}