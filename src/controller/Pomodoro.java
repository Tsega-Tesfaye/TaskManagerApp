package controller;

/**
 * Pomodoro technique class that helps user focus for a set time and rest.
 */
public class Pomodoro {
    private int focusMinutes;
    private int breakMinutes;

    public Pomodoro(int focusMinutes, int breakMinutes) {
        this.focusMinutes = focusMinutes;
        this.breakMinutes = breakMinutes;
    }

    public void start() {
        // Simulate pomodoro timer logic here
        System.out.println("Pomodoro started for " + focusMinutes + " minutes.");
    }

    public void breakTime() {
        // Simulate break
        System.out.println("Break time for " + breakMinutes + " minutes.");
    }
}







