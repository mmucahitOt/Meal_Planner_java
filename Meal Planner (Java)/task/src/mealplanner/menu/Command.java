package mealplanner.menu;

import mealplanner.error.ErrorMessage;

import java.util.InputMismatchException;

public enum Command {
    ADD("add"),
    SHOW("show"),
    PLAN("plan"),
    SAVE("save"),
    EXIT("exit");

    private String name;

    Command(String name) {
        this.name = name;
    }


    public static Command convertStringToCommand(String input) {
        switch (input) {
            case "add":
                return ADD;
            case "show":
                return SHOW;
            case "plan":
                return PLAN;
            case "save":
                return SAVE;
            case "exit":
                return EXIT;
            default:
                throw new InputMismatchException();
        }
    }

    public String getName() {
        return name;
    }

}
