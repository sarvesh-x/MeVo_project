package com.example.mevo.DataModels;

public class MedicineModel {

    private int MedicineID;
    private String MedicineName;
    private int MedicineQty;

    public MedicineModel(int MedicineID,int MedicineQty, String MedicineName){
        this.MedicineID = MedicineID;
        this.MedicineQty = MedicineQty;
        this.MedicineName = MedicineName;
    }

    public int getMedicineID() {
        return MedicineID;
    }

    public int getMedicineQty() {
        return MedicineQty;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineID(int medicineID) {
        MedicineID = medicineID;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public void setMedicineQty(int medicineQty) {
        MedicineQty = medicineQty;
    }
}
