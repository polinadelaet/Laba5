package app.collection.worker;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class LocalDateFormatter extends XmlAdapter<String, LocalDate> {

    public LocalDate unmarshal(String date) throws Exception {
        return LocalDate.parse(date);
    }

    public String marshal(LocalDate date) throws Exception {
        return date.toString();
    }
}
