package app.collection.worker.xmlAdapters;

import app.collection.worker.Color;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ColorAdapter extends XmlAdapter<String, Color> {
    @Override
    public Color unmarshal(String v) throws Exception {
        return Color.valueOf(v);
    }

    @Override
    public String marshal(Color v) throws Exception {
        return v.toString();
    }
}
