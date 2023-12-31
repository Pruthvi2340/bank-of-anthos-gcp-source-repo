package anthos.samples.bankofanthos.balancereader;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines a banking transaction.
 *
 * Timestamped at object creation time.
 */
@Entity
@Table(name = "TRANSACTIONS")
public final class Transaction {
    @Id
    @Column(name = "TRANSACTION_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;
    @Column(name = "FROM_ACCT", nullable = false, updatable = false)
    @JsonProperty("fromAccountNum")
    private String fromAccountNum;
    @Column(name = "FROM_ROUTE", nullable = false, updatable = false)
    @JsonProperty("fromRoutingNum")
    private String fromRoutingNum;
    @Column(name = "TO_ACCT", nullable = false, updatable = false)
    @JsonProperty("toAccountNum")
    private String toAccountNum;
    @Column(name = "TO_ROUTE", nullable = false, updatable = false)
    @JsonProperty("toRoutingNum")
    private String toRoutingNum;
    @Column(name = "AMOUNT", nullable = false, updatable = false)
    @JsonProperty("amount")
    private Integer amount;
    @Column(name = "TIMESTAMP", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonProperty("timestamp")
    private Date timestamp;

    private static final double CENTS_PER_DOLLAR = 100.0;

    public long getTransactionId() {
        return transactionId;
    }

    public String getFromAccountNum() {
        return fromAccountNum;
    }

    public String getFromRoutingNum() {
        return fromRoutingNum;
    }

    public String getToAccountNum() {
        return toAccountNum;
    }

    public String getToRoutingNum() {
        return toRoutingNum;
    }

    public Integer getAmount() {
        return amount;
    }
    /**
     * String representation.
     *
     * Formatting = "{accountNum}:{type}:{amount}"
     */
    public String toString() {
        return String.format("%s->$%.2f->%s",
                fromAccountNum, amount / CENTS_PER_DOLLAR, toAccountNum);
    }
}
