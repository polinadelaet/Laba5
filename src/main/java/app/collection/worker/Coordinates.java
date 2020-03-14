package app.collection.worker;

public class Coordinates {
    private Double x; //Поле не может быть null
    private Integer y; //Поле не может быть null

    public Coordinates(Double x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
