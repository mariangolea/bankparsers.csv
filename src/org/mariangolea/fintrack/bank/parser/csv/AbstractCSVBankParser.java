package org.mariangolea.fintrack.bank.parser.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.mariangolea.fintrack.bank.parser.api.AbstractBankParser;
import org.mariangolea.fintrack.bank.parser.api.Bank;

public abstract class AbstractCSVBankParser extends AbstractBankParser {
	public AbstractCSVBankParser(Bank bank) {
		super(bank);
	}

	public final CSVRecord parseSingleLine(final String singleLine) {
		Reader in = new StringReader(singleLine);
		CSVRecord record = null;
		try (CSVParser parser = new CSVParser(in, CSVFormat.EXCEL)){
			List<CSVRecord> list = parser.getRecords();
			record = list.get(0);
			if (!mandatoryChecks(record)) {
				return null;
			}
		} catch (IOException ex) {}

		return record;
	}

	private boolean mandatoryChecks(final CSVRecord record) {
		if (record == null || bank.maxRecordSize > record.size()) {
			return false;
		}

		for (int index : bank.mandatoryRecordIndexes) {
			if (record.get(index) == null) {
				return false;
			}
		}

		return true;
	}
}
