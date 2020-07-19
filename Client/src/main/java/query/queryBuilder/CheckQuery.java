package query.queryBuilder;

import query.enums.Color;
import query.enums.Country;
import query.enums.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public final class CheckQuery {
    //todo чекнуть все провер очки

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

    private static boolean isStatusIncorrect(String userInput){
        try {
            Status.valueOf(userInput);
            return false;
        } catch (IllegalArgumentException e){
            return true;
        }
    }

    private static boolean InvalidPersonWeightNull(String userInput){
        return userInput != null;
    }

    private static boolean isPersonHairColorIncorrect(String userInput){
        try {
            Color.valueOf(userInput);
            return false;
        } catch (IllegalArgumentException e){
            return true;
        }
    }

    private static boolean isPersonNationalityIncorrect(String userInput){
        try {
            Country.valueOf(userInput);
            return false;
        } catch (IllegalArgumentException e){
            return true;
        }
    }

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
        return userInput == null || isLongFormatIncorrect(userInput) || Long.parseLong(userInput) < 0 || Long.parseLong(userInput) == 0;
    }

    public static boolean invalidStartDate(String userInput){
        return userInput == null || userInput.trim().isEmpty() || isZonedDateFormatIncorrect(userInput);
    }

    public static boolean invalidEndDate(String userInput){
        return userInput.trim().isEmpty() || isLocalDateFormatIncorrect(userInput);
    }

    public static boolean invalidStatus(String userInput){
        return isStatusIncorrect(userInput);
    }

    public static boolean invalidPersonWeight(String userInput){
        return isDoubleFormatIncorrect(userInput) || Double.parseDouble(userInput) < 0 || Double.parseDouble(userInput) == 0;
    }

    public static boolean invalidPersonHairColor(String userInput){
        return isPersonHairColorIncorrect(userInput) || userInput.trim().isEmpty();
    }

    public static boolean invalidPersonNationality(String userInput){
        return isPersonNationalityIncorrect(userInput);
    }

    public static boolean invalidId(String userInput){
        return isIntFormatIncorrect(userInput);
    }
}
