package anthos.samples.bankofanthos.ledgermonolith;

/**
 * Class for all exception messages used in ledgermonolith.
 *
 */
public class ExceptionMessages {
    public static final String
            EXCEPTION_MESSAGE_WHEN_AUTHORIZATION_HEADER_NULL =
            "HTTP request 'Authorization' header is null";
    public static final String EXCEPTION_MESSAGE_INSUFFICIENT_BALANCE =
            "insufficient balance";
    public static final String
            EXCEPTION_MESSAGE_INVALID_NUMBER = "invalid account details";
    public static final String
            EXCEPTION_MESSAGE_NOT_AUTHENTICATED = "sender not authenticated";
    public static final String
            EXCEPTION_MESSAGE_SEND_TO_SELF = "can't send to self";
    public static final String
            EXCEPTION_MESSAGE_INVALID_AMOUNT = "invalid amount";
    public static final String EXCEPTION_MESSAGE_DUPLICATE_TRANSACTION =
            "duplicate transaction uuid";
}
