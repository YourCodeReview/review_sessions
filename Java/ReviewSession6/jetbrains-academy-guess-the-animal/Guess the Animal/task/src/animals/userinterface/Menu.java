package animals.userinterface;

/**
 * An interface for a console menu system.
 */
public interface Menu extends Runnable {

    /**
     * Set additional property for the menu.
     *
     * @param property to set
     * @param value    for the property
     * @return this menu
     */
    Menu set(Property property, String value);

    /**
     * Add new menu entry with key, description and action
     *
     * @param key         of menu entry, should be a digit, a letter or a keyword.
     * @param description of menu entry.
     * @param action      is an object implemented Runnable interface.
     * @return this menu
     */
    Menu add(String key, String description, Runnable action);

    /**
     * Add new menu entry with description and action.
     * The key will be number from 1 (menu.size + 1)
     *
     * @param description of menu entry
     * @param action      is an object implemented Runnable interface.
     * @return this menu
     */
    Menu add(String description, Runnable action);

    /**
     * Disable last added menu entry.
     *
     * @return this menu
     */
    Menu disable();

    /**
     * Disable menu entry with specified key
     *
     * @param key for the menu entry to disable.
     * @return this menu
     */
    Menu disable(String key);

    /**
     * Enable menu entry with specified key
     *
     * @param key for the menu entry to enable.
     * @return this menu
     */
    Menu enable(String key);

    /**
     * The menu should be finished after executing any menu entry
     *
     * @return this menu
     */
    Menu onlyOnce();

    /**
     * Add the exit entry to the menu.
     * <p>
     * The key is specified in Property.EXIT_KEY
     * The description is specified in Property.EXIT
     * <p>
     * If you would like to change default properties
     * you should do this with set method before addExit.
     *
     * @return this menu
     */
    Menu addExit();

    enum Property {
        TITLE("Choose your action:"),
        FORMAT("{0}. {1}"),
        ERROR("Please enter the number from 1 to {0} or 0 for exit."),
        EXIT("Exit"),
        EXIT_KEY("0");

        private final String value;

        Property(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}