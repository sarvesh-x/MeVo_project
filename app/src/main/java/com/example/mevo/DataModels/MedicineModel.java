package com.example.mevo.DataModels;

public class MedicineModel {

    private String MedicineID;
    private String MedicineName;
    private String MedicineQty;

    public MedicineModel(String MedicineID,String MedicineQty, String MedicineName){
        this.MedicineID = MedicineID;
        this.MedicineQty = MedicineQty;
        this.MedicineName = MedicineName;
    }

    public String getMedicineID() {
        return MedicineID;
    }

    public String getMedicineQty() {
        return MedicineQty;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineID(String medicineID) {
        MedicineID = medicineID;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public void setMedicineQty(String medicineQty) {
        MedicineQty = medicineQty;
    }
}
