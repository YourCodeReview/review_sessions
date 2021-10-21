package animals.userinterface;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LocalMenu extends TextInterface implements Menu {
    private final Map<String, MenuEntry> menu = new LinkedHashMap<>();
    private final Map<Property, String> properties = new EnumMap<>(Property.class);
    private final MenuEntry defaultEntry = new MenuEntry("Incorrect option",
            () -> System.out.println(MessageFormat.format(get(Property.ERROR), menu.size())));

    private boolean isOnlyOnce;

    public LocalMenu() {
        super();
        for (var property : Property.values()) {
            var key = "menu.property." + property.name().toLowerCase();
            if (resourceBundle.containsKey(key)) {
                set(property, resourceBundle.getString(key));
            }
        }
    }

    @Override
    public Menu set(Property property, String value) {
        properties.put(property, value);
        return this;
    }

    @Override
    public Menu add(String description, Runnable action) {
        return this.add(String.valueOf(menu.size() + 1), description, action);
    }

    @Override
    public Menu add(String key, String description, Runnable action) {
        menu.put(key, new MenuEntry(resourceBundle.getString(description), action));
        return this;
    }

    @Override
    public Menu disable() {
        disable(String.valueOf(menu.size()));
        return this;
    }

    @Override
    public Menu disable(String key) {
        menu.get(key).isEnabled = false;
        return this;
    }

    @Override
    public Menu enable(String key) {
        menu.get(key).isEnabled = true;
        return this;
    }

    @Override
    public Menu onlyOnce() {
        isOnlyOnce = true;
        return this;
    }

    @Override
    public Menu addExit() {
        menu.put(get(Property.EXIT_KEY), new MenuEntry(get(Property.EXIT), this::onlyOnce));
        return this;
    }

    protected String get(Property property) {
        return properties.getOrDefault(property, property.getValue());
    }

    @Override
    public void run() {
        do {
            println();
            println(get(Property.TITLE));
            menu.forEach((key, entry) -> {
                if (entry.isEnabled) {
                    println(get(Property.FORMAT), key, entry);
                }
            });
            final var key = readToLowerCase();
            println();
            menu.getOrDefault(key, defaultEntry).run();
        } while (!isOnlyOnce);

    }

    static final class MenuEntry implements Runnable {
        private final String description;
        private final Runnable action;
        boolean isEnabled = true;

        MenuEntry(final String description, final Runnable action) {
            this.description = description;
            this.action = action;
        }

        @Override
        public String toString() {
            return description;
        }

        @Override
        public void run() {
            action.run();
        }
    }
}
