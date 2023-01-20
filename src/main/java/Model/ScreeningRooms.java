package Model;

public class ScreeningRooms {
    private final long id_screening_room;
    private int amount_of_seats;
    private int amount_of_rows;
    private int amount_of_columns;

    public ScreeningRooms(long id_screening_room, int amount_of_seats, int amount_of_rows, int amount_of_columns) {
        this.id_screening_room = id_screening_room;
        this.amount_of_seats = amount_of_seats;
        this.amount_of_rows = amount_of_rows;
        this.amount_of_columns = amount_of_columns;
    }

    public long getId_screening_room() {
        return id_screening_room;
    }

    public int getAmount_of_seats() {
        return amount_of_seats;
    }

    public void setAmount_of_seats(int amount_of_seats) {
        this.amount_of_seats = amount_of_seats;
    }

    public int getAmount_of_rows() {
        return amount_of_rows;
    }

    public void setAmount_of_rows(int amount_of_rows) {
        this.amount_of_rows = amount_of_rows;
    }

    public int getAmount_of_columns() {
        return amount_of_columns;
    }

    public void setAmount_of_columns(int amount_of_columns) {
        this.amount_of_columns = amount_of_columns;
    }

    @Override
    public String toString() {
        return "id_screening_room=" + id_screening_room +
                ", amount_of_seats=" + amount_of_seats +
                ", amount_of_rows=" + amount_of_rows +
                ", amount_of_columns=" + amount_of_columns;
    }
}
