package org.mariangolea.fintrack.bank.transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.mariangolea.fintrack.bank.parser.persistence.transactions.BankTransaction;
import org.mariangolea.fintrack.bank.parser.persistence.transactions.BankTransactionText;
import org.mariangolea.fintrack.bank.transaction.BankTransactionInterface;
import org.mariangolea.fintrack.bank.transaction.BankTransactionTextInterface;

public class CsvBankTransaction implements BankTransactionInterface {
	private static final long serialVersionUID = 1412765369346130958L;
	private BankTransaction dbTransaction;
	private CsvBankTransactionText dbTransactionTextWrapper;

	public CsvBankTransaction(BankTransaction dbTransaction) {
		this.dbTransaction = Objects.requireNonNull(dbTransaction);
	}

	public CsvBankTransaction(Date startedDate, Date completedDate, BigDecimal credit, BigDecimal debit, String desc,
			List<String> toConsume) {
		dbTransaction = new BankTransaction(toConsume);
		dbTransaction.setStartDate(startedDate);
		dbTransaction.setCompletedDate(completedDate);
		dbTransaction.setDescription(desc);
		dbTransaction.setCreditAmount(credit);
		dbTransaction.setDebitAmount(debit);
		dbTransactionTextWrapper = new CsvBankTransactionText(dbTransaction.getOriginalContent());
	}

	@Override
	public int compareTo(BankTransactionInterface o) {
		if (o == null) {
			return 1;
		}

		if (!(o instanceof CsvBankTransaction)) {
			return 1;
		}

		CsvBankTransaction other = (CsvBankTransaction) o;

		return dbTransaction.compareTo(other.dbTransaction);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof CsvBankTransaction)) {
			return false;
		}

		CsvBankTransaction other = (CsvBankTransaction) obj;

		return dbTransaction.equals(other.dbTransaction);
	}

	@Override
	public int hashCode() {
		return dbTransaction.hashCode();
	}

	@Override
	public BankTransactionTextInterface getOriginalContent() {
		return dbTransactionTextWrapper;
	}

	@Override
	public int getContentLines() {
		return dbTransaction.getContentLines();
	}

	@Override
	public Date getStartDate() {
		return dbTransaction.getStartDate();
	}

	@Override
	public Date getCompletedDate() {
		return dbTransaction.getCompletedDate();
	}

	@Override
	public BigDecimal getCreditAmount() {
		return dbTransaction.getCreditAmount();
	}

	@Override
	public BigDecimal getDebitAmount() {
		return dbTransaction.getDebitAmount();
	}

	@Override
	public String getDescription() {
		return dbTransaction.getDescription();
	}

	@Override
	public void setStartDate(Date startDate) {
		dbTransaction.setStartDate(startDate);
	}

	@Override
	public void setCompletedDate(Date completedDate) {
		dbTransaction.setCompletedDate(completedDate);
	}

	@Override
	public void setCreditAmount(BigDecimal creditAmount) {
		dbTransaction.setCreditAmount(creditAmount);
	}

	@Override
	public void setDebitAmount(BigDecimal debitAmount) {
		dbTransaction.setDebitAmount(debitAmount);
	}

	@Override
	public void setDescription(String description) {
		dbTransaction.setDescription(description);
	}

	@Override
	public void setOriginalContent(BankTransactionTextInterface originalContent) {
		dbTransaction.setOriginalContent(new BankTransactionText(originalContent.getOriginalContent()));
		dbTransactionTextWrapper = new CsvBankTransactionText(dbTransaction.getOriginalContent());
	}

	@Override
	public void setContentLines(int contentLines) {
		dbTransaction.setContentLines(contentLines);
	}

}
