package com.greatrun;

import com.meterware.httpunit.HTMLSegment;
import org.xml.sax.SAXException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaginationDataExtractor {

    private final Pattern pattern = Pattern.compile("Page \\d+ of (\\d+)");

    public int totalNumberOfAvailablePagesFrom(HTMLSegment htmlSegment) throws SAXException {
        String paginationText = htmlSegment.getElementWithID("PageDetails").getText();
        Matcher matcher = pattern.matcher(paginationText);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalStateException("Can't find pagination data in : "+paginationText);
    }

}
