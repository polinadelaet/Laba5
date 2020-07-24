package reer;

import java.io.Serializable;

public class Kyk implements Serializable {

    private static final long serialVersionUID = 12L;
    public String name;
    public int age;

    public Kyk(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
