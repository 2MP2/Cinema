package Model;

import java.sql.Timestamp;

public class TakenSeats {
    private final long id_seat;
    private Timestamp expiration_date;
    private char row_identifier;
    private int column_identifier;
    private char reserved_or_taken;
    private long id_seance;
    private long id_transaction;
    private long id_customer;

    public TakenSeats(long id_seat, Timestamp expiration_date, char row_identifier, int column_identifier, char reserved_or_taken, long id_seance, long id_transaction, long id_customer) {
        this.id_seat = id_seat;
        this.expiration_date = expiration_date;
        this.row_identifier = row_identifier;
        this.column_identifier = column_identifier;
        this.reserved_or_taken = reserved_or_taken;
        this.id_seance = id_seance;
        this.id_transaction = id_transaction;
        this.id_customer = id_customer;
    }

    public long getId_seat() {
        return id_seat;
    }

    public Timestamp getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Timestamp expiration_date) {
        this.expiration_date = expiration_date;
    }

    public char getRow_identifier() {
        return row_identifier;
    }

    public void setRow_identifier(char row_identifier) {
        this.row_identifier = row_identifier;
    }

    public int getColumn_identifier() {
        return column_identifier;
    }

    public void setColumn_identifier(int column_identifier) {
        this.column_identifier = column_identifier;
    }

    public char getReserved_or_taken() {
        return reserved_or_taken;
    }

    public void setReserved_or_taken(char reserved_or_taken) {
        this.reserved_or_taken = reserved_or_taken;
    }

    public long getId_seance() {
        return id_seance;
    }

    public void setId_seance(long id_seance) {
        this.id_seance = id_seance;
    }

    public long getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(long id_transaction) {
        this.id_transaction = id_transaction;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    @Override
    public String toString() {
        return "TakenSeats{" +
                "id_seat=" + id_seat +
                ", expiration_date=" + expiration_date +
                ", row_identifier=" + row_identifier +
                ", column_identifier=" + column_identifier +
                ", reserved_or_taken=" + reserved_or_taken +
                ", id_seance=" + id_seance +
                ", id_transaction=" + id_transaction +
                ", id_customer=" + id_customer +
                '}';
    }
}
