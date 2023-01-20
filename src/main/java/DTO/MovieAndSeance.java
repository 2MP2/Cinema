package DTO;

import Model.Movies;
import Model.Seances;

public class MovieAndSeance {
    private Movies m;
    private Seances s;

    public MovieAndSeance(Movies m, Seances s) {
        this.m = m;
        this.s = s;
    }

    public Movies getM() {
        return m;
    }

    public Seances getS() {
        return s;
    }

    @Override
    public String toString() {
        return  m.getTitle() + " "+ m.getLength() + "min "
                + Math.round(s.getTicket_price() * 100.00)/100.00 + "zł "
                + s.getStart_time().getHours() + ":" + ((s.getStart_time().getMinutes()<10) ? "0" : "") + s.getStart_time().getMinutes() + "-"
                + s.getEnd_time().getHours() + ":" + s.getEnd_time().getMinutes()
                + ((Character.toUpperCase(m.getDub_sub_lec()) == 'D') ? " DUBBING"
                : ((Character.toUpperCase(m.getDub_sub_lec()) == 'S') ? " NAPISY"
                : ((Character.toUpperCase(m.getDub_sub_lec()) == 'L') ? " LECTOR" : " XXX")))
                + ((m.isIs3D()) ? " 3D" : " 2D");
    }
}
