package org.mariangolea.fintrack.bank.transaction.csv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.mariangolea.fintrack.bank.transaction.api.BankTransaction;
import org.mariangolea.fintrack.bank.transaction.api.BankTransactionAbstractGroup;
import org.mariangolea.fintrack.bank.transaction.api.BankTransactionGroupInterface;

public class BankTransactionCompanyGroup extends BankTransactionAbstractGroup {

    private final List<BankTransaction> list = new ArrayList<>();
    private BigDecimal amount = BigDecimal.ZERO;

    public BankTransactionCompanyGroup(String companyDesc) {
        super(companyDesc);
        Objects.requireNonNull(companyDesc);
    }

    @Override
    public BigDecimal getTotalAmount() {
        return amount;
    }

    @Override
    public int getGroupsNumber() {
        return 0;
    }

    @Override
    public List<BankTransactionGroupInterface> getContainedGroups() {
        return Collections.emptyList();
    }

    @Override
    public List<BankTransaction> getContainedTransactions() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankTransactionCompanyGroup that = (BankTransactionCompanyGroup) o;
        return super.equals(that)
                && Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), list);
    }

    protected void addTransaction(final BankTransaction parsedTransaction) {
        list.add(parsedTransaction);
        amount = amount.add(parsedTransaction.getCreditAmount()).subtract(parsedTransaction.getDebitAmount());
    }

    protected void addTransactions(final Collection<BankTransaction> parsedTransactions) {
        parsedTransactions.forEach(transaction -> addTransaction(transaction));
    }

}
