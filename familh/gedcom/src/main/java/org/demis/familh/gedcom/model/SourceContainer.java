package org.demis.familh.gedcom.model;

import java.util.List;

public interface SourceContainer {

    public void addSource(Source source);

    public void removeSource(Source source);

    public List<Source> getSources();
}
