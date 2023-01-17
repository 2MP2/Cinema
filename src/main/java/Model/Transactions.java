package Model;

public class Transactions {
    private final long id_transaction;
    private double amount;
    private int amount_of_tickets;
    private int amount_of_reduced_tickets;
    private long id_seance;
    private long id_customer;

    public Transactions(long id_transaction, double amount, int amount_of_tickets, int amount_of_reduced_tickets, long id_seance, long id_customer) {
        this.id_transaction = id_transaction;
        this.amount = amount;
        this.amount_of_tickets = amount_of_tickets;
        this.amount_of_reduced_tickets = amount_of_reduced_tickets;
        this.id_seance = id_seance;
        this.id_customer = id_customer;
    }

    public long getId_transaction() {
        return id_transaction;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAmount_of_tickets() {
        return amount_of_tickets;
    }

    public void setAmount_of_tickets(int amount_of_tickets) {
        this.amount_of_tickets = amount_of_tickets;
    }

    public int getAmount_of_reduced_tickets() {
        return amount_of_reduced_tickets;
    }

    public void setAmount_of_reduced_tickets(int amount_of_reduced_tickets) {
        this.amount_of_reduced_tickets = amount_of_reduced_tickets;
    }

    public long getId_seance() {
        return id_seance;
    }

    public void setId_seance(long id_seance) {
        this.id_seance = id_seance;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id_transaction=" + id_transaction +
                ", amount=" + amount +
                ", amount_of_tickets=" + amount_of_tickets +
                ", amount_of_reduced_tickets=" + amount_of_reduced_tickets +
                ", id_seance=" + id_seance +
                ", id_customer=" + id_customer +
                '}';
    }
}
