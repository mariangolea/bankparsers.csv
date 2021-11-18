package org.mariangolea.fintrack.bank.transaction.csv;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.Utilities;
import org.mariangolea.fintrack.bank.parser.api.BankParserUtils;
import org.mariangolea.fintrack.bank.parser.csv.bancatransilvania.BTParser;
import org.mariangolea.fintrack.bank.transaction.api.BankTransaction;

public class BankTransactionTest {

    @Test
    public void testBankTransaction() {
        BTParser bt = new BTParser();
        BankParserUtils utils = new BankParserUtils(bt.bank);
        Date date = utils.parseCompletedDate("19-08-2018");
        BankTransaction first = createTransaction("description", BigDecimal.ONE);
        String toString = first.toString();
        assertTrue(toString != null);
        assertTrue(first.getStartDate().equals(date));
        assertTrue(first.getCompletedDate().equals(date));
        assertTrue(first.getCreditAmount() == BigDecimal.ONE);
        assertTrue(first.getDebitAmount() == BigDecimal.ZERO);
        assertTrue(first.getDescription().equals("description"));
//        assertTrue(first.getCsvContent().equals(Arrays.asList("one", "two")));
        assertTrue(first.getContentLines() == 2);

        BankTransaction second = createTransaction("description", BigDecimal.ONE);

        assertTrue(first.equals(second));
        assertTrue(first.hashCode() == second.hashCode());
    }

    @Test
    public void testSort() {
        BankTransaction first = createTransaction("a", BigDecimal.ONE, Utilities.createDate(1, 2018));
        BankTransaction second = createTransaction("a", BigDecimal.ONE, Utilities.createDate(2, 2018));
        
        assertTrue(first.compareTo(second) < 0);
        assertTrue(first.compareTo(first) == 0);
        assertTrue(second.compareTo(first) > 0);
        
        second = createTransaction("ab", BigDecimal.ONE, Utilities.createDate(1, 2018));
        assertTrue(first.compareTo(second) < 0);
    }

    protected BankTransaction[] createTestTransactions() {
        return new BankTransaction[]{createTransaction("description", BigDecimal.ONE), createTransaction("description", BigDecimal.ONE)};
    }

    protected BankTransaction createTransaction(final String category, final BigDecimal amount) {
        BTParser bt = new BTParser();
        BankParserUtils utils = new BankParserUtils(bt.bank);
        Date date = utils.parseCompletedDate("19-08-2018");
        return createTransaction(category, amount, date);
    }

    protected BankTransaction createTransaction(final String category, final BigDecimal amount, final Date completed) {
        BankTransaction first = new BankTransaction(completed, completed, amount, BigDecimal.ZERO, category,
                Arrays.asList("one", "two"));
        return first;
    }
}
