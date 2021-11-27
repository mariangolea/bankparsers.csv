package org.mariangolea.fintrack.bank.transaction;

import java.util.Collection;
import java.util.Objects;

import org.mariangolea.fintrack.bank.parser.persistence.transactions.BankTransactionText;
import org.mariangolea.fintrack.bank.transaction.BankTransactionTextInterface;

public class CsvBankTransactionText implements BankTransactionTextInterface {
	BankTransactionText dbText;

	public CsvBankTransactionText(BankTransactionText text) {
		this.dbText = Objects.requireNonNull(text);
	}

	public CsvBankTransactionText(Collection<String> text) {
		StringBuffer buffer = new StringBuffer();
		for (String line : text) {
			buffer.append(line).append("\n");
		}
		this.dbText = new BankTransactionText(buffer.toString());
	}

	@Override
	public String getOriginalContent() {
		return dbText.getOriginalContent();
	}

	@Override
	public void setOriginalContent(String originalContent) {
		dbText.setOriginalContent(originalContent);
	}
}
