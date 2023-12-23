package anthos.samples.bankofanthos.ledgermonolith;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for performing queries on the Transaction database
 */
@Repository
public interface TransactionRepository
        extends CrudRepository<Transaction, Long> {

    @Query(value = "SELECT "
    + "(SELECT SUM(AMOUNT) FROM TRANSACTIONS t "
    + "     WHERE (TO_ACCT = ?1 AND TO_ROUTE = ?2)) - "
    + " (SELECT COALESCE((SELECT SUM(AMOUNT) FROM TRANSACTIONS t "
    + "     WHERE (FROM_ACCT = ?1 AND FROM_ROUTE = ?2)),0))",
        nativeQuery = true)
    Long findBalance(String accountNum, String routeNum);

    /**
     * Returns the id of the latest transaction, or NULL if none exist.
     */
    @Query("SELECT MAX(transactionId) FROM Transaction")
    Long latestTransactionId();

    @Query("SELECT t FROM Transaction t "
        + " WHERE (t.fromAccountNum=?1 AND t.fromRoutingNum=?2) "
        + "   OR (t.toAccountNum=?1 AND t.toRoutingNum=?2) "
        + " ORDER BY t.timestamp DESC")
    LinkedList<Transaction> findForAccount(String accountNum,
                                           String routingNum,
                                           Pageable pager);

    @Query("SELECT t FROM Transaction t "
        + " WHERE t.transactionId > ?1 ORDER BY t.transactionId ASC")
    List<Transaction> findLatest(long latestTransaction);
}
