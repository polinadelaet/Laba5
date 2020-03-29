package app.collection.worker;

import app.collection.worker.xmlAdapters.ColorAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Person {
    private Double weight; //Поле может быть null, Значение поля должно быть больше 0
    @XmlJavaTypeAdapter(ColorAdapter.class)
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null

    public Person() {

    }

    public Person(Double weight, Color hairColor, Country nationality) {
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    public Double getWeight() {
        return weight;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }
}
