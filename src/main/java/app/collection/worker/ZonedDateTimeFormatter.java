package app.collection.worker;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.SimpleTimeZone;

public class ZonedDateTimeFormatter extends XmlAdapter<String, ZonedDateTime> {

    public ZonedDateTime unmarshal(String date) throws Exception {
        return ZonedDateTime.parse(date);
    }

    public String marshal(ZonedDateTime date) throws Exception {
        return date.toString();
    }
}
