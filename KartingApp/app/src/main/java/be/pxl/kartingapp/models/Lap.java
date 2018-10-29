package be.pxl.kartingapp.models;

public class Lap {
    private String lapTime;
    private String date;

    public Lap(String laptime, String date){
        this.lapTime = laptime;
        this.date = date;
    }


    public String getLapTime() {
        return lapTime;
    }

    public String getDate() {
        return date;
    }
}
