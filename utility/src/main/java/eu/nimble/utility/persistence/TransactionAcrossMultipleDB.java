package eu.nimble.utility.persistence;

/**
 * This interface makes sure that all operations across multiple databases (i.e.,repositories) are performed in a single transaction.
 * If all operations are successful, the changes are committed via {@link #commit()} method, otherwise {@link #rollback()} method undoes all changes.
 * */
public interface TransactionAcrossMultipleDB {

    /**
     * Begins the transaction which is the only one used for all operations
     * */
    void beginTransaction();

    /**
     * Commits the changes
     * */
    void commit();

    /**
     * Undoes all changes
     * */
    void rollback();
}