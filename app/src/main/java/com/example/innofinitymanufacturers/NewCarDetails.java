package com.example.innofinitymanufacturers;

public class NewCarDetails {

    String registrationNumbers;
    String Model;
    String Fuel;

    public NewCarDetails (String registrationNumbers, String model, String fuel) {
        this.registrationNumbers = registrationNumbers;
        this.Model = model;
        this.Fuel = fuel;

    }

    public String getRegistrationNumbers() {
        return registrationNumbers;
    }

    public void setRegistrationNumbers(String registrationNumbers) {
        this.registrationNumbers = registrationNumbers;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }
}
