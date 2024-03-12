package com.example.mevo.DataModels;

public class DoctorModel {

    private String doctor_name;
    private int available;
    private int doctor_image;

    // Constructor
    public DoctorModel(String doctor_name, int available, int doctor_image) {
        this.doctor_name = doctor_name;
        this.available = available;
        this.doctor_image = doctor_image;
    }

    // Getter and Setter
    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getDoctor_image() {
        return doctor_image;
    }

    public void setDoctor_image(int doctor_image) {
        this.doctor_image = doctor_image;
    }
}
