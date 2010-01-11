package com.greatrun;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.meterware.httpunit.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Scraper {
    private final PaginationDataExtractor paginationDataExtractor;

    @Inject
    public Scraper(PaginationDataExtractor paginationDataExtractor) {

        this.paginationDataExtractor = paginationDataExtractor;
    }

    public void dump() throws Exception {
        processAllPages(performInitialSearch());
    }

    private HTMLSegment performInitialSearch() throws IOException, SAXException {
        WebConversation client = new WebConversation();
        WebResponse response = client.getResponse("http://raceresults.greatrun.org/results.aspx?race=144");

        WebForm form = response.getFormWithID("form1");
        SubmitButton button = form.getSubmitButton("DoSearch");
        return form.submit(button);
    }

    private void processAllPages(HTMLSegment currentResultsPageHtml) throws Exception {
        int totalNumResultsPages = paginationDataExtractor.totalNumberOfAvailablePagesFrom(currentResultsPageHtml);
        System.out.println("totalNumResultsPages="+totalNumResultsPages);
        for (int page=1;page<=totalNumResultsPages;++page) {
            currentResultsPageHtml=jumpToPage(page, currentResultsPageHtml);
            extractDataFrom(currentResultsPageHtml);
        }
    }

    private void extractDataFrom(HTMLSegment response) throws SAXException {
        WebTable resultsTable = response.getTableWithID("ResultsGrid");
        List<TableRow> rows = asList(resultsTable.getRows());
        for (int row=0;row<resultsTable.getRowCount();++row) {
            List<String> rowText=new ArrayList<String>();
            for (int col=0;col<resultsTable.getColumnCount();++col) {
                rowText.add(resultsTable.getCellAsText(row,col));
            }
            String line="\""+ Joiner.on("\",\"").join(rowText)+"\"";
            System.out.println(line);
        }
    }

    private HTMLSegment jumpToPage(int page, HTMLSegment response) throws Exception {
        WebForm form = response.getFormWithID("form1");
        form.setParameter("JumpPage",""+page);
        SubmitButton button = form.getSubmitButton("JumpPageButton");
        return form.submit(button);
    }
}
