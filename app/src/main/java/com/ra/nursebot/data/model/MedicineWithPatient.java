package com.ra.nursebot.data.model;

import androidx.room.Embedded;

import com.ra.nursebot.data.room.MedicineInfoTable;
import com.ra.nursebot.data.room.PatientInfoTable;

import java.io.Serializable;

public class MedicineWithPatient extends BaseModel implements Serializable {

    private MedicineInfoTable med;

    private PatientInfoTable patient;

    public MedicineInfoTable getMed() {
        return med;
    }

    public void setMed(MedicineInfoTable med) {
        this.med = med;
    }

    public PatientInfoTable getPatient() {
        return patient;
    }

    public void setPatient(PatientInfoTable patient) {
        this.patient = patient;
    }
}
