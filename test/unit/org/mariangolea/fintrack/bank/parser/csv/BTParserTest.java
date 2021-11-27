package org.mariangolea.fintrack.bank.parser.csv;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mariangolea.fintrack.bank.Utilities;
import org.mariangolea.fintrack.bank.parser.BankParserUtils;
import org.mariangolea.fintrack.bank.parser.ParseResponse;
import org.mariangolea.fintrack.bank.parser.csv.bancatransilvania.BTParser;


public class BTParserTest extends BTParser {

    private final Utilities utils = new Utilities();
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testCompleteDateParser() {

    	BankParserUtils parserUtils = new BankParserUtils(bank);
        Date output = parserUtils.parseCompletedDate("gibberish");
        assertTrue(output == null);

        output = parserUtils.parseCompletedDate("2018-08-12");
        assertTrue(output != null);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(output);
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 12);
        assertTrue(calendar.get(Calendar.MONTH) == 7);
        assertTrue(calendar.get(Calendar.YEAR) == 2018);
    }

    @Test
    public void testStartedDateParser() {
    	BankParserUtils parserUtils = new BankParserUtils(bank);
        Date output = parserUtils.parseStartDate("gibberish");
        assertTrue(output == null);

        output = parserUtils.parseStartDate("2018-08-12");
        assertTrue(output != null);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(output);
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 12);
        assertTrue(calendar.get(Calendar.MONTH) == 7);
        assertTrue(calendar.get(Calendar.YEAR) == 2018);
    }

    @Test
    public void testAmount() {
        String input = "1,195.60";
        BankParserUtils parserUtils = new BankParserUtils(bank);
        BigDecimal output = parserUtils.parseAmount(input);
        assertTrue("Amount parsing failed.", (float) 1195.6 == output.floatValue());

        output = parserUtils.parseAmount("gibberish");
        assertTrue(output == BigDecimal.ZERO);
    }

    @Test
    public void testSupportedTransactionsBTRoundTrip() throws IOException {
        String[] mockData = utils.constructMockCSVContentForBT();
        folder.create();
        File csvFile = utils.writeCSVFile(folder.newFile("test.csv"), mockData);
        assertTrue(null != csvFile);

        ParseResponse response = parseTransactions(csvFile);
        assertTrue(null != response);

        // we expect a unrecognized string.
        assertTrue(!response.allContentProcessed);
        assertTrue(response.foundTransactionsNumber == 3);
    }

    @Test
    public void testMethodsBasic() {
        assertTrue(findNextTransactionLineIndex(null) == 1);
    }
}
