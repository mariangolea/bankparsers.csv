package org.mariangolea.fintrack.bank.transaction.csv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.transaction.api.BankTransaction;

public class BankTransactionCompanyGroupTest extends BankTransactionTest {

    @Test
    public void testAdd() {
        BankTransaction[] legal = createTestTransactions();
        Extension firstGroup = new Extension("description");
        firstGroup.addTransaction(legal[0]);
        firstGroup.addTransactions(Arrays.asList(legal[1]));

        assertTrue(firstGroup.getTotalAmount().intValue() == 2);
        assertTrue(firstGroup.getTransactionsNumber() == 2);
        assertTrue(firstGroup.getGroupsNumber() == 0);
        assertTrue(firstGroup.getCategoryName().equals("description"));
        assertTrue(firstGroup.getContainedTransactions().equals(Arrays.asList(legal)));
        assertNotNull(firstGroup.getContainedGroups());
        String toString = firstGroup.toString();
        assertTrue(toString != null
                && !toString.isEmpty()
                && toString.contains("description")
                && toString.contains(firstGroup.getTotalAmount().floatValue() + ""));
    }

    @Test
    public void testHashEquals() {
        BankTransaction[] legal = createTestTransactions();
        Extension firstGroup = new Extension("description");
        firstGroup.addTransactions(Arrays.asList(legal));

        BankTransaction[] legalCloned = createTestTransactions();
        Extension secondGroup = new Extension("description");
        secondGroup.addTransactions(Arrays.asList(legalCloned));

        assertTrue(firstGroup.equals(secondGroup));
        assertTrue(firstGroup.hashCode() == secondGroup.hashCode());
    }

    protected static class Extension extends BankTransactionCompanyGroup {

        public Extension(String companyDesc) {
            super(companyDesc);
        }

        @Override
        protected void addTransactions(Collection<BankTransaction> parsedTransactions) {
            super.addTransactions(parsedTransactions);
        }

        @Override
        protected void addTransaction(BankTransaction parsedTransaction) {
            super.addTransaction(parsedTransaction);
        }
    }
}
