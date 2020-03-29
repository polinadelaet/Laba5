package app.collection.worker.xmlAdapters;

import app.collection.worker.Status;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StatusAdapter extends XmlAdapter<String, Status> {
    @Override
    public Status unmarshal(String v) throws Exception {
        return Status.valueOf(v);
    }

    @Override
    public String marshal(Status v) throws Exception {
        return v.toString();
    }
}
