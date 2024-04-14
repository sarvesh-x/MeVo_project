package com.example.mevo.DataModels;

public class PatientModel {
    String _id,PatientName, PatientAge, PatientContact, PatientAddress, PatientGender, PatientImage;

    public PatientModel( String ID, String Name, String Age, String Contact, String Address, String Gender, String PatientImage){
        this._id = ID;
        this.PatientName = Name;
        this.PatientAge = Age;
        this.PatientContact = Contact;
        this.PatientAddress = Address;
        this.PatientGender = Gender;
        this.PatientImage = PatientImage;
    }


    public String getPatientID() {
        return _id;
    }
    public void setPatientID(String patientID) {
        _id = patientID;
    }
    public String getPatientImage() {
        return PatientImage;
    }
    public void setPatientImage(String patientImage) {
        PatientImage = patientImage;
    }
    public String getPatientAddress() {
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
    public void setPatientAddress(String pateintAddress) {
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
