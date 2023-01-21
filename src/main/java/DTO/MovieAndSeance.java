package DTO;

import Model.Movies;
import Model.Seances;

public class MovieAndSeance {
    private final Movies movie;
    private final Seances seance;

    public MovieAndSeance(Movies m, Seances s) {
        this.movie = m;
        this.seance = s;
    }

    public Movies getMovie() {
        return movie;
    }

    public Seances getSeance() {
        return seance;
    }

    @Override
    public String toString() {
        return  movie.getTitle() + " "+ movie.getLength() + "min "
                + Math.round(seance.getTicket_price() * 100.00)/100.00 + "zł "
                + seance.getStart_time().getHours() + ":" + ((seance.getStart_time().getMinutes()<10) ? "0" : "") + seance.getStart_time().getMinutes() + "-"
                + seance.getEnd_time().getHours() + ":" + seance.getEnd_time().getMinutes()
                + ((Character.toUpperCase(movie.getDub_sub_lec()) == 'D') ? " DUBBING"
                : ((Character.toUpperCase(movie.getDub_sub_lec()) == 'S') ? " NAPISY"
                : ((Character.toUpperCase(movie.getDub_sub_lec()) == 'L') ? " LECTOR" : " XXX")))
                + ((movie.isIs3D()) ? " 3D" : " 2D");
    }
}
