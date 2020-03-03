package com.example.innofinitymanufacturers;

public class AccidentDetection {

    private String fuel,temp,smoke,fire,tripped, sos, alert;

    public AccidentDetection(){

    }

    public AccidentDetection(String fuel, String temp, String smoke, String fire, String tripped, String sos, String alert) {
        this.fuel = fuel;
        this.temp = temp;
        this.smoke = smoke;
        this.fire = fire;
        this.tripped = tripped;
        this.alert = alert;
        this.sos = sos;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getFire() {
        return fire;
    }

    public void setFire(String fire) {
        this.fire = fire;
    }

    public String getTripped() {
        return tripped;
    }

    public void setTripped(String tripped) {
        this.tripped = tripped;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
}
