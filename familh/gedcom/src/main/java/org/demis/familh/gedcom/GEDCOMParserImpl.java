package org.demis.familh.gedcom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;

public class GEDCOMParserImpl implements GEDCOMParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(GEDCOMParserImpl.class);

    @Override
    public void parse(Reader input, GEDCOMHandler handler) {
        String line;
        int lineNumber = 1;
        BufferedReader reader = new BufferedReader(input);
        try {
            while (!(line = reader.readLine()).contains("0 " + "TRLR")) {
                LOGGER.debug("read line : " + line);
                handler.handle(parseLine(line, lineNumber++));
            }
        } catch (IOException e) {
            LOGGER.error("Error in parsing input", e);
        }
    }

    @Override
    public void parse(InputStream input, GEDCOMHandler handler) {
        parse(input, Charset.forName("UTF-8"), handler);
    }

    @Override
    public void parse(InputStream input, Charset charset, GEDCOMHandler handler) {
        if (input == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Handler is null");
        }

        InputStreamReader reader = new InputStreamReader(input, charset);
        parse(reader, handler);
    }

    @Override
    public void parse(File input, GEDCOMHandler handler) {
        parse(input, Charset.forName("UTF-8"), handler);
    }

    @Override
    public void parse(File input, Charset charset, GEDCOMHandler handler) {
        if (input == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Handler is null");
        }
        Reader stream;
        try {
            stream = new InputStreamReader(new FileInputStream(input), charset);
            parse(stream, handler);
        } catch (FileNotFoundException ex) {
            LOGGER.error(null, ex);
        }
    }

    @Override
    public void parse(String input, GEDCOMHandler handler) {
        parse(input, Charset.forName("UTF-8"), handler);
    }

    @Override
    public void parse(String input, Charset charset, GEDCOMHandler handler) {
        Reader stream;
        try {
            stream = new InputStreamReader(new FileInputStream(new File(input)), charset);
            parse(stream, handler);
        } catch (FileNotFoundException ex) {
            LOGGER.error(null, ex);
        }
    }

    public GEDCOMTuple parseLine(String line, int lineNumber) {
        if (line != null && line.length() > 0 && line.endsWith("\n")) {
            line = line.substring(0, line.length() - 1);
        }
        GEDCOMTuple tuple = new GEDCOMTuple();
        tuple.setLineNumber(lineNumber);
        tuple.setLevel(Integer.parseInt(line.substring(0,1)));
        // line kind '<level> @<ref>@ <code> <info>'
        if (line.matches("[0-9]{1} @[a-zA-Z0-9]*@ [a-zA-Z0-9]+ .*")) {
            String subline = line.substring(line.indexOf('@') + 1);
            String code = subline.substring(subline.indexOf('@') + 2);
            tuple.setInfo(code.substring(code.indexOf(' ') + 1));
            tuple.setCode(code.substring(0, code.indexOf(' ')));
            tuple.setRef(subline.substring(0, subline.indexOf('@')));
        } else if (line.matches("[0-9]{1} @[a-zA-Z0-9]*@ [a-zA-Z0-9]+")) {
            // line kind '<level> @<ref>@ <code>'
            String subline = line.substring(line.indexOf('@') + 1);
            String code = subline.substring(subline.indexOf('@') + 2);
            tuple.setCode(code);
            tuple.setRef(subline.substring(0, subline.indexOf('@')));
        } else if (line.matches("[0-9]{1} [a-zA-Z]{4} @[a-zA-Z0-9]+@")) {
            // line kind '<level> <code> @<ref>@'
            tuple.setCode(line.substring(2,6));
            tuple.setRef(line.substring(8, line.length() -1));
        } else if (line.matches("[0-9]{1} [a-zA-Z]+ .*")) {
            // line kind '<level> <code> <info>'
            tuple.setCode(line.substring(2, line.substring(2).indexOf(' ') + 2));
            tuple.setInfo(line.substring(3 + tuple.getCode().length()));
        } else {
            // line kind '<level> <code>'
            tuple.setCode(line.substring(2));
        }
        return tuple;
    }
}
