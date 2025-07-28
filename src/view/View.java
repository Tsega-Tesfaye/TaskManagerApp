package view;

/**
 * Interface to define how CLI will present options to the user.
 */
public interface View {
    void showMainMenu();
    void showUserMenu();
    void showTaskMenu();
}