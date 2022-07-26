package com.ra.nursebot.data.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.ra.nursebot.data.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class Prescription extends BaseModel {
    @Embedded
    private PatientInfoTable patient;

    @Relation(
            entity = MedicineInfoTable.class,
            parentColumn = RoomKey.PATIENT_ID,
            entityColumn = RoomKey.FK_PATIENT_ID
    )
    private List<MedicineInfoTable> medicines;



    public Prescription() {
        this.patient = new PatientInfoTable();
        this.medicines = new ArrayList<>();
    }

    public Prescription(PatientInfoTable patient, List<MedicineInfoTable> medicines) {
        this.patient = patient;
        this.medicines = medicines;
    }

    public PatientInfoTable getPatient() {
        return patient;
    }

    public void setPatient(PatientInfoTable patient) {
        this.patient = patient;
    }

    public List<MedicineInfoTable> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineInfoTable> medicines) {
        this.medicines = medicines;
    }

    public String getPatientName() {
        return patient.getPatientName();
    }

    public void setPatientName(String patientName) {
        patient.setPatientName(patientName);
    }

    public int getBedNo() {
        return patient.getBedNo();
    }

    public void setBedNo(int bedNo) {
        patient.setBedNo(bedNo);
    }
}
