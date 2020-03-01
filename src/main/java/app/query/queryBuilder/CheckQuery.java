package app.query.queryBuilder;


import app.collection.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class CheckQuery {


    private static boolean isInputNull(String userInput){
        return userInput == null;
    }

    private static boolean isInputIsEmptyLIne(String userInput){
        return userInput.trim().isEmpty();
    }

    private static boolean isIntFormatIncorrect(String userInput){
        try {
            Integer.parseInt(userInput);
            return false;
        } catch (NumberFormatException e){
            return true;
        }
    }


    private static boolean isDoubleFormatIncorrect(String userInput){
        try {
            Double.parseDouble(userInput);
            return false;
        } catch (NumberFormatException e){
            return true;
        }
    }

    private static boolean isLongFormatIncorrect(String userInput){
        try {
            Long.parseLong(userInput);
            return false;
        } catch (NumberFormatException e){
            return true;
        }
    }

    private static boolean isZonedDateFormatIncorrect(String userInput){
        try {
            ZonedDateTime.parse(userInput);
            return false;
        } catch (DateTimeParseException e){
            return true;
        }
    }

    private static boolean isLocalDateFormatIncorrect(String userInput){
        try {
            LocalDate.parse(userInput);
            return false;
        } catch (DateTimeParseException e){
            return true;
        }
    }

    private static boolean isStatucIncorrect(String userInput){
        try {
            Status.valueOf(userInput);
            return false;
        } catch (IllegalArgumentException e){
            return true;
        }
    }

    //TODO написать методы для каждой команды(вынести из эддкверибилдерсюда)
    public static boolean invalidName(String userInput){
        return userInput == null || userInput.trim().isEmpty();
    }

    public static boolean invalidCoordinateX(String userInput){
        return userInput == null || isDoubleFormatIncorrect(userInput);
    }

    public static boolean invalidCoordinateY(String userInput){
        return userInput == null || isIntFormatIncorrect(userInput);
    }

    public static boolean invalidSalary(String userInput){
        return userInput == null || isLongFormatIncorrect(userInput) || Long.parseLong(userInput) > 0;
    }

    public static boolean invalidStartDate(String userInput){
        return userInput == null || userInput.trim().isEmpty() || isZonedDateFormatIncorrect(userInput);
    }

    public static boolean invalidEndDate(String userInput){
        return userInput == null || userInput.trim().isEmpty() || isLocalDateFormatIncorrect(userInput);
    }

    public static boolean invalidStatus(String userInput){
        return isStatucIncorrect(userInput);
    }
}
