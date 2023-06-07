package model.gamefield;

/**
 * Перечисление - Времена года (сезоны).
 */
public enum Season {
    WINTER,
    SPRING,
    SUMMER,
    AUTUMN;

    public Season next() {
        switch (this) {
            case WINTER -> {return SPRING;}
            case SPRING -> {return SUMMER;}
            case SUMMER -> {return AUTUMN;}
            case AUTUMN -> {return WINTER;}
        }
        return null;
    }

    @Override
    public String toString() {
        switch (this) {
            case WINTER -> {return "winter";}
            case SPRING -> {return "spring";}
            case SUMMER -> {return "summer";}
            case AUTUMN -> {return "autumn";}
        }
        return null;
    }
}
