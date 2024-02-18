import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
public class TransactionHistory {
    private static int nextTransactionId = 1;
    private List<Transaction> transactions;
    public TransactionHistory() {
        this.transactions = new ArrayList<>();
    }
    public void addTransaction(String action, String car, String customer) {
        Transaction transaction = new Transaction(nextTransactionId++, LocalDateTime.now(), action, car, customer);
        transactions.add(transaction);
    }
    public List<Transaction> getHistory() {
        return transactions;
    }
    public void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactions) {
            transaction.displayTransactionDetails();
            System.out.println("-------------------------");
        }
    }
    public class Transaction {
        private int transactionId;
        private LocalDateTime timestamp;
        private String action;
        private String car;
        private String customer;
        public Transaction(int transactionId, LocalDateTime timestamp, String action, String car, String customer) {
            this.transactionId = transactionId;
            this.timestamp = timestamp;
            this.action = action;
            this.car = car;
            this.customer = customer;
        }
        public void displayTransactionDetails() {
            System.out.println("Transaction ID: " + transactionId);
            System.out.println("Timestamp: " + timestamp);
            System.out.println("Action: " + action);
            System.out.println("Car: " + car);
            System.out.println("Customer: " + customer);
        }
    }
}
