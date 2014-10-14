package org.demis.familh.gedcom;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

public interface GEDCOMParser {

    public void parse(Reader input, GEDCOMHandler handler) ;

    public void parse(InputStream input, GEDCOMHandler handler) ;

    public void parse(InputStream input, Charset charset, GEDCOMHandler handler) ;

    public void parse(File input, Charset charset, GEDCOMHandler handler) ;

    public void parse(File input, GEDCOMHandler handler) ;

    public void parse(String input, GEDCOMHandler handler) ;

    public void parse(String input, Charset charset, GEDCOMHandler handler) ;
}
