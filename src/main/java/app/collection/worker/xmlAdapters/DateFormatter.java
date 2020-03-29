package app.collection.worker.xmlAdapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter extends XmlAdapter<String, Date> {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    public Date unmarshal(String date) throws Exception {
        return formatter.parse(date);
    }

    public String marshal(Date date) throws Exception {
        return formatter.format(date);
    }
}
