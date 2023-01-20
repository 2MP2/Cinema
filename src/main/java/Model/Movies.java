package Model;

import java.sql.Timestamp;

public class Movies {
    private final long id_movie;
    private String title;
    private int length;
    private  String  distributor;
    private Timestamp borrow_date;
    private Timestamp return_date;
    private char dub_sub_lec;
    private boolean is3D;

    public Movies(long id_movie, String title, int length, String distributor, Timestamp borrow_date, Timestamp return_date, char dub_sub_lec, boolean is3D) {
        this.id_movie = id_movie;
        this.title = title;
        this.length = length;
        this.distributor = distributor;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
        this.dub_sub_lec = dub_sub_lec;
        this.is3D = is3D;
    }

    public long getId_movie() {
        return id_movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public Timestamp getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(Timestamp borrow_date) {
        this.borrow_date = borrow_date;
    }

    public Timestamp getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Timestamp return_date) {
        this.return_date = return_date;
    }

    public char getDub_sub_lec() {
        return dub_sub_lec;
    }

    public void setDub_sub_lec(char dub_sub_lec) {
        this.dub_sub_lec = dub_sub_lec;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
    }

    @Override
    public String toString() {
        return "id_movie=" + id_movie +
                ", title=" + title +
                ", length=" + length +
                ", distributor=" + distributor +
                ", borrow_date=" + borrow_date +
                ", return_date=" + return_date +
                ", dub_sub_lec=" + dub_sub_lec +
                ", is3D=" + is3D;
    }
}
