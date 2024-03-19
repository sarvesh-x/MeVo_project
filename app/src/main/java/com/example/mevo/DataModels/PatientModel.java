package com.example.mevo.DataModels;

public class PatientModel {
    String PatientName, PatientAge, PatientContact, PatientAddress, PatientGender, PatientImage;

    public PatientModel(String Name, String Age, String Contact, String Address, String Gender, String PatientImage){
        this.PatientName = Name;
        this.PatientAge = Age;
        this.PatientContact = Contact;
        this.PatientAddress = Address;
        this.PatientGender = Gender;
        this.PatientImage = PatientImage;
    }

    public String getPatientImage() {
        return PatientImage;
    }
    public void setPatientImage(String patientImage) {
        PatientImage = patientImage;
    }
    public String getPateintAddress() {
        return PatientAddress;
    }

    public String getPatientAge() {
        return PatientAge;
    }

    public String getPatientContact() {
        return PatientContact;
    }

    public String getPatientGender() {
        return PatientGender;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPateintAddress(String pateintAddress) {
        PatientAddress = pateintAddress;
    }

    public void setPatientAge(String patientAge) {
        PatientAge = patientAge;
    }

    public void setPatientContact(String patientContact) {
        PatientContact = patientContact;
    }

    public void setPatientGender(String patientGender) {
        PatientGender = patientGender;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

}
