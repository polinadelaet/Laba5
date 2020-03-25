package app.collection.worker;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateFormatter extends XmlAdapter<String, Date> {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    public Date unmarshal(String date) throws Exception {
        return formatter.parse(date);
    }

    public String marshal(Date date) throws Exception {
        return formatter.format(date);
    }
}
