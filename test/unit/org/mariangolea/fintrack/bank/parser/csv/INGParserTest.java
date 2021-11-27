package org.mariangolea.fintrack.bank.parser.csv;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mariangolea.fintrack.bank.Utilities;
import org.mariangolea.fintrack.bank.parser.BankParserUtils;
import org.mariangolea.fintrack.bank.parser.ParseResponse;
import org.mariangolea.fintrack.bank.parser.csv.ing.INGParser;
import org.mariangolea.fintrack.bank.transaction.BankTransactionInterface;

public class INGParserTest extends INGParser{

    private final Utilities utils = new Utilities();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testCompleteDateParser() {

    	BankParserUtils parserUtils = new BankParserUtils(bank);
        Date output = parserUtils.parseCompletedDate("gibberish");
        assertTrue(output == null);

        output = parserUtils.parseCompletedDate("12 septembrie 2018");
        assertTrue(output != null);
        Calendar calendar = Calendar.getInstance(ROMANIAN_LOCALE);
        calendar.setTime(output);
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 12);
        assertTrue(calendar.get(Calendar.MONTH) == 8);
        assertTrue(calendar.get(Calendar.YEAR) == 2018);
    }

    @Test
    public void testAmount() {
        String input = "1.195,60";
        BankParserUtils parserUtils = new BankParserUtils(bank);
        BigDecimal output = parserUtils.parseAmount(input);
        assertTrue("Amount parsing failed.", (float) 1195.6 == output.floatValue());

        output = parserUtils.parseAmount("gibberish");
        assertTrue(output == BigDecimal.ZERO);
    }

    @Test
    public void testSupportedTransactionsINGRoundTrip() throws IOException {
        String[] mockData = utils.constructMockCSVContentForING();
        folder.create();
        File csvFile = utils.writeCSVFile(folder.newFile("test.csv"), mockData);
        assertTrue(null != csvFile);

        ParseResponse response = parseTransactions(csvFile);
        assertTrue(null != response);

        // ING CSV files are dumber than BT ones. They end with a signature text which is irrelevant, but no way of taking it out programtically...
        assertTrue(response.unprocessedStrings.isEmpty());
        assertTrue(response.parsedTransactions != null && response.parsedTransactions.size() == 3);
    }

    @Test
    public void testMethodsBasic() {
        assertTrue(findNextTransactionLineIndex(null) == -1);
        
        List<String> impropper = new ArrayList<>();
        impropper.add("12 septembrie 2018,,,Cumparare POS,,\"\",");
        
        BankTransactionInterface trans = parseTransaction(impropper);
        assertTrue(trans == null);
        
        impropper.clear();
        impropper.add("12 septembrie 2018,,,,,\"\",");
        trans = parseTransaction(impropper);
        assertTrue(trans == null);
        
        impropper.clear();
        impropper.add("12 kashmir 2018,,,,,\"\",");
        trans = parseTransaction(impropper);
        assertTrue(trans == null);


        impropper.clear();
        impropper.add(",,,\"\",");
        trans = parseTransaction(impropper);
        assertTrue(trans == null);

                impropper.clear();
        impropper.add("12 kashmir 2018,,,,,\"\",");
        trans = parseTransaction(impropper);
        assertTrue(trans == null);

    }

}
