package org.mariangolea.fintrack.bank.parser.csv.bancatransilvania;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.csv.CSVRecord;
import org.mariangolea.fintrack.bank.parser.csv.AbstractCSVBankParser;
import org.mariangolea.fintrack.bank.transaction.BankTransactionInterface;
import org.mariangolea.fintrack.bank.transaction.CsvBankTransaction;

public class BTParser extends AbstractCSVBankParser {

    public BTParser() {
        super(new BancaTransilvania());
    }

    @Override
    public int findNextTransactionLineIndex(List<String> toConsume) {
        return 1;
    }

    @Override
    public BankTransactionInterface parseTransaction(List<String> toConsume) {
        Objects.requireNonNull(toConsume);
        if (toConsume.size() != 1 || toConsume.get(0).isEmpty()) {
            return null;
        }
        
        CSVRecord record = parseSingleLine(toConsume.get(0));
        if (record == null) {
            return null;
        }

        Date startedDate =  utils.parseStartDate(record.get(0));
        Date completedDate =  utils.parseCompletedDate(record.get(1));
        String desc = record.get(2).trim();
        BigDecimal credit =  utils.parseAmount(record.get(4));
        BigDecimal debit =  utils.parseAmount(record.get(5));
        
        if (credit == BigDecimal.ZERO && debit == BigDecimal.ZERO) {
            return null;
        }

        return new CsvBankTransaction(startedDate, completedDate, credit.abs(), debit.abs(), desc, toConsume);
    }
}
