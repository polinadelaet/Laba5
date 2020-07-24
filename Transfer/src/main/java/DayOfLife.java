import java.io.Serializable;

public final class DayOfLife implements Serializable {

    public String name;
    public int years;

    public DayOfLife(String name, int years) {
        this.name = name;
        this.years = years;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
