package app.collection.worker.xmlAdapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;

public class ZonedDateTimeFormatter extends XmlAdapter<String, ZonedDateTime> {

    public ZonedDateTime unmarshal(String date) throws Exception {
        return ZonedDateTime.parse(date);
    }

    public String marshal(ZonedDateTime date) throws Exception {
        return date.toString();
    }
}
