package com.greatrun;

import com.google.common.base.Joiner;
import com.google.inject.Guice;
import com.meterware.httpunit.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) throws Exception {
        Scraper scraper = Guice.createInjector().getInstance(Scraper.class);
        scraper.dump();
    }

}
