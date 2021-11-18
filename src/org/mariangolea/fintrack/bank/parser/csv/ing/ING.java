package org.mariangolea.fintrack.bank.parser.csv.ing;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.mariangolea.fintrack.bank.parser.api.AbstractBankParser;
import org.mariangolea.fintrack.bank.parser.api.Bank;

class ING extends Bank {

    public ING() {
        super(AbstractBankParser.ROMANIAN_LOCALE,
                "Data,,,Detalii tranzactie,,Debit,Credit",
                "", 
                new SimpleDateFormat("dd MMM yyyy", AbstractBankParser.ROMANIAN_LOCALE),
                new SimpleDateFormat("dd MMM yyyy", AbstractBankParser.ROMANIAN_LOCALE),
                NumberFormat.getInstance(AbstractBankParser.ROMANIAN_LOCALE),
                new int[]{0, 3, 5, 6},
                7);
    }

}
