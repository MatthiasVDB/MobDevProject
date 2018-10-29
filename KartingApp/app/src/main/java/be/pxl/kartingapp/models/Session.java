package be.pxl.kartingapp.models;

public class Session {
    private int id;
    private String date;

    public Session(int id, String date){
        this.id = id;
        this.date = date;
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
}
