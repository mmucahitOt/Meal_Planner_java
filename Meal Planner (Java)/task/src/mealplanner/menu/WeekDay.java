package mealplanner.menu;

import java.util.InputMismatchException;

public enum WeekDay {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private String name;

    WeekDay(String name) {
        this.name = name;
    }


    public static WeekDay convertStringToWeekDay(String input) {
        switch (input) {
            case "Monday":
                return MONDAY;
            case "Tuesday":
                return TUESDAY;
            case "Wednesday":
                return WEDNESDAY;
            case "Thursday":
                return THURSDAY;
            case "Friday":
                return FRIDAY;
            case "Saturday":
                return SATURDAY;
            case "Sunday":
                return SUNDAY;
            default:
                throw new InputMismatchException();
        }
    }

    public static WeekDay[] weekDays() {
        return new WeekDay[]{MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
       return this.name;
    }


}
