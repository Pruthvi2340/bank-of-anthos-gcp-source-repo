package anthos.samples.bankofanthos.ledgermonolith;

import java.util.Deque;


/**
 * Defines the account Info object used for the LedgerReaderCache
 */
public class AccountInfo {
  Long balance;
  Deque<Transaction> transactions;

  // Constructor
  public AccountInfo(Long balance,
    Deque<Transaction> transactions) {
        this.balance = balance;
        this.transactions = transactions;
    }

// Getters
  public Deque<Transaction> getTransactions() {
    return transactions;
  }

  public Long getBalance() {
    return balance;
  }
}
