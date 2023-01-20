package DTO;

import Model.Movies;
import Model.Seances;
import Model.TakenSeats;

public class TSandMandS {
    TakenSeats takenSeat;
    Movies movie;
    Seances seance;

    public TSandMandS(TakenSeats takenSeat, Movies movie, Seances seance) {
        this.takenSeat = takenSeat;
        this.movie = movie;
        this.seance = seance;
    }

    public TakenSeats getTakenSeat() {
        return takenSeat;
    }

    public Movies getMovie() {
        return movie;
    }

    public Seances getSeance() {
        return seance;
    }

    @Override
    public String toString() {
        return movie.getTitle() + " " +
                ((Character.toUpperCase(movie.getDub_sub_lec()) == 'D') ? " DUBBING"
                        : ((Character.toUpperCase(movie.getDub_sub_lec()) == 'S') ? " NAPISY"
                        : ((Character.toUpperCase(movie.getDub_sub_lec()) == 'L') ? " LECTOR" : " XXX"))) + " " +
                ((movie.isIs3D()) ? " 3D" : " 2D") + " " +
                + seance.getStart_time().getHours() + ":" + ((seance.getStart_time().getMinutes()<10) ? "0" : "") + seance.getStart_time().getMinutes() + "-"
                + seance.getEnd_time().getHours() + ":" + seance.getEnd_time().getMinutes() + " " + "miejsce: " +
                Character.toString(takenSeat.getRow_identifier()) + takenSeat.getColumn_identifier();

    }
}
