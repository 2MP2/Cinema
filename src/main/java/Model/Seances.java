package Model;

import java.sql.Timestamp;

public class Seances {
    private final long id_seance;
    private Timestamp start_time;
    private Timestamp end_time;
    private double ticket_price;
    private long id_movie;
    private long id_screening_room;

    public Seances(long id_seance, Timestamp start_time, Timestamp end_time, double ticket_price, long id_movie, long id_screening_room) {
        this.id_seance = id_seance;
        this.start_time = start_time;
        this.end_time = end_time;
        this.ticket_price = ticket_price;
        this.id_movie = id_movie;
        this.id_screening_room = id_screening_room;
    }

    public long getId_seance() {
        return id_seance;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public double getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(double ticket_price) {
        this.ticket_price = ticket_price;
    }

    public long getId_movie() {
        return id_movie;
    }

    public void setId_movie(long id_movie) {
        this.id_movie = id_movie;
    }

    public long getId_screening_room() {
        return id_screening_room;
    }

    public void setId_screening_room(long id_screening_room) {
        this.id_screening_room = id_screening_room;
    }

    @Override
    public String toString() {
        return
                " " + id_seance +
                " " + start_time +
                " " + end_time +
                " " + ticket_price +
                " " + id_movie +
                " " + id_screening_room;

    }
}
