package be.pxl.kartingapp.models;

public class Session {
    private int id;
    private String date;
    private String trackLayout;

    public Session(int id, String date){
        this.id = id;
        this.date = date;
    }

    public Session(int id, String date, String trackLayout){
        this.id = id;
        this.date = date;
        this.trackLayout = trackLayout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrackLayout() {
        return trackLayout;
    }

    public void setTrackLayout(String trackLayout) {
        this.trackLayout = trackLayout;
    }
}
