package com.example.mevo.DataModels;

public class DoctorModel {

    private String doctor_name;
    private String room_Name;
    private String available;
    private String doctor_image;
    // Constructor
    public DoctorModel(String doctor_name, String room_Name, String available, String doctor_image) {
        this.doctor_name = doctor_name;
        this.room_Name = room_Name;
        this.available = available;
        this.doctor_image = doctor_image;
    }

    // Getter and Setter

    public String getRoom_Name() {
        return room_Name;
    }

    public void setRoom_Name(String room_Name) {
        this.room_Name = room_Name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDoctor_image() {
        return doctor_image;
    }

    public void setDoctor_image(String doctor_image) {
        this.doctor_image = doctor_image;
    }
}
