package com.greatrun;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.HTMLSegment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaginationDataExtractorTest {

    @Mock HTMLSegment htmlSegment;
    @Mock HTMLElement pageDetailsSpan;

    @Test
    public void shouldExtractTotalNumberOfAvailablePagesFromSearchResultsHtml() throws Exception {
        when(htmlSegment.getElementWithID("PageDetails")).thenReturn(pageDetailsSpan);
        when(pageDetailsSpan.getText()).thenReturn("1507 Results Found : Displaying Page 1 of 76");

        assertThat(new PaginationDataExtractor().totalNumberOfAvailablePagesFrom(htmlSegment),equalTo(76));
    }
}
