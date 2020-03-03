package com.example.innofinitymanufacturers;

public class NewAccidentDetails {

    private String alert,fire,fuel,mpv,smoke,sos,temp;

    public NewAccidentDetails() {
    }

    public NewAccidentDetails(String alert, String fire, String fuel, String mpv, String smoke, String sos, String temp) {
        this.alert = alert;
        this.fire = fire;
        this.fuel = fuel;
        this.mpv = mpv;
        this.smoke = smoke;
        this.sos = sos;
        this.temp = temp;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getFire() {
        return fire;
    }

    public void setFire(String fire) {
        this.fire = fire;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getMpv() {
        return mpv;
    }

    public void setMpv(String mpv) {
        this.mpv = mpv;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
