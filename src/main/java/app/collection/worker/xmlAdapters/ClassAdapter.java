package app.collection.worker.xmlAdapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class ClassAdapter extends XmlAdapter<String, Class<?>> {
    @Override
    public Class<?> unmarshal(String v) throws Exception {
        return Class.forName(v);
    }

    @Override
    public String marshal(Class<?> v) throws Exception {
        return v.getName();
    }
}
